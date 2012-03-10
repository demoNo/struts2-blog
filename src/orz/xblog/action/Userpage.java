package orz.xblog.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Page;
import orz.xblog.task.HandleUsers;

import com.opensymphony.xwork2.ActionSupport;

public class Userpage extends ActionSupport {
	
	private Page page; // 引入Page类型变量
	private int pageNow; // 当前页数

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String first() throws Exception {
		HandleUsers hu = new HandleUsers();
		page = hu.total();
		page = hu.firstPage(page);
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		List list = new ArrayList();
		List list1 = new ArrayList();
		list1 = hu.findbyid(session.getAttribute("login").toString());
		list = hu.findBypage(page);
		req.setAttribute("users", list); // 查找第一页的数据
		req.setAttribute("usr", list1);
		return SUCCESS;
	}

	public String pre() throws Exception {
		HandleUsers hu = new HandleUsers();
		page = hu.total(); // 获得页数的信息如当前页数 一页包含多少条记录等
		if (pageNow < 1) { // 页数小于1时 当前页数，仍然为1
			pageNow = 1;
		}
		page.setPageNow(pageNow); // 设置当前的页数
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = hu.findBypage(page); // 查找当前页数的数据
		req.setAttribute("users", list);
		return SUCCESS;
	}

	public String next() throws Exception {
		HandleUsers hu = new HandleUsers();
		page = hu.total(); // 获得页数的信息如当前页数 一页包含多少条记录等
		if (pageNow < 2) { // 如果当前页数小于2下一页链接无效，下一页的第一个当前页面为第二页
			pageNow = 2;
		}
		if (pageNow > page.getLastPage()) {
			pageNow = page.getLastPage(); // 如果当前页数大于最大的页数，是当前页数永远是最后一页
		}
		page.setPageNow(pageNow);
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = hu.findBypage(page);     // 查找当前页数的数据
		req.setAttribute("users", list); 
		return SUCCESS;
	}

	public String last() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HandleUsers hu = new HandleUsers();
		page = hu.total(); // 获得页数的信息如当前页数 一页包含多少条记录等
		page = hu.lastPage(page); // 设置当前页数为最大的也是
		List list = new ArrayList();
		list = hu.findBypage(page); // 查找最后一页的信息
		req.setAttribute("users", list); 
		return SUCCESS;
	}
}
