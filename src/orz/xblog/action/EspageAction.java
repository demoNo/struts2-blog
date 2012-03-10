package orz.xblog.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import orz.xblog.dto.Page;
import orz.xblog.task.HandleEssay;

import com.opensymphony.xwork2.ActionSupport;

public class EspageAction extends ActionSupport {
	
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
		HandleEssay he = new HandleEssay();
		page = he.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		page = he.firstPage(page); // ���õ�ǰҳ��Ϊ��һҳ
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = he.findBypage(page);
		req.setAttribute("list", list); // ���ҵ�һҳ������
		return SUCCESS;
	}

	public String pre() throws Exception {
		HandleEssay he = new HandleEssay();
		page = he.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		if (pageNow < 1) { // ҳ��С��1ʱ ��ǰҳ������ȻΪ1
			pageNow = 1;
		}
		page.setPageNow(pageNow); // ���õ�ǰ��ҳ��
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = he.findBypage(page); // ���ҵ�ǰҳ��������
		req.setAttribute("list", list);
		return SUCCESS;
	}

	public String next() throws Exception {
		HandleEssay he = new HandleEssay();
		page = he.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		if (pageNow < 2) { // �����ǰҳ��С��2��һҳ������Ч����һҳ�ĵ�һ����ǰҳ��Ϊ�ڶ�ҳ
			pageNow = 2;
		}
		if (pageNow > page.getLastPage()) {
			pageNow = page.getLastPage(); // �����ǰҳ����������ҳ�����ǵ�ǰҳ����Զ�����һҳ
		}
		page.setPageNow(pageNow);
		HttpServletRequest req = ServletActionContext.getRequest();
		List list = new ArrayList();
		list = he.findBypage(page);
		req.setAttribute("list", list); // ���ҵ�ǰҳ��������
		return SUCCESS;
	}

	public String last() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HandleEssay he = new HandleEssay();
		page = he.total(); // ���ҳ������Ϣ�統ǰҳ�� һҳ������������¼��
		page = he.lastPage(page); // ���õ�ǰҳ��Ϊ����Ҳ��
		List list = new ArrayList();
		list = he.findBypage(page);
		req.setAttribute("list", list); // �������һҳ����Ϣ
		return SUCCESS;
	}
}
