package orz.xblog.action;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Page;
import orz.xblog.dto.Users;
import orz.xblog.task.HandleEssay;
import orz.xblog.task.HandleUsers;
import sun.misc.BASE64Encoder;

import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport {
	private Users user;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String login() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpSession session = req.getSession();
		HandleUsers hu = new HandleUsers();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		String pwd = null;
		if (user.getPassword().length() > 12)
			pwd = user.getPassword();
		else
			pwd = base64en.encode(md5.digest(user.getPassword().trim().getBytes("utf-8")));
		if (user == null)
			return "fail";
		if (hu.isUser(user.getUsername().trim(), pwd)) {
			session.setAttribute("login", user.getUsername());
			if (req.getParameter("remember") != null) {
				Cookie users = new Cookie("userlog", user.getUsername() + "-" + pwd);
				users.setMaxAge(10 * 24 * 60 * 60);
				res.addCookie(users);
			}else {
				Cookie[] cookies = req.getCookies();
				if(cookies != null){
					Cookie cookie = new Cookie("userlog", null);  
					cookie.setMaxAge(0);
					res.addCookie(cookie);  
				}
			}
			if(hu.isAdmin(user.getUsername())){
				List list = new ArrayList();
				HandleEssay he = new HandleEssay();
				list = he.findAll();
				page = he.total();
				page = he.firstPage(page);
				req.setAttribute("list", list);
			}
			if (!hu.isAdmin(user.getUsername())) {
				HandleEssay he = new HandleEssay();
				List list = new ArrayList();
				List list1 = new ArrayList();
				list1 = he.findesByname(user.getUsername());
				list = hu.findbyid(user.getUsername());
				session.setAttribute("uessay", list1);
				session.setAttribute("usr", list);
				return "user";
			} else
				return "success";
		} else {
			session.setAttribute("flag", "fail");
			return "fail";
		}
	}

	public String logout() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		session.removeAttribute("flag");
		if (session.getAttribute("login") != null) {
			session.removeAttribute("login");
			return "success";
		} else
			return "fail";
	}
}
