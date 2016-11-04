package com.estore.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/4.
 */
public class CareInfo implements Serializable {

    private int id;
    private int collectionId;
    private String name;
    private String category;
    private Integer userId;
    private Double minPrice;
    private String time;
    private String beginTime;
    private String endTime;
    private String decription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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


    @Override
    public String toString() {
        return "CareInfo{" +
                "id=" + id +
                ", collectionId=" + collectionId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", userId=" + userId +
                ", minPrice=" + minPrice +
                ", time='" + time + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", decription='" + decription + '\'' +
                '}';
    }
}
