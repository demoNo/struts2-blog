package orz.xblog.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Page;
import orz.xblog.task.HandleComment;
import orz.xblog.task.HandleEssay;
import orz.xblog.task.HandleTag;
import orz.xblog.task.HandleUrl;
import orz.xblog.task.HandleUsers;

import com.opensymphony.xwork2.ActionSupport;

public class DelAction extends ActionSupport {
	
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
	
	// delete essay and the comment
	public String delEssay() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String id[] = req.getParameterValues("eid");
		HandleEssay he = new HandleEssay();
		HandleTag ht = new HandleTag();
		if (id != null && id.length > 0) {
			HandleComment hc = new HandleComment();
			for (int i = 0; i < id.length; i++){
				he.delete(id[i]);
				hc.delByes(id[i]);
				ht.delete(id[i]);
			}
			req.setAttribute("result", "success");
			page = he.total();
			page = he.firstPage(page);
			List list = new ArrayList();
			list = he.findAll();
			req.setAttribute("list", list);
			return "success";
		} else
			return "fail";
	}

	// delete comment
	public String delComment() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String id[] = req.getParameterValues("id");
		HandleComment hc = new HandleComment();
		if (id != null && id.length > 0) {
			for (int i = 0; i < id.length; i++)
				hc.delete(id[i]);
			req.setAttribute("result", "success");
			page = hc.total();
			page = hc.firstPage(page);
			List comm = new ArrayList();
			comm = hc.findAll();
			req.setAttribute("comment", comm);
			return "success";
		} else 
			return "fail";
	}

	// delete url
	public String delUrl() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String name[] = req.getParameterValues("id");
		HandleUrl hu = new HandleUrl();
		if (name != null && name.length > 0) {
			for(int i = 0; i < name.length; i++)
				hu.delete(name[i]);
			req.setAttribute("result", "success");
			page = hu.total();
			page = hu.firstPage(page);
			List url = new ArrayList();
			url = hu.findAll();
			req.setAttribute("url", url);
			return "success";
		} else 
			return "fail";
	}

	// delete user
	public String delUser() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String uname[] = req.getParameterValues("uname");
		HandleUsers hu = new HandleUsers();
		if (uname != null && uname.length > 0) {
			for(int i = 0; i < uname.length; i++)
				hu.delete(uname[i]);
			req.setAttribute("result", "success");
			page = hu.total();
			page = hu.firstPage(page);
			List list = new ArrayList();
			list = hu.findAll();
			req.setAttribute("users", list);
			return "success";
		} else 
			return "fail";
	}
}
