package orz.xblog.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Page;
import orz.xblog.task.HandleComment;
import orz.xblog.task.HandleUsers;

import com.opensymphony.xwork2.ActionSupport;

public class Commpage extends ActionSupport {
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
		HandleComment hc = new HandleComment();
		page = hc.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		page = hc.firstPage(page); // ���õ�ǰҳ��Ϊ��һҳ
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = hc.findBypage(page);
		req.setAttribute("comment", list); // ���ҵ�һҳ������
		return SUCCESS;
	}

	public String pre() throws Exception {
		HandleComment hc = new HandleComment();
		page = hc.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		if (pageNow < 1) { // ҳ��С��1ʱ ��ǰҳ������ȻΪ1
			pageNow = 1;
		}
		page.setPageNow(pageNow); // ���õ�ǰ��ҳ��
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = hc.findBypage(page); // ���ҵ�ǰҳ��������
		req.setAttribute("comment", list);
		return SUCCESS;
	}

	public String next() throws Exception {
		HandleComment hc = new HandleComment();
		page = hc.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		if (pageNow < 2) { // �����ǰҳ��С��2��һҳ������Ч����һҳ�ĵ�һ����ǰҳ��Ϊ�ڶ�ҳ
			pageNow = 2;
		}
		if (pageNow > page.getLastPage()) {
			pageNow = page.getLastPage(); // �����ǰҳ����������ҳ�����ǵ�ǰҳ����Զ�����һҳ
		}
		page.setPageNow(pageNow);
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = hc.findBypage(page);
		req.setAttribute("comment", list); // ���ҵ�ǰҳ��������
		return SUCCESS;
	}

	public String last() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HandleComment hc = new HandleComment();
		page = hc.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		page = hc.lastPage(page); // ���õ�ǰҳ��Ϊ����Ҳ��
		List list = new ArrayList();
		list = hc.findBypage(page);
		req.setAttribute("comment", list); // �������һҳ����Ϣ
		return SUCCESS;
	}
}
