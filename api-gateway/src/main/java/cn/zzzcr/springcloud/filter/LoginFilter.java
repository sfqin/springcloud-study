package cn.zzzcr.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class LoginFilter extends ZuulFilter {

    /**
     * 设置过滤器类型 前置还是后置
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器质性顺序，越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 定义过滤器生效规则
     * @return
     */
    @Override
    public boolean shouldFilter() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        System.out.println("拦截URI => " + request.getRequestURI());
        System.out.println("拦截URL => " + request.getRequestURL());

        //ACL 访问权限控制列表

        if("/api/order/v6/order".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }

        return false;
    }

    /**
     * 过滤的业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {


        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        String token = request.getHeader("token");
        String cookie = request.getHeader("cookie");

        System.out.println("token => "+token+" cookie => " + cookie);
        // 自定义一个加密算法 解密token 获取登录信息 jwt
        if(StringUtils.isBlank(token) || StringUtils.isBlank(cookie)){
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
