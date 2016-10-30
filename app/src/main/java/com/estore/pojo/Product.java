package com.estore.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class Product implements Serializable {
   public int status;
    public ArrayList<Products> list;

    public static class Products implements Serializable{
        public int user_id;
        public int id;
        public String name;
        public int youfei;
        public String category;
        public double marketprice;
        public String userPhoto;
        public String userNick;
        public double estoreprice;
        public int pnum;
        public String imgurl;
        public String description;
        public String proaddress;
        public int prowhere;
        public String schoolname;
        public String time;
        public int xingCount;

        @Override
        public String toString() {
            return "Products{" +
                    "user_id=" + user_id +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", youfei=" + youfei +
                    ", category='" + category + '\'' +
                    ", marketprice=" + marketprice +
                    ", userPhoto='" + userPhoto + '\'' +
                    ", userNick='" + userNick + '\'' +
                    ", estoreprice=" + estoreprice +
                    ", pnum=" + pnum +
                    ", imgurl='" + imgurl + '\'' +
                    ", description='" + description + '\'' +
                    ", proaddress='" + proaddress + '\'' +
                    ", prowhere=" + prowhere +
                    ", schoolname='" + schoolname + '\'' +
                    ", time='" + time + '\'' +
                    ", xingCount=" + xingCount +
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
    

