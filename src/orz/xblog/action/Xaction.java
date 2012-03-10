package orz.xblog.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Comment;
import orz.xblog.dto.Essay;
import orz.xblog.dto.Page;
import orz.xblog.dto.Tags;
import orz.xblog.task.HandleComment;
import orz.xblog.task.HandleEssay;
import orz.xblog.task.HandleTag;
import orz.xblog.task.HandleUrl;
import orz.xblog.task.HandleUsers;

import com.opensymphony.xwork2.ActionSupport;

public class Xaction extends ActionSupport {

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

	public String index() throws Exception {
		List list = new ArrayList();
		List list1 = new ArrayList();
		List link = new ArrayList();
		List ltag = new ArrayList();
		HandleTag ht = new HandleTag();
		ltag = ht.findtag();
		HandleUrl hu = new HandleUrl();
		HandleEssay he = new HandleEssay();
		page = he.total();
		page = he.firstPage(page);
		list = he.findAll();
		list1 = he.findclass();
		link = hu.findAll();
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		req.setAttribute("list", list);
		session.setAttribute("tags", ltag);
		session.setAttribute("detail", list1);
		session.setAttribute("link", link);
		return SUCCESS;
	}
	
	public String sitemap() throws Exception {
		List list = new ArrayList();
		HandleEssay he = new HandleEssay();
		list = he.findalles();
		HttpServletRequest req = ServletActionContext.getRequest();
		req.setAttribute("sitemap", list);
		return SUCCESS;
	}

	public String main() throws Exception {
		List list = new ArrayList();
		HandleEssay he = new HandleEssay();
		HttpServletRequest req = ServletActionContext.getRequest();
		String id = req.getParameter("id");
		String date = req.getParameter("date");
		list = he.findbyid(id);
		Essay pre = new Essay(); // 查询上一篇文章
		Essay next = new Essay(); // 查询下一篇文章
		pre = he.findpre(date);
		next = he.findnext(date);
		req.setAttribute("pre", pre);
		req.setAttribute("next", next);
		req.setAttribute("content", list);
		HandleComment hc = new HandleComment();
		List list1 = new ArrayList();
		list1 = hc.findbyid(id);
		req.setAttribute("comment", list1);
		return "success";
	}

	public String addcomm() throws Exception {
		// 获取参数
		HttpServletRequest req = ServletActionContext.getRequest();
		String essayid = req.getParameter("id");
		String date = req.getParameter("edate");
		String avatar = req.getParameter("ava");
		HandleEssay he = new HandleEssay();
		Essay pre = new Essay();
		Essay next = new Essay();
		pre = he.findpre(date);
		next = he.findnext(date);
		String cname = req.getParameter("commname");
		String email = req.getParameter("email");
		String comment = req.getParameter("comment");
		Comment co = new Comment();
		HandleComment hc = new HandleComment();
		String url = req.getParameter("url");
		// 添加评论
		co.setComment(comment);
		co.setEmail(email);
		co.setCommname(cname);
		co.setEssayid(essayid);
		co.setUrl(url);
		co.setAvatar(avatar);
		hc.addecomm(co);
		// 重新获取
		List list = new ArrayList();
		req.setAttribute("pre", pre);
		req.setAttribute("next", next);
		list = he.findbyid(essayid);
		req.setAttribute("content", list);
		List list1 = new ArrayList();
		list1 = hc.findbyid(essayid);
		req.setAttribute("comment", list1);
		return "success";
	}
}
