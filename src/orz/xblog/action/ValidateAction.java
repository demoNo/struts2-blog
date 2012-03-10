package orz.xblog.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import orz.xblog.task.HandleUsers;

import com.opensymphony.xwork2.ActionSupport;

public class ValidateAction extends ActionSupport {

	public void validate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String fieldId = request.getParameter("fieldId");
		String fieldValue = request.getParameter("fieldValue");
		HandleUsers hu = new HandleUsers();
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if (hu.findbyid(fieldValue).isEmpty()) {
				pw.write("[\"" + fieldId + "\"," + true + "]");
			} else
				pw.write("[\"" + fieldId + "\"," + false + "]");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
}
