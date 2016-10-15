package com.estore.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class MyPublishActivityBean implements Serializable {
    /*
    {
    "status": 1,
    "list": [
        {
            "category": "笔记本",
            "pnum": 1,
            "heighschool": "河南科技大学",
            "estoreprice": 2000,
            "description": "渣渣买的，，质量不渣渣",
            "samecity": "河南洛阳",
            "name": "手表",
            " prowhere": 0,
            "imgurl": "uploads//010a5a5e-f146-478b-88b7-a9b42176249e_2.png"
        },
     */
    public int status;//1
    public List<ProImag> list;
    public static class ProImag implements Serializable {
        public  String category;
        public int pnum;
        public  String heighschool;
        public double estoreprice;
        public String description;
        public  String samecity;

        public  String name;
        public int prowhere;
        public String imgurl;

        @Override
        public String toString() {
            return "ProImag{" +
                    "category='" + category + '\'' +
                    ", pum=" + pnum +
                    ", heighschool='" + heighschool + '\'' +
                    ", estoreprice=" + estoreprice +
                    ", description='" + description + '\'' +
                    ", samecity='" + samecity + '\'' +
                    ", name='" + name + '\'' +
                    ", prowhere=" + prowhere +
                    ", imgurl='" + imgurl + '\'' +
                    '}';
        }
    }
}