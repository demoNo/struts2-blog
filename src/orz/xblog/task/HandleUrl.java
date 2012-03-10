package orz.xblog.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import orz.xblog.dto.Page;
import orz.xblog.dto.Url;
import orz.xblog.dto.Users;

public class HandleUrl {
	// query all url.
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	// query all url by pagination
	public List<Url> findBypage(Page page) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		int pageSize = page.getPageSize();
		int pageNow = page.getPageNow();
		List<Url> list = new ArrayList();
		try {
			String sql = "select * from url limit ? , ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pageNow * pageSize - pageSize);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Url url = new Url();
				url.setUrl(rs.getString("url"));
				url.setUrlname(rs.getString("urlname"));
				list.add(url);
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
			String sql = "select count(*) from url";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Page page = new Page();
		page.setPageSize(10);
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
	
	//query all url.
	public List findAll() {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			String sql = "select * from  url";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Url url = new Url();
				url.setUrl(rs.getString("url"));
				url.setUrlname(rs.getString("urlname"));
				list.add(url);
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
	
	//delete url
	public boolean delete(String urlname) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "delete from url where urlname=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, urlname);
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
	
	//add url
	public boolean addurl(Url url) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "insert into url(url,urlname) values (?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, url.getUrl());
			pstmt.setString(2, url.getUrlname());
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
}
