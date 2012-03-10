package orz.xblog.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {

	private File upload;
	private String uploadFileName;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	// 上传原图
	public String upload() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String user = "";
		if(session.getAttribute("login") != null)
			user = session.getAttribute("login").toString();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setCharacterEncoding("utf-8");
		PrintWriter out = res.getWriter();
		String savePath = req.getSession().getServletContext().getRealPath(
				"/upload/");
		if (uploadFileName.indexOf(" ") != -1) {
			uploadFileName = uploadFileName.replace(" ", "_");
		}
		BufferedImage bi = ImageIO.read(upload);
		if (bi == null) {
			out.println("<script type=\"text/javascript\">parent.alert('不是图片文件!请上传jpg,jpeg,gif,png等图片文件.')</script>");
			return NONE;
		}
		String smallpic = null;
		if (req.getParameter("up_flag") != null && req.getParameter("up_flag").equals("flag")) {
			InputStream in = new FileInputStream(getUpload());
			File path = new File(savePath);
			if (!path.exists()) {
				path.mkdirs();
			}
			OutputStream os = new FileOutputStream(savePath + "/"
					+ uploadFileName);
			Thumbnails.of(upload).size(45, 45).toOutputStream(os);
			byte buffer[] = new byte[1024];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
			os.flush();
			os.close();
			in.close();
			out.println("<script type=\"text/javascript\">parent.callback('"
					+ uploadFileName + "')</script>");
			out.flush();
			out.close();
			return NONE;
		}
		if (req.getParameter("imgpre").equals("big") && upload != null) {
			File path = new File(savePath + "/" + user);
			if (!path.exists()) {
				path.mkdirs();
			}
			InputStream in = new FileInputStream(getUpload());
			OutputStream os = new FileOutputStream(path + "/" + uploadFileName);
			byte buffer[] = new byte[1024 * 2];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
			os.flush();
			os.close();
			in.close();
			smallpic = "small" + uploadFileName;
			Thumbnails.of(path + "/" + uploadFileName).size(90, 100).toFile(
					path + "/" + smallpic);
			out.println("<script type=\"text/javascript\">parent.callback('"
					+ smallpic + "')</script>");
			out.flush();
			out.close();
		} else if (req.getParameter("imgpre").equals("small") && upload != null) {
			InputStream in = new FileInputStream(getUpload());
			File path = new File(savePath);
			if (!path.exists()) {
				path.mkdirs();
			}
			OutputStream os = new FileOutputStream(savePath + "/"
					+ uploadFileName);
			Thumbnails.of(upload).size(140, 170).toOutputStream(os);
			byte buffer[] = new byte[1024 * 2];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
			os.flush();
			os.close();
			in.close();
			out.println("<script type=\"text/javascript\">parent.callback('"
					+ uploadFileName + "')</script>");
			out.flush();
			out.close();
		}
		return NONE;
	}
}
