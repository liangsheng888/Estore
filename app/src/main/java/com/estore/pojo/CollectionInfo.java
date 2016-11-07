package com.estore.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/7.
 */
public class CollectionInfo implements Serializable {
        private Integer userId;
        private Integer auctId;   //竞拍商品id
        private Integer productId;
        private Boolean shoucangFlag;
        private Integer collectionId;   //收藏id
        private String name;    //收藏商品名字
        private String category;  //类型
        private Double minPrice;    //最低价格
        private String time;       //竞拍时间轴
        private String beginTime;   //竞拍开始时间
        private String endTime;    //竞拍结束时间
        private String decription;  //介绍
        private String imgurl;

        public CollectionInfo() {
        }

        public CollectionInfo(Integer userId, Integer auctId) {
            super();
            this.userId = userId;
            this.auctId = auctId;
        }







        public CollectionInfo(Integer userId, Integer auctId, Integer collectionId,
                        String name, String category, Double minPrice, String time,
                        String beginTime, String endTime, String decription,String imgurl) {
            super();
            this.userId = userId;
            this.auctId = auctId;
            this.collectionId = collectionId;
            this.name = name;
            this.category = category;
            this.minPrice = minPrice;
            this.time = time;
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.decription = decription;
            this.imgurl=imgurl;
        }


        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public Integer getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(Integer collectionId) {
            this.collectionId = collectionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Double minPrice) {
            this.minPrice = minPrice;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getDecription() {
            return decription;
        }

        public void setDecription(String decription) {
            this.decription = decription;
        }

        public Boolean getShoucangFlag() {
            return shoucangFlag;
        }

        public void setShoucangFlag(Boolean shoucangFlag) {
            this.shoucangFlag = shoucangFlag;
        }


        public Integer getUserId() {
            return userId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getAuctId() {
            return auctId;
        }

        public void setAuctId(Integer auctId) {
            this.auctId = auctId;
        }


    @Override
    public String toString() {
        return "CollectionInfo{" +
                "userId=" + userId +
                ", auctId=" + auctId +
                ", productId=" + productId +
                ", shoucangFlag=" + shoucangFlag +
                ", collectionId=" + collectionId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", minPrice=" + minPrice +
                ", time='" + time + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", decription='" + decription + '\'' +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}

