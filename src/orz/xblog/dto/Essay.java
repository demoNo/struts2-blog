package orz.xblog.dto;

import java.util.List;

public class Essay {
	private List<Tags> Tag;
	private String essayid;
	private String title;
	private String content;
	private String date;
	private String classification;
	private String author;
	private String status;
	private String html;
	private String summary;
	private String thumbnail;
	private int Comments;
	
	public List<Tags> getTag() {
		return Tag;
	}
	public void setTag(List<Tags> tag) {
		Tag = tag;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEssayid() {
		return essayid;
	}
	public void setEssayid(String essayid) {
		this.essayid = essayid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getComments() {
		return Comments;
	}
	public void setComments(int comments) {
		Comments = comments;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
