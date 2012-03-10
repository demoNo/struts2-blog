package orz.xblog.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Page;
import orz.xblog.task.HandleEssay;

import com.opensymphony.xwork2.ActionSupport;

public class FindBycls extends ActionSupport {
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

	public String cindex() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		HandleEssay he = new HandleEssay();
		if (session.getAttribute("cls") != null)
			session.removeAttribute("cls");// 每次访问分类结果时先清空搜索结果.
		String cls = req.getParameter("cls");
		session.setAttribute("cls", cls);
		page = he.ctotal(cls);
		page = he.firstPage(page);
		List list = new ArrayList();
		list = he.findclassBypage(page, cls);
		req.setAttribute("cessay", list);
		return SUCCESS;
	}

	public String first() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String cls = session.getAttribute("cls").toString();
		HandleEssay he = new HandleEssay();
		page = he.ctotal(cls); // 获得页数的信息如当前页数 一页包含多少条记录等
		page = he.firstPage(page); // 设置当前页数为第一页
		List list = new ArrayList();
		list = he.findclassBypage(page, cls);
		req.setAttribute("cessay", list); // 查找第一页的数据
		return SUCCESS;
	}

	public String pre() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String cls = session.getAttribute("cls").toString();
		HandleEssay he = new HandleEssay();
		page = he.ctotal(cls); // 获得页数的信息如当前页数 一页包含多少条记录等
		if (pageNow < 1) { // 页数小于1时 当前页数，任然为1
			pageNow = 1;
		}
		page.setPageNow(pageNow); // 设置当前的页数
		List list = new ArrayList();
		list = he.findclassBypage(page, cls); // 查找当前页数的数据
		req.setAttribute("cessay", list);
		return SUCCESS;
	}

	public String next() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String cls = session.getAttribute("cls").toString();
		HandleEssay he = new HandleEssay();
		page = he.ctotal(cls); // 获得页数的信息如当前页数 一页包含多少条记录等
		if (pageNow < 2) { // 如果当前页数小于2下一页链接无效，下一页的第一个当前页面为第二页
			pageNow = 2;
		}
		if (pageNow > page.getLastPage()) {
			pageNow = page.getLastPage(); // 如果当前页数大于最大的页数，是当前页数永远是最后一页
		}
		page.setPageNow(pageNow);
		List list = new ArrayList();
		list = he.findclassBypage(page, cls);
		req.setAttribute("cessay", list); // 查找当前页数的数据
		return SUCCESS;
	}

	public String last() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String cls = session.getAttribute("cls").toString();
		HandleEssay he = new HandleEssay();
		page = he.ctotal(cls); // 获得页数的信息如当前页数 一页包含多少条记录等
		page = he.lastPage(page); // 设置当前页数为最大的也是
		List list = new ArrayList();
		list = he.findclassBypage(page, cls);
		req.setAttribute("cessay", list); // 查找最后一页的信息
		return SUCCESS;
	}
}
