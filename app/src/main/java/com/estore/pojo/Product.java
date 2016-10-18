package com.estore.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class Product implements Serializable {
   public int status;
    public ArrayList<Products> list;

    public static class Products implements Serializable{
        public int id;
        public String name;
        public String category;
        public double marketprice;
        public double estoreprice;
        public int pnum;
        public String imgurl;
        public String description;
        public String proaddress;
        public int prowhere;


        @Override
        public String toString() {
            return "Products{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", category='" + category + '\'' +
                    ", marketprice=" + marketprice +
                    ", estoreprice=" + estoreprice +
                    ", pnum=" + pnum +
                    ", imgurl='" + imgurl + '\'' +
                    ", description='" + description + '\'' +
                    ", proaddress='" + proaddress + '\'' +
                    ", prowhere='" + prowhere + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "status=" + status +
                ", list=" + list +
                '}';
    }
}
    

