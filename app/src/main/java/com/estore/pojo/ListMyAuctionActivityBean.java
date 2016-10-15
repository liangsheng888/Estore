package com.estore.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class ListMyAuctionActivityBean implements Serializable {
    public int status;//2
    public List<ProImag> list;
    public static class ProImag implements Serializable{
        public  String auct_category;
        //结束时间
        public  String auct_end;//结束时间
        public  int auct_pnum;//个数
        public  String auct_name;
        public Double auct_minprice;//拍卖最低价格
        public  int auct_id;
        public  String auct_begin;//开始时间
        public  int user_id;
        public Double auct_time;//竞拍开始剩余时间
        public String auct_imgurl;//图片
        public String auct_description;//描述

//{"status":2,"list":[{"auct_category":"手机","auct_end":"2016-09-21 00:09:00","auct_pnum":1,"auct_name":"苹果手机","auct_minprice":1000,"auct_id":1,"auct_begin":"2016-09-21 00:09:00","user_id":1,"auct_time":30,"auct_imgurl":"uploads//2b7505f7-346d-4913-866b-fb13f50208ad_phone.png","auct_description":"刚买没几天，还是新的，不想用了"}
        @Override
        public String toString() {
            return "ProImag{" +
                    "auct_begin=" + auct_begin +
                    ", auct_category='" + auct_category + '\'' +
                    ", auct_end=" + auct_end +
                    ", auct_pnum=" + auct_pnum +
                    ", auct_name='" + auct_name + '\'' +
                    ", auct_minprice=" + auct_minprice +
                    ", auct_id=" + auct_id +
                    ", user_id=" + user_id +
                    ", auct_time=" + auct_time +
                    ", imgurl='" + auct_imgurl + '\'' +
                    ", auct_description='" + auct_description + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "ListMyPaiMaiActivityBean{" +
                "list=" + list +
                ", status=" + status +
                '}';
    }
}
