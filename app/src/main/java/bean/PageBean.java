package bean;


import java.util.List;

public class PageBean<Products> {
        private Integer pageSize=12;//每页显示数据
        private Integer curPage=1;//当前页
        private Integer pageTotal;//总页数
        private Integer dataTotal;//总数据
		private List<Products> pageProduct;//每页显示的数据
		public Integer getPageSize() {
			return pageSize;
		}
		@Override
		public String toString() {
			return "PageBean [curPage=" + curPage + ", dataTotal=" + dataTotal
					+ ", pageProduct=" + pageProduct + ", pageSize=" + pageSize
					+ ", pageTotal=" + pageTotal + "]";
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
			if(this.dataTotal%this.pageSize==0){
				this.pageTotal=this.dataTotal/this.pageSize;
			}
			else{
				this.pageTotal=(this.dataTotal/this.pageSize)+1;
			}
		}

	public List<Products> getPageProduct() {
		return pageProduct;
	}

	public void setPageProduct(List<Products> pageProduct) {
		this.pageProduct = pageProduct;
	}
}
