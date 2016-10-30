package com.estore.pojo;

import java.io.Serializable;
import java.util.Date;

public class Envalute implements Serializable {
	private Integer evt_id;
	private User user;
	private Product product;//
	private String evt_msg;//内容
	private String evt_imgurl;//图片
	public Envalute(User user, Product product, String evtMsg,
			Integer evtHonest) {
		super();
		this.user = user;
		this.product = product;
		evt_msg = evtMsg;

		evt_honest = evtHonest;
	}
	public Envalute( User user, Product product, String evtMsg,
			String evtImgurl, String evtTime, Integer evtHonest) {
		super();
		this.user = user;
		this.product = product;
		evt_msg = evtMsg;
		evt_imgurl = evtImgurl;
		evt_time = evtTime;
		evt_honest = evtHonest;
	}
	private String evt_time;//评价时间
	private Integer evt_honest;//诚信度
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getEvt_msg() {
		return evt_msg;
	}
	public void setEvt_msg(String evtMsg) {
		evt_msg = evtMsg;
	}
	public String getEvt_imgurl() {
		return evt_imgurl;
	}
	public void setEvt_imgurl(String evtImgurl) {
		evt_imgurl = evtImgurl;
	}
	public String getEvt_time() {
		return evt_time;
	}
	public void setEvt_time(String evtTime) {
		evt_time = evtTime;
	}
	public Integer getEvt_honest() {
		return evt_honest;
	}
	public void setEvt_honest(Integer evtHonest) {
		evt_honest = evtHonest;
	}
	public Envalute() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
