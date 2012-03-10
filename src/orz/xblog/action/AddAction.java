package orz.xblog.action;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Essay;
import orz.xblog.dto.Page;
import orz.xblog.dto.Tags;
import orz.xblog.dto.Url;
import orz.xblog.dto.Users;
import orz.xblog.task.HandleEssay;
import orz.xblog.task.HandleTag;
import orz.xblog.task.HandleUrl;
import orz.xblog.task.HandleUsers;
import sun.misc.BASE64Encoder;

import com.opensymphony.xwork2.ActionSupport;

public class AddAction extends ActionSupport {

	private Essay essay;
	private Users user;
	private Url url;
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

	public Essay getEssay() {
		return essay;
	}

	public void setEssay(Essay essay) {
		this.essay = essay;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

	// add essay.
	public String addEssay() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		HandleEssay he = new HandleEssay();
		Essay es = new Essay();
		Random random = new Random();
		String id = String.valueOf(random.nextInt());
		Tags t = new Tags();
		String thumb = req.getParameter("image");
		String tag = req.getParameter("tag");
		if(tag != ""){
			HandleTag ht = new HandleTag();
			String gt[] = tag.split(",");
			for(int i = 0; i < gt.length; i++){
				t.setTag(gt[i]);
				t.setEssayid(id);
				ht.addtag(t);
			}
		}
		es.setEssayid(id);
		es.setThumbnail(thumb);
		es.setAuthor(session.getAttribute("login").toString());
		es.setClassification(essay.getClassification());
		es.setContent(essay.getContent());
		es.setTitle(essay.getTitle());
		es.setSummary(essay.getSummary());
		es.setStatus(essay.getStatus());
		if (he.addessay(es)) {
			req.setAttribute("result", "ok");
			HandleUsers hu = new HandleUsers();
			if (hu.isAdmin(session.getAttribute("login").toString())) {
				List list = new ArrayList();
				page = he.total();
				page = he.firstPage(page);
				list = he.findAll();
				req.setAttribute("list", list);
				return "admin";
			} else {
				List list = new ArrayList();
				list = he.findesByname(session.getAttribute("login").toString());
				session.setAttribute("uessay", list);
				return "user";
			}
		} else
			return "fail";
	}

	// add user.
	public String addUser() throws Exception {
		MessageDigest md5=MessageDigest.getInstance("MD5");  
	    BASE64Encoder base64en = new BASE64Encoder();  
		Users us = new Users();
		us.setUsername(user.getUsername());
		us.setEmail(user.getEmail());
		us.setSex(user.getSex());
		String pwd = user.getPassword();
		String md5pwd = base64en.encode(md5.digest(pwd.getBytes("utf-8")));
		us.setPassword(md5pwd);
		HandleUsers hu = new HandleUsers();
		if (hu.adduser(us))
			return "success";
		else
			return "fail";
	}

	// add url.
	public String addUrl() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		Url ur = new Url();
		ur.setUrl(url.getUrl());
		ur.setUrlname(url.getUrlname());
		HandleUrl hu = new HandleUrl();
		if (hu.addurl(ur)) {
			req.setAttribute("result", "ok");
			page = hu.total();
			page = hu.firstPage(page);
			List<Url> list = hu.findAll();
			req.setAttribute("url", list);
			return "success";
		} else {
			req.setAttribute("result", "fail");
			List<Url> list = hu.findAll();
			req.setAttribute("url", list);
			return "fail";
		}
	}
}
