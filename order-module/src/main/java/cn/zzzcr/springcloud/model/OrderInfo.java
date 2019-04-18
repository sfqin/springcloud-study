package cn.zzzcr.springcloud.model;

public class OrderInfo {

    private String orderNo;

    private Integer productNo;

    private String productName;

    private Integer price;

    private Object productInfo;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Object productInfo) {
        this.productInfo = productInfo;
    }

    public OrderInfo(){}

    public OrderInfo(String orderNo, Integer productNo, String productName, Integer price) {
        this.orderNo = orderNo;
        this.productNo = productNo;
        this.productName = productName;
        this.price = price;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
