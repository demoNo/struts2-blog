package orz.xblog.dto;

public class Page {
	private int totalNum;       //�ܼ�¼��  
    private int lastPage;       //���һҳҳ��  
    private int firstPage;      //��һҳ  
    private int pageNow;        //��ǰҳ��  
    private int pageSize;       //һҳ������������¼  
      
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
