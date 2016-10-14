package com.estore.pojo;

import java.util.Map;



public class InsertOrderBean {
	
	Integer userId;//�û�id
	Integer addressId;//��ַid
	double totalPrice;//�ܼ۸�
	Map<Product, Integer> details;//��Ʒ����
	public Integer getUserId() {
		return userId;
	}

	public  InsertOrderBean(){

	}

	public InsertOrderBean(Integer userId, Integer addressId, double totalPrice, Map<Product, Integer> details) {
		super();
		this.userId = userId;
		this.addressId = addressId;
		this.totalPrice = totalPrice;
		this.details = details;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Map<Product, Integer> getDetails() {
		return details;
	}
	public void setDetails(Map<Product, Integer> details) {
		this.details = details;
	}
	
	

}
