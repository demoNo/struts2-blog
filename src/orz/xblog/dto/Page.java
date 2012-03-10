package orz.xblog.dto;

public class Page {
	private int totalNum;       //总记录数  
    private int lastPage;       //最后一页页数  
    private int firstPage;      //第一页  
    private int pageNow;        //当前页数  
    private int pageSize;       //一页包含多少条记录  
      
    public int getTotalNum() {  
        return totalNum;  
    }  
    public void setTotalNum(int totalNum) {  
        this.totalNum = totalNum;  
    }  
    public int getLastPage() {  
        return lastPage;  
    }  
    public void setLastPage(int lastPage) {  
        this.lastPage = lastPage;  
    }  
    public int getFirstPage() {  
        return firstPage;  
    }  
    public void setFirstPage(int firstPage) {  
        this.firstPage = firstPage;  
    }  
    public int getPageNow() {  
        return pageNow;  
    }  
    public void setPageNow(int pageNow) {  
        this.pageNow = pageNow;  
    }  
    public int getPageSize() {  
        return pageSize;  
    }  
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
}
