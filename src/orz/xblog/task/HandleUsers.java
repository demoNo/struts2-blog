package orz.xblog.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import orz.xblog.dto.Essay;
import orz.xblog.dto.Page;
import orz.xblog.dto.Users;

public class HandleUsers {

	private Connection conn = null;
	private PreparedStatement pstmt = null;

	// query all users by pagination
	public List<Users> findBypage(Page page) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		int pageSize = page.getPageSize();
		int pageNow = page.getPageNow();
		List<Users> list = new ArrayList();
		try {
			String sql = "select * from users limit ? , ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pageNow * pageSize - pageSize);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Users user = new Users();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setSex(rs.getString("sex"));
				user.setUsername(rs.getString("username"));
				list.add(user);
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
			String sql = "select count(*) from users";
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

	// query is user.
	public boolean isUser(String uname, String password) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		try {
			String sql = "select username from users where username = ? and password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uname);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			while (rs.next()) {
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

	// query all users.
	public List findAll() {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			String sql = "select * from  users";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Users user = new Users();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setSex(rs.getString("sex"));
				user.setUsername(rs.getString("username"));
				list.add(user);
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

	// query user is admin or not
	public boolean isAdmin(String username) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		try {
			String sql = "select admin from users where username=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			// System.out.println(username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("admin") != null)
					return true;
				else
					return false;
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
		return false;
	}

	// query user by username.
	public List findbyid(String username) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			String sql = "select * from  users where username=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Users user = new Users();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setSex(rs.getString("sex"));
				user.setUsername(rs.getString("username"));
				list.add(user);
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

	// delete user
	public boolean delete(String username) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "delete from users where username=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
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

	// add user
	public boolean adduser(Users user) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		try {
			String sql = "insert into users(email,password,sex,username) values (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getSex());
			pstmt.setString(4, user.getUsername());
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

	// update user detail
	public boolean update(Users user) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		String sql = "update users set sex=?,email=? where username=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getSex());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getUsername());
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

	// update user password
	public boolean updatepwd(String pwd, String uname) {
		DBconn dconn = new DBconn();
		conn = dconn.getConn();
		String sql = "update users set password=? where username=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pwd);
			pstmt.setString(2, uname);
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
}
