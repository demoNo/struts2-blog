package orz.xblog.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import orz.xblog.dto.Tags;

public class HandleTag {
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	public List findtag() {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		List list = new ArrayList();
		ResultSet rs = null;
		try {
			String sql = "select distinct(tag) from tags";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Tags t = new Tags();
				t.setTag(rs.getString("tag"));
				list.add(t);
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
	
	
	//find tag by essayid.
	public List findtagbyid(String id) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		List list = new ArrayList();
		ResultSet rs = null;
		try {
			String sql = "select tag from tags where essayid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Tags t = new Tags();
				t.setTag(rs.getString("tag"));
				list.add(t);
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
	
	
	//add tag 
	public boolean addtag(Tags t) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		Random random = new Random();
		String id = String.valueOf(random.nextInt());
		try {
			String sql = "insert into tags(essayid,tag) values ( ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getEssayid());
			pstmt.setString(2, t.getTag());
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
	
	//delete tag by id
	public boolean delete(String id) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "delete from tags where essayid=?";
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
}
