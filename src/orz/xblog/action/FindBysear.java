package orz.xblog.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Page;
import orz.xblog.task.HandleEssay;
import com.opensymphony.xwork2.ActionSupport;

public class FindBysear extends ActionSupport {
	private Page page; 
	private int pageNow; 

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

	public String sindex() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		HandleEssay he = new HandleEssay();
		if (session.getAttribute("search") != null)
			session.removeAttribute("search");// ÿ�η����������ʱ������������.
		//String search = new String(req.getParameter("search").trim().getBytes("iso-8859-1"),"utf-8");
		String search = req.getParameter("search").trim();
		session.setAttribute("search", search);
		page = he.stotal(search);
		page = he.firstPage(page);
		List list = new ArrayList();
		list = he.findtagBypage(page, search);
		req.setAttribute("sessay", list);
		return SUCCESS;
	}

	public String first() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String search = session.getAttribute("search").toString();
		HandleEssay he = new HandleEssay();
		page = he.stotal(search); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		page = he.firstPage(page); // ���õ�ǰҳ��Ϊ��һҳ
		List list = new ArrayList();
		list = he.findtagBypage(page, search);
		req.setAttribute("sessay", list); // ���ҵ�һҳ������
		return SUCCESS;
	}

	public String pre() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String search = session.getAttribute("search").toString();
		HandleEssay he = new HandleEssay();
		page = he.stotal(search); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		if (pageNow < 1) { // ҳ��С��1ʱ ��ǰҳ������ȻΪ1
			pageNow = 1;
		}
		page.setPageNow(pageNow); // ���õ�ǰ��ҳ��
		List list = new ArrayList();
		list = he.findtagBypage(page, search); // ���ҵ�ǰҳ��������
		req.setAttribute("sessay", list);
		return SUCCESS;
	}

	public String next() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String search = session.getAttribute("search").toString();
		HandleEssay he = new HandleEssay();
		page = he.stotal(search); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		if (pageNow < 2) { // �����ǰҳ��С��2��һҳ������Ч����һҳ�ĵ�һ����ǰҳ��Ϊ�ڶ�ҳ
			pageNow = 2;
		}
		if (pageNow > page.getLastPage()) {
			pageNow = page.getLastPage(); // �����ǰҳ����������ҳ�����ǵ�ǰҳ����Զ�����һҳ
		}
		page.setPageNow(pageNow);
		List list = new ArrayList();
		list = he.findtagBypage(page, search);
		req.setAttribute("sessay", list); // ���ҵ�ǰҳ��������
		return SUCCESS;
	}

	public String last() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String search = session.getAttribute("search").toString();
		HandleEssay he = new HandleEssay();
		page = he.stotal(search); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		page = he.lastPage(page); // ���õ�ǰҳ��Ϊ����Ҳ��
		List list = new ArrayList();
		list = he.findtagBypage(page, search);
		req.setAttribute("sessay", list); // �������һҳ����Ϣ
		return SUCCESS;
	}
}
