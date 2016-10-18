package com.estore.pojo;

import java.io.Serializable;

public class OrderDetail implements Serializable {
	private Integer orderDetailId;//
	private Integer goodsOrderId;//������id��
	private Product.Products product;//��Ʒ��Ϣ
	private int goodsNum;//��Ʒ����
	private double goodsPrice;// ���������

	@Override
	public String toString() {
		return "OrderDetail{" +
				"orderDetailId=" + orderDetailId +
				", goodsOrderId=" + goodsOrderId +
				", product=" + product +
				", goodsNum=" + goodsNum +
				", goodsPrice=" + goodsPrice +
				'}';
	}

	public OrderDetail(Integer orderDetailId, Integer goodsOrderId, Product.Products product, int goodsNum, double goodsPrice) {
		super();
		this.orderDetailId = orderDetailId;
		this.goodsOrderId = goodsOrderId;
		this.product = product;
		this.goodsNum = goodsNum;
		this.goodsPrice = goodsPrice;
	}
	public Integer getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}


	public OrderDetail(Integer goodsOrderId, Product.Products product, int goodsNum, double goodsPrice) {
		super();
		this.goodsOrderId = goodsOrderId;
		this.product = product;
		this.goodsNum = goodsNum;
		this.goodsPrice = goodsPrice;
	}
	public Integer getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(Integer goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	public Product.Products getProduct() {
		return product;
	}
	public void setProduct(Product.Products product) {
		this.product = product;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	public double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	

}
