package com.estore.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class MyPublishActivityBean implements Serializable {

    public int status;//1
    public List<ProImag> list;
    public static class ProImag implements Serializable {
        public int id;
        public  String category;
        public int pnum;
        public  String schoolname;
        public double estoreprice;
        public String description;
        public  String cityaddress;

        public  String name;
        public int prowhere;
        public String imgurl;

//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getCategory() {
//            return category;
//        }
//
//        public void setCategory(String category) {
//            this.category = category;
//        }
//
//        public int getPnum() {
//            return pnum;
//        }
//
//        public void setPnum(int pnum) {
//            this.pnum = pnum;
//        }
//
//        public String getSchoolname() {
//            return schoolname;
//        }
//
//        public void setSchoolname(String schoolname) {
//            this.schoolname = schoolname;
//        }
//
//        public double getEstoreprice() {
//            return estoreprice;
//        }
//
//        public void setEstoreprice(double estoreprice) {
//            this.estoreprice = estoreprice;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String getCityaddress() {
//            return cityaddress;
//        }
//
//        public void setCityaddress(String cityaddress) {
//            this.cityaddress = cityaddress;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getProwhere() {
//            return prowhere;
//        }
//
//        public void setProwhere(int prowhere) {
//            this.prowhere = prowhere;
//        }
//
//        public String getImgurl() {
//            return imgurl;
//        }
//
//        public void setImgurl(String imgurl) {
//            this.imgurl = imgurl;
//        }

        @Override
        public String toString() {
            return "ProImag{" +
                    "productId=" + id +
                    ", category='" + category + '\'' +
                    ", pnum=" + pnum +
                    ", schoolname='" + schoolname + '\'' +
                    ", estoreprice=" + estoreprice +
                    ", description='" + description + '\'' +
                    ", cityaddress='" + cityaddress + '\'' +
                    ", name='" + name + '\'' +
                    ", prowhere=" + prowhere +
                    ", imgurl='" + imgurl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MyPublishActivityBean{" +
                "status=" + status +
                ", list=" + list +
                '}';
    }



}