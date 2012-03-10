package orz.xblog.action;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Essay;
import orz.xblog.dto.Page;
import orz.xblog.dto.Tags;
import orz.xblog.dto.Users;
import orz.xblog.task.HandleEssay;
import orz.xblog.task.HandleTag;
import orz.xblog.task.HandleUsers;
import sun.misc.BASE64Encoder;

import com.opensymphony.xwork2.ActionSupport;

public class UpAction extends ActionSupport {

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
	private Essay essay;
	private Users user;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Essay getEssay() {
		return essay;
	}

	public void setEssay(Essay essay) {
		this.essay = essay;
	}

	// update essay by id.
	public String upEssay() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String thumb = req.getParameter("image");
		HandleEssay he = new HandleEssay();
		Essay es = new Essay();
		es.setThumbnail(thumb);
		es.setClassification(essay.getClassification());
		es.setContent(essay.getContent());
		es.setTitle(essay.getTitle());
		es.setSummary(essay.getSummary());
		es.setEssayid(essay.getEssayid());
		es.setStatus(essay.getStatus());
		if(req.getParameter("tag") != ""){
			Tags t = new Tags();
			HandleTag ht = new HandleTag();
			ht.delete(essay.getEssayid());
			String tag = req.getParameter("tag");
			String gt[] = tag.split(",");
			for(int i = 0; i < gt.length; i++){
				t.setTag(gt[i]);
				t.setEssayid(essay.getEssayid());
				ht.addtag(t);
			}
		}
		if (he.update(es)) {
			session.removeAttribute("uessay");
			HandleUsers hu = new HandleUsers();
			req.setAttribute("result", "ok");
			if (hu.isAdmin(session.getAttribute("login").toString())) {
				page = he.total();
				page = he.firstPage(page);
				List<Essay> list = he.findAll();
				List<Essay> list1 = he.findesByname(session.getAttribute("login").toString());
				req.setAttribute("usr", list1);
				req.setAttribute("list", list);
				return "admin";
			} else {
				List<Essay> list = he.findesByname(session.getAttribute("login").toString());
				session.setAttribute("uessay", list);
				return "user";
			}
		} else
			return "fail";
	}

	// update user detail.
	public String upUser() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		Users us = new Users();
		us.setUsername(user.getUsername());
		us.setEmail(user.getEmail());
		us.setSex(user.getSex());
		HandleUsers hu = new HandleUsers();
		if (hu.update(user)) {
			session.removeAttribute("usr");
			if (hu.isAdmin(user.getUsername())) {
				req.setAttribute("result", "ok");
				page = hu.total();
				page = hu.firstPage(page);
				List<Users> list1 = hu.findAll();
				List<Users> list = hu.findbyid(user.getUsername());
				req.setAttribute("users", list1);
				session.setAttribute("usr", list);
				return "admin";
			} else {
				req.setAttribute("result", "ok");
				List<Users> list = hu.findbyid(user.getUsername());
				session.setAttribute("usr", list);
				return "user";
			}
		} else
			return "fail";
	}

	// update user password
	public String upUserpwd() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		String olpwd = base64en.encode(md5.digest(req.getParameter("oldpwd").trim().getBytes("utf-8")));
		String newpwd = base64en.encode(md5.digest(user.getPassword().trim().getBytes("utf-8")));
		String uname = session.getAttribute("login").toString();
		HandleUsers hu = new HandleUsers();
		if (hu.isAdmin(uname)) {
			if (hu.isUser(uname, olpwd) && hu.updatepwd(newpwd, uname)) {
				req.setAttribute("isok", "ok");
				return "admin";
			} else {
				req.setAttribute("isok", "fail");
				return "admin";
			}
		} else if (!hu.isAdmin(uname)) {
			if (hu.isUser(uname, olpwd) && hu.updatepwd(newpwd, uname)) {
				List list = new ArrayList();
				list = hu.findbyid(uname);
				req.setAttribute("usr", list);
				req.setAttribute("isok", "ok");
				return "user";
			} else {
				List list = new ArrayList();
				list = hu.findbyid(uname);
				req.setAttribute("usr", list);
				req.setAttribute("isok", "fail");
				return "user";
			}
		} else
			return "fail";
	}

	// get essay by id.
	public String getEss() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String essayid = req.getParameter("id");
		HandleEssay he = new HandleEssay();
		List<Essay> list = he.findbyid(essayid);
		req.setAttribute("essay", list);
		return "success";
	}

	// get user by username.
	public String getUs() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		if(session.getAttribute("login") == null)
			return "fail";
		String username = new String(req.getParameter("uname").getBytes(
				"iso-8859-1"), "utf-8");
		HandleUsers hu = new HandleUsers();
		if (hu.isAdmin(username)) {
			page = hu.total();
			page = hu.firstPage(page);
			List<Users> list = hu.findbyid(username);
			List<Users> list1 = hu.findBypage(page);		
			req.setAttribute("users", list1);
			req.setAttribute("usr", list);
			return "admin";
		} else {
			return "success";
		}
	}
}
