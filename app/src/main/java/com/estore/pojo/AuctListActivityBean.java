package com.estore.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/21.
 */
public class AuctListActivityBean implements Serializable{


    public int status;
    public ArrayList<Auct> list;

    public static class Auct implements Serializable{//必须是static类型的

        public String auct_category;
        public String auct_end;
        public String auct_pnum;
        public String auct_name;
        public String auct_minprice;
        public String auct_id;
        public String auct_begin;
        public String  user_id;
        public String auct_time;
        public String  auct_description;
        public String  auct_imgurl;

///{
//            "status": 2,
//                    "list": [
//            {
//                "auct_category": "手机",
//                    "auct_end": "2016-09-21",
//                    "auct_pnum": 1,
//                    "auct_name": "苹果手机",
//                    "auct_minprice": 1000,
//                    "auct_id": 1,
//                    "auct_begin": "2016-09-21",
//                    "user_id": 1,
//                    "auct_time": 30,
//                    "auct_description": "刚买没几天，还是新的，不想用了"
//            }
//            ]
//        }


        @Override
        public String toString() {
            return "Auct{" +
                    "auct_category='" + auct_category + '\'' +
                    ", auct_end='" + auct_end + '\'' +
                    ", auct_pnum='" + auct_pnum + '\'' +
                    ", auct_name='" + auct_name + '\'' +
                    ", auct_minprice='" + auct_minprice + '\'' +
                    ", auct_id='" + auct_id + '\'' +
                    ", auct_begin='" + auct_begin + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", auct_time='" + auct_time + '\'' +
                    ", auct_description='" + auct_description + '\'' +
                    ", auct_imgurl='" + auct_imgurl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AuctListActivityBean{" +
                "status=" + status +
                ", list=" + list +
                '}';
    }
}
