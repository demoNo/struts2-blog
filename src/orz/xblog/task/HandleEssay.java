package orz.xblog.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import orz.xblog.dto.Essay;
import orz.xblog.dto.Page;
import orz.xblog.dto.Tags;

import com.petebevin.markdown.MarkdownProcessor;

public class HandleEssay {
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	// query all essays and comment.
	public List findalles() {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		List list = new ArrayList();
		List list1 = new ArrayList();
		try {
			String sql = "select * from  essay order by date desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			HandleTag ht = new HandleTag();
			while (rs.next()) {
				Essay es = new Essay();
				String sql1 = "select count(*) from  comment where essayid = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, rs.getString("essayid"));
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					es.setComments(rs1.getInt(1));
				}
				list1 = ht.findtagbyid(rs.getString("essayid"));
				es.setTag(list1);
				es.setEssayid(rs.getString("essayid"));
				es.setTitle(rs.getString("title"));
				es.setAuthor(rs.getString("author"));
				es.setClassification(rs.getString("classification"));
				es.setDate(rs.getString("date"));
				list.add(es);
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

	// query previous by date
	public Essay findpre(String date) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		List list1 = new ArrayList();
		HandleTag ht = new HandleTag();
		Essay es = new Essay();
		try {
			String sql = "select date,title,essayid from essay where date>? order by date limit 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				es.setDate(rs.getString("date"));
				es.setEssayid(rs.getString("essayid"));
				es.setTitle(rs.getString("title"));
				list1 = ht.findtagbyid(rs.getString("essayid"));
				es.setTag(list1);
			}
			return es;
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
		return es;
	}

	// query next by date.
	public Essay findnext(String date) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		Essay es = new Essay();
		List list1 = new ArrayList();
		HandleTag ht = new HandleTag();
		try {
			String sql = "select date,title,essayid from essay where date<? order by date desc limit 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				es.setDate(rs.getString("date"));
				es.setEssayid(rs.getString("essayid"));
				es.setTitle(rs.getString("title"));
				list1 = ht.findtagbyid(rs.getString("essayid"));
				es.setTag(list1);
			}
			return es;
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
		return es;
	}

	// query essay page by search or tag.
	public List<Essay> findtagBypage(Page page, String tag) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		int pageSize = page.getPageSize();
		int pageNow = page.getPageNow();
		List list1 = new ArrayList();
		HandleTag ht = new HandleTag();
		List<Essay> list = new ArrayList();
		try {
			String sql = "select * from essay where content like ?  or title like ? or classification like ? or author like ? or summary like ? order by date desc limit ? , ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + tag + "%");
			pstmt.setString(2, "%" + tag + "%");
			pstmt.setString(3, "%" + tag + "%");
			pstmt.setString(4, "%" + tag + "%");
			pstmt.setString(5, "%" + tag + "%");
			pstmt.setInt(6, pageNow * pageSize - pageSize);
			pstmt.setInt(7, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Essay es = new Essay();
				String sql1 = "select count(*) from  comment where essayid = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, rs.getString("essayid"));
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					es.setComments(rs1.getInt(1));
				}
				es.setThumbnail(rs.getString("thumbnail"));
				es.setSummary(rs.getString("summary"));
				es.setEssayid(rs.getString("essayid"));
				es.setTitle(rs.getString("title"));
				es.setAuthor(rs.getString("author"));
				es.setClassification(rs.getString("classification"));
				es.setDate(rs.getString("date"));
				list1 = ht.findtagbyid(rs.getString("essayid"));
				es.setTag(list1);
				list.add(es);
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

	// query essay in by pagination
	public List<Essay> findBypage(Page page) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		int pageSize = page.getPageSize();
		int pageNow = page.getPageNow();
		List<Essay> list = new ArrayList();
		List list1 = new ArrayList();
		HandleTag ht = new HandleTag();
		try {
			String sql = "select * from essay order by date desc limit ? , ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pageNow * pageSize - pageSize);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Essay es = new Essay();
				String sql1 = "select count(*) from  comment where essayid = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, rs.getString("essayid"));
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					es.setComments(rs1.getInt(1));
				}
				es.setThumbnail(rs.getString("thumbnail"));
				es.setSummary(rs.getString("summary"));
				// es.setStatus(rs.getString("status"));
				es.setEssayid(rs.getString("essayid"));
				es.setTitle(rs.getString("title"));
				es.setAuthor(rs.getString("author"));
				es.setClassification(rs.getString("classification"));
				es.setDate(rs.getString("date"));
				list1 = ht.findtagbyid(rs.getString("essayid"));
				es.setTag(list1);
				list.add(es);
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

	// query essay page by class
	public List<Essay> findclassBypage(Page page, String clas) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		int pageSize = page.getPageSize();
		int pageNow = page.getPageNow();
		List list1 = new ArrayList();
		HandleTag ht = new HandleTag();
		List<Essay> list = new ArrayList();
		try {
			String sql = "select * from essay where classification = ? order by date desc limit ? , ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, clas);
			pstmt.setInt(2, pageNow * pageSize - pageSize);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Essay es = new Essay();
				String sql1 = "select count(*) from  comment where essayid = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, rs.getString("essayid"));
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					es.setComments(rs1.getInt(1));
				}
				es.setThumbnail(rs.getString("thumbnail"));
				es.setSummary(rs.getString("summary"));
				// es.setStatus(rs.getString("status"));
				es.setEssayid(rs.getString("essayid"));
				es.setTitle(rs.getString("title"));
				es.setAuthor(rs.getString("author"));
				es.setClassification(rs.getString("classification"));
				es.setDate(rs.getString("date"));
				list1 = ht.findtagbyid(rs.getString("essayid"));
				es.setTag(list1);
				list.add(es);
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
			String sql = "select count(*) from essay";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Page page = new Page();
		page.setPageSize(6);
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

	// query all page number in class.
	public Page ctotal(String clas) {
		int totalNum = 0;
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		try {
			String sql = "select count(*) from essay where classification = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, clas);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Page page = new Page();
		page.setPageSize(6);
		int pageSize = page.getPageSize();
		int lastPage = totalNum / pageSize;
		if (totalNum % pageSize == 0) {
			page.setLastPage(lastPage);
		}
		if (totalNum % pageSize != 0) {
			page.setLastPage(lastPage + 1);
		}
		// System.out.println(pageSize);
		page.setTotalNum(totalNum);
		page.setPageNow(1);
		return page;
	}

	// query all page number in search.
	public Page stotal(String search) {
		int totalNum = 0;
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		try {
			String sql = "select count(*) from essay where content like ?  or title like ? or classification like ? or author like ? or summary like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setString(2, "%" + search + "%");
			pstmt.setString(3, "%" + search + "%");
			pstmt.setString(4, "%" + search + "%");
			pstmt.setString(5, "%" + search + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Page page = new Page();
		page.setPageSize(6);
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

	// query all essay.
	public List findAll() {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		List list = new ArrayList();
		List list1 = new ArrayList();
		HandleTag ht = new HandleTag();
		try {
			String sql = "select * from  essay order by date desc limit 0,6";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Essay es = new Essay();
				String sql1 = "select count(*) from  comment where essayid = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, rs.getString("essayid"));
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					es.setComments(rs1.getInt(1));
				}
				es.setThumbnail(rs.getString("thumbnail"));
				es.setSummary(rs.getString("summary"));
				// es.setStatus(rs.getString("status"));
				es.setEssayid(rs.getString("essayid"));
				es.setTitle(rs.getString("title"));
				es.setAuthor(rs.getString("author"));
				es.setClassification(rs.getString("classification"));
				es.setDate(rs.getString("date"));
				list1 = ht.findtagbyid(rs.getString("essayid"));
				es.setTag(list1);
				list.add(es);
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

	// query title by class.
	public List findclass() {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		List list = new ArrayList();
		ResultSet rs = null;
		try {
			String sql = "select distinct(classification) from  essay";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Essay es = new Essay();
				es.setClassification(rs.getString("classification"));
				list.add(es);
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

	// query content by id.
	public List findbyid(String id) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		MarkdownProcessor mp = new MarkdownProcessor();
		List list = new ArrayList();
		List list1 = new ArrayList();
		HandleTag ht = new HandleTag();
		try {
			String sql = "select * from  essay where essayid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Essay es = new Essay();
				String sql1 = "select count(*) from  comment where essayid = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, rs.getString("essayid"));
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					es.setComments(rs1.getInt(1));
				}
				es.setThumbnail(rs.getString("thumbnail"));
				es.setHtml(mp.markdown(rs.getString("content")));
				es.setSummary(rs.getString("summary"));
				es.setStatus(rs.getString("status"));
				es.setEssayid(rs.getString("essayid"));
				es.setContent(rs.getString("content"));
				es.setTitle(rs.getString("title"));
				es.setAuthor(rs.getString("author"));
				es.setClassification(rs.getString("classification"));
				es.setDate(rs.getString("date"));
				list1 = ht.findtagbyid(rs.getString("essayid"));
				es.setTag(list1);
				list.add(es);
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

	// delete content by id
	public boolean delete(String id) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "delete from essay where essayid=?";
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
			// 3. close resource
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

	// add content
	public boolean addessay(Essay essay) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "insert into essay(author,classification,content,date,title,essayid,status,summary,thumbnail) values (?, ?, ?, NOW(), ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, essay.getAuthor());
			pstmt.setString(2, essay.getClassification());
			pstmt.setString(3, essay.getContent());
			pstmt.setString(4, essay.getTitle());
			pstmt.setString(5, essay.getEssayid());
			pstmt.setString(6, essay.getStatus());
			pstmt.setString(7, essay.getSummary());
			pstmt.setString(8, essay.getThumbnail());
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

	// update content by id
	public boolean update(Essay essay) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		String sql = "update essay set title=?,content=?,classification=?,status=?,summary=?,thumbnail=?  where essayid=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, essay.getTitle());
			pstmt.setString(2, essay.getContent());
			pstmt.setString(3, essay.getClassification());
			pstmt.setString(4, essay.getStatus());
			pstmt.setString(5, essay.getSummary());
			pstmt.setString(6, essay.getThumbnail());
			pstmt.setString(7, essay.getEssayid());
			int res = pstmt.executeUpdate();
			if (res != 0) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
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
		return false;
	}

	// query essay by username
	public List findesByname(String name) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		MarkdownProcessor mp = new MarkdownProcessor();
		List list = new ArrayList();
		try {
			String sql = "select * from  essay where author=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Essay es = new Essay();
				es.setHtml(mp.markdown(rs.getString("content")));
				es.setThumbnail(rs.getString("thumbnail"));
				es.setSummary(rs.getString("summary"));
				es.setStatus(rs.getString("status"));
				es.setEssayid(rs.getString("essayid"));
				es.setContent(rs.getString("content"));
				es.setTitle(rs.getString("title"));
				es.setAuthor(rs.getString("author"));
				es.setClassification(rs.getString("classification"));
				es.setDate(rs.getString("date"));
				list.add(es);
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

}
