package orz.xblog.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import orz.xblog.dto.Comment;
import orz.xblog.dto.Page;

public class HandleComment {
	// query all comment.
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	// query url by name
	public String findurl(String name) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		String curl = null;
		try {
			String sql = "select url from comment where commname = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name.replace("@", ""));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				curl = rs.getString("url");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return curl;
	}

	// query all comment by pagination
	public List<Comment> findBypage(Page page) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		int pageSize = page.getPageSize();
		int pageNow = page.getPageNow();
		List<Comment> list = new ArrayList();
		try {
			String sql = "select * from comment limit ? , ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pageNow * pageSize - pageSize);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Comment co = new Comment();
				String sql1 = "select title from  essay where essayid=?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, rs.getString("essayid"));
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					co.setTitle(rs1.getString(1));
				}
				co.setAvatar(rs.getString("avatar"));
				co.setCommid(rs.getString("commid"));
				co.setCdate(rs.getString("cdate"));
				co.setComment(rs.getString("comment"));
				co.setCommname(rs.getString("commname"));
				co.setUrl(rs.getString("url"));
				list.add(co);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	// query all page number.
	public Page total() {
		int totalNum = 0;
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		try {
			String sql = "select count(*) from comment";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Page page = new Page();
		page.setPageSize(8);
		int pageSize = page.getPageSize();
		int lastPage = totalNum / pageSize;
		if (totalNum % pageSize == 0) {
			page.setLastPage(lastPage);
		}
		if (totalNum % pageSize != 0) {
			page.setLastPage(lastPage + 1);
		}
		page.setTotalNum(totalNum);
		page.setPageNow(1);
		return page;
	}

	public Page firstPage(Page page) {
		page.setPageNow(1);
		return page;
	}

	public Page lastPage(Page page) {
		page.setPageNow(page.getLastPage());
		return page;
	}

	public List findAll() {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		List list = new ArrayList();
		try {
			String sql = "select * from  comment";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Comment co = new Comment();
				String sql1 = "select title from  essay where essayid=?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, rs.getString("essayid"));
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					co.setTitle(rs1.getString(1));
				}
				co.setAvatar(rs.getString("avatar"));
				co.setCommid(rs.getString("commid"));
				co.setCdate(rs.getString("cdate"));
				co.setComment(rs.getString("comment"));
				co.setCommname(rs.getString("commname"));
				co.setUrl(rs.getString("url"));
				list.add(co);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	// query user by essayid.
	public List findbyid(String id) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		List list = new ArrayList();
		HandleComment hc = new HandleComment();
		try {
			String sql = "select * from  comment where essayid=? order by cdate desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int i = 0;
				int j = 0;
				String subcomm = "";
				String newstr = "";
				Comment co = new Comment();
				if (rs.getString("avatar") == null || rs.getString("avatar").equals(""))
					co.setAvatar("avatar.png");
				else
					co.setAvatar(rs.getString("avatar"));
				co.setCdate(rs.getString("cdate"));
				String comm = rs.getString("comment");
				if (comm.indexOf("@") != -1) {
					while (i != -1) {
						i = comm.indexOf("@");
						j = comm.indexOf(" ");
						if (i == -1 || j == -1)
							break;
						String tem = comm.substring(i, j);
						subcomm = comm.replace(tem + " ", "");
						comm = subcomm;// 每次截取后更新字符串。
						String url = hc.findurl(tem);
						newstr += "<a href=" + url + ">" + tem + " </a>";
					}
					comm = newstr + subcomm;
				} else
					comm = rs.getString("comment");
				co.setComment(comm);
				co.setCommname(rs.getString("commname"));
				co.setUrl(rs.getString("url"));
				list.add(co);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	// delete comment by id
	public boolean delete(String id) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "delete from comment where commid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int res = pstmt.executeUpdate();
			if (res != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	// delete comment by essayid
	public boolean delByes(String id) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "delete from comment where essayid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int res = pstmt.executeUpdate();
			if (res != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	// add comments
	public boolean addecomm(Comment comment) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		Random random = new Random();
		String id = String.valueOf(random.nextInt());
		try {
			String sql = "insert into comment(commid,avatar,comment,commname,cdate,essayid,email,url) values (?, ?, ?, ?, NOW(), ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, comment.getAvatar());
			pstmt.setString(3, comment.getComment());
			pstmt.setString(4, comment.getCommname());
			pstmt.setString(5, comment.getEssayid());
			pstmt.setString(6, comment.getEmail());
			pstmt.setString(7, comment.getUrl());
			int res = pstmt.executeUpdate();
			if (res != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}
}
