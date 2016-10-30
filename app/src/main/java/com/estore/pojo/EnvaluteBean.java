package com.estore.pojo;

import java.util.List;

public class EnvaluteBean<Envalute> {
	 private Integer pageSize=5;//每页显示数据
     private Integer curPage=1;//当前页
     private Integer pageTotal;//总页数
     private Integer dataTotal;//总数据
     private List<Envalute> pageEnvalute;//每页显示的数据  
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}
	public Integer getDataTotal() {
		return dataTotal;
	}
	public void setDataTotal(Integer dataTotal) {
		this.dataTotal = dataTotal;
	}
	public List<Envalute> getPageEnvalute() {
		return pageEnvalute;
	}
	public void setPageEnvalute(List<Envalute> pageEnvalute) {
		this.pageEnvalute = pageEnvalute;
	}
     

}
