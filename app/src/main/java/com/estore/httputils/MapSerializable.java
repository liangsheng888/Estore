package com.estore.httputils;



import com.estore.pojo.Product;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/12.
 */
 public class MapSerializable implements Serializable {
  Map<Product,Integer> pro ;

 public Map<Product, Integer> getPro() {
  return pro;
 }

 public void setPro(Map<Product, Integer> pro) {
  this.pro = pro;
 }
}
