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
	
	private Page page; // ����Page���ͱ���
	private int pageNow; // ��ǰҳ��

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
		req.setAttribute("users", list); // ���ҵ�һҳ������
		req.setAttribute("usr", list1);
		return SUCCESS;
	}

	public String pre() throws Exception {
		HandleUsers hu = new HandleUsers();
		page = hu.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		if (pageNow < 1) { // ҳ��С��1ʱ ��ǰҳ������ȻΪ1
			pageNow = 1;
		}
		page.setPageNow(pageNow); // ���õ�ǰ��ҳ��
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = hu.findBypage(page); // ���ҵ�ǰҳ��������
		req.setAttribute("users", list);
		return SUCCESS;
	}

	public String next() throws Exception {
		HandleUsers hu = new HandleUsers();
		page = hu.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		if (pageNow < 2) { // �����ǰҳ��С��2��һҳ������Ч����һҳ�ĵ�һ����ǰҳ��Ϊ�ڶ�ҳ
			pageNow = 2;
		}
		if (pageNow > page.getLastPage()) {
			pageNow = page.getLastPage(); // �����ǰҳ����������ҳ�����ǵ�ǰҳ����Զ�����һҳ
		}
		page.setPageNow(pageNow);
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = hu.findBypage(page);     // ���ҵ�ǰҳ��������
		req.setAttribute("users", list); 
		return SUCCESS;
	}

	public String last() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HandleUsers hu = new HandleUsers();
		page = hu.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		page = hu.lastPage(page); // ���õ�ǰҳ��Ϊ����Ҳ��
		List list = new ArrayList();
		list = hu.findBypage(page); // �������һҳ����Ϣ
		req.setAttribute("users", list); 
		return SUCCESS;
	}
}
