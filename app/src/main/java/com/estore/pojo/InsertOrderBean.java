package com.estore.pojo;

import java.util.Map;



public class InsertOrderBean {
	Integer estoreId;
	String estoreid;

	public String getEstoreid() {
		return estoreid;
	}

	public void setEstoreid(String estoreid) {
		this.estoreid = estoreid;
	}

	Integer userId;//�û�id
	Integer addressId;//��ַid
	double totalPrice;//�ܼ۸�
	Map<Product.Products, Integer> details;//��Ʒ����
	public Integer getUserId() {
		return userId;
	}

	public  InsertOrderBean(){

	}public Integer getEstoreId() {
		return estoreId;
	}

	public void setEstoreId(Integer estoreId) {
		this.estoreId = estoreId;
	}



	public InsertOrderBean(Integer userId, Integer addressId, double totalPrice, Map<Product.Products, Integer> details) {
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
	public Map<Product.Products, Integer> getDetails() {
		return details;
	}
	public void setDetails(Map<Product.Products, Integer> details) {
		this.details = details;
	}
	
	

}
