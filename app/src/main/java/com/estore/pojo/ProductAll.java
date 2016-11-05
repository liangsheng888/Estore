package com.estore.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ProductAll implements Serializable{
	private Integer id;
	private Integer user_id;//
	private String name;
	private String category;
	private double marketprice;
	private double estoreprice;
	private String proaddress;
	public String imgurl;
	public ProductAll(Integer userId, String name, String category,
					  double estoreprice, String proaddress, String imgurl,
					  String description, Integer youfei, Integer prowhere, Integer pnum) {
		super();
		user_id = userId;
		this.name = name;
		this.category = category;
		this.estoreprice = estoreprice;
		this.proaddress = proaddress;
		this.imgurl = imgurl;
		this.description = description;
		this.youfei = youfei;
		this.prowhere = prowhere;
		this.pnum = pnum;
		
	}

	public ArrayList<ProductAll> list;
	private String description;
	private Integer youfei;
	public Integer getYoufei() {
		return youfei;
	}
	public void setYoufei(Integer youfei) {
		this.youfei = youfei;
	}
	public String getProaddress() {
		return proaddress;
	}
	public void setProaddress(String proaddress) {
		this.proaddress = proaddress;
	}
	private String email;
	private Integer prowhere;//商品所属 0同城 1 城市
	private Integer pnum;
	private Double auct_minprice;//竞拍最低价格
	private Double auct_time;//竞拍开始剩余时间
	private Date auct_begin;//竞拍开始时间
	private Date auct_end;//结束时间
	private String shcoolname;

	private String auct_name;//拍卖商品
	private Double auct_bid_price;//每次最少加价价格


	public Double getAuct_bid_price() {
		return auct_bid_price;
	}
	public void setAuct_bid_price(Double auctBidPrice) {
		auct_bid_price = auctBidPrice;
	}
	public String getShcoolname() {
		return shcoolname;
	}
	public void setShcoolname(String shcoolname) {
		this.shcoolname = shcoolname;
	}
	public ProductAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProductAll(String name, String category, double estoreprice,
			String email, Integer pnum, String imgurl, String description) {
		super();
		this.name = name;
		this.category = category;
		this.estoreprice = estoreprice;
		this.email = email;
		this.pnum = pnum;
		this.imgurl = imgurl;
		this.description = description;
	}
	public ProductAll(int id, String name, String category,
			int pnum, Double auctMinprice, Double auctTime, Date auctBegin,
			Date auctEnd, String imgurl, String description) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		
		this.pnum = pnum;
		auct_minprice = auctMinprice;
		auct_time = auctTime;
		auct_begin = auctBegin;
		auct_end = auctEnd;
		this.imgurl = imgurl;
		this.description = description;
	}
	
	public ProductAll(String name, String category, double marketprice,
			double estoreprice, int pnum, String imgurl, String description) {
		super();
		this.name = name;
		this.category = category;
		this.marketprice = marketprice;
		this.estoreprice = estoreprice;
		this.pnum = pnum;
		this.imgurl = imgurl;
		this.description = description;
	}
	public ProductAll(String name, String category, double estoreprice,
			String email, int prowhere, int pnum, int id, String imgurl,
			String description) {
		super();
		this.name = name;
		this.category = category;
		this.estoreprice = estoreprice;
		this.email = email;
		this.prowhere = prowhere;
		this.pnum = pnum;
		this.id = id;
		this.imgurl = imgurl;
		this.description = description;
	}
//拍卖
 
	
	
	
	public ProductAll(String proName, String category2, double parseDouble,
			 String fileurl, String proDescription,
			double parseDouble2) {
		super();
		this.name=proName;
		this.category=category2;
		this.auct_minprice=parseDouble;
		this.imgurl=fileurl;
		this.description=proDescription;
		this.auct_bid_price=parseDouble2;
		
	}
	public int getProwhere() {
		return prowhere;
	}
	public void setProwhere(Integer prowhere) {
		this.prowhere = prowhere;
	}
	public Double getAuct_minprice() {
		return auct_minprice;
	}
	public void setAuct_minprice(Double auctMinprice) {
		auct_minprice = auctMinprice;
	}
	public Double getAuct_time() {
		return auct_time;
	}
	public void setAuct_time(Double auctTime) {
		auct_time = auctTime;
	}
	public Date getAuct_begin() {
		return auct_begin;
	}
	public void setAuct_begin(Date auctBegin) {
		auct_begin = auctBegin;
	}
	public Date getAuct_end() {
		return auct_end;
	}
	public void setAuct_end(Date auctEnd) {
		auct_end = auctEnd;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer userId) {
		user_id = userId;
	}
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public double getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(double marketprice) {
		this.marketprice = marketprice;
	}
	public double getEstoreprice() {
		return estoreprice;
	}
	public void setEstoreprice(double estoreprice) {
		this.estoreprice = estoreprice;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Product [auct_begin=" + auct_begin + ", auct_end=" + auct_end
				+ ", auct_minprice=" + auct_minprice + ", auct_time="
				+ auct_time + ", bidprice=" + auct_bid_price + ", category="
				+ category + ", description=" + description + ", email="
				+ email + ", estoreprice=" + estoreprice + ", id=" + id
				+ ", imgurl=" + imgurl + ", marketprice=" + marketprice
				+ ", name=" + name + ", pnum=" + pnum + ", proaddress="
				+ proaddress + ", prowhere=" + prowhere + ", shcoolname="
				+ shcoolname + ", user_id=" + user_id + "]";
	}
	public void setYoufei(int youfei) {
		// TODO Auto-generated method stub
		this.youfei=youfei;
	}
	public ProductAll(Integer id, String name, String category,
			double marketprice, double estoreprice, String proaddress,
			String imgurl, String description, Integer prowhere, Integer pnum,
			String shcoolname) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.marketprice = marketprice;
		this.estoreprice = estoreprice;
		this.proaddress = proaddress;
		this.imgurl = imgurl;
		this.description = description;
		this.prowhere = prowhere;
		this.pnum = pnum;
		this.shcoolname = shcoolname;
	}

	
}
