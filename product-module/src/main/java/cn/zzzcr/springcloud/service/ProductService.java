package cn.zzzcr.springcloud.service;

import cn.zzzcr.springcloud.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {


    public static Map<Integer,Product> productMap = new HashMap<>();

    static {
        productMap.put(1,new Product(1,"茅台",100));
        productMap.put(2,new Product(2,"猫粮",50));
        productMap.put(3,new Product(3,"玩具飞机",80));
        productMap.put(4,new Product(4,"iPhone XS",8700));
        productMap.put(5,new Product(5,"联想显示器",5000));
    }



    public Product findById(Integer id){

        Product product = productMap.get(id);
        return product;
    }

}
