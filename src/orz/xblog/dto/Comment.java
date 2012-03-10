package orz.xblog.dto;


public class Comment {
	private String commid;
	private String commname;
	private String essayid;
	private String comment;
	private String cdate;
	private String email;
	private String avatar;
	private String title;
	private String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getCommid() {
		return commid;
	}
	public void setCommid(String commid) {
		this.commid = commid;
	}
	public String getCommname() {
		return commname;
	}
	public void setCommname(String commname) {
		this.commname = commname;
	}
	public String getEssayid() {
		return essayid;
	}
	public void setEssayid(String essayid) {
		this.essayid = essayid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
