package com.estore.pojo;

import java.io.Serializable;
import java.util.List;


/**
 * {
 "status": 1,
 "list": [
 {
 {
 "prowhere": 1,
 "proaddress": "北京",
 "estoreprice": 111,
 "marketprice": 0,
 "youfei": 0,
 "id": 69,
 "time": "2016-10-13 20:23:53",
 "category": "笔记本",
 "schoolname": "天津师范学院",
 "pnum": 11,
 "description": "吾问无为谓呜呜呜呜呜呜我问问",
 "name": "我玩",
 "user_id": 0,
 "imgurl": "/uploads/20161027_193325_a5c178e2-4abd-4f4f-b212-8ea99128e616.png"
 },
 }
 */
public class MyPublishActivityBean implements Serializable {

    public int status;//1
    public List<ProImag> list;

    public static class ProImag implements Serializable {
        public int id;
        public String name;//名字
        public String category;//类型
        public double marketprice;//超市价
        public double estoreprice;//二货价格
        public int pnum;//个数
        public String imgurl;//图片
        public String description;//描述
        public String proaddress;//同城
        public int prowhere;//地点
        public int youfei;//邮费
        public String schoolname;//高校
        public String time;//时间

        @Override
        public String toString() {
            return "ProImag{" +
                    "category='" + category + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", marketprice=" + marketprice +
                    ", estoreprice=" + estoreprice +
                    ", pnum=" + pnum +
                    ", imgurl='" + imgurl + '\'' +
                    ", description='" + description + '\'' +
                    ", proaddress='" + proaddress + '\'' +
                    ", prowhere=" + prowhere +
                    ", youfei=" + youfei +
                    ", schoolname='" + schoolname + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getEstoreprice() {
            return estoreprice;
        }

        public void setEstoreprice(double estoreprice) {
            this.estoreprice = estoreprice;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public double getMarketprice() {
            return marketprice;
        }

        public void setMarketprice(double marketprice) {
            this.marketprice = marketprice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPnum() {
            return pnum;
        }

        public void setPnum(int pnum) {
            this.pnum = pnum;
        }

        public String getProaddress() {
            return proaddress;
        }

        public void setProaddress(String proaddress) {
            this.proaddress = proaddress;
        }

        public int getProwhere() {
            return prowhere;
        }

        public void setProwhere(int prowhere) {
            this.prowhere = prowhere;
        }

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getYoufei() {
            return youfei;
        }

        public void setYoufei(int youfei) {
            this.youfei = youfei;
        }
//        public String getCategory() {
//            return category;
//        }
//
//        public void setCategory(String category) {
//            this.category = category;
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
//        public double getEstoreprice() {
//            return estoreprice;
//        }
//
//        public void setEstoreprice(double estoreprice) {
//            this.estoreprice = estoreprice;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getImgurl() {
//            return imgurl;
//        }
//
//        public void setImgurl(String imgurl) {
//            this.imgurl = imgurl;
//        }
//
//        public double getMarketprice() {
//            return marketprice;
//        }
//
//        public void setMarketprice(double marketprice) {
//            this.marketprice = marketprice;
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
//        public int getPnum() {
//            return pnum;
//        }
//
//        public void setPnum(int pnum) {
//            this.pnum = pnum;
//        }
//
//        public String getProaddress() {
//            return proaddress;
//        }
//
//        public void setProaddress(String proaddress) {
//            this.proaddress = proaddress;
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
//        public String getSchoolname() {
//            return schoolname;
//        }
//
//        public void setSchoolname(String schoolname) {
//            this.schoolname = schoolname;
//        }
//
//        public String getTime() {
//            return time;
//        }
//
//        public void setTime(String time) {
//            this.time = time;
//        }
//
//        public int getYoufei() {
//            return youfei;
//        }
//
//        public void setYoufei(int youfei) {
//            this.youfei = youfei;
//        }
    }
}