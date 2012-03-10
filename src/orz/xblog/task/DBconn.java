package orz.xblog.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBconn {
	public Connection getConn() {
		Connection conn = null;
		ResourceBundle bundle = ResourceBundle.getBundle("orz.xblog.task.mysql");
		String dbDriver = bundle.getString("dbDriver");
		String dbURL = bundle.getString("dbURL");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		
		try {
			// 1. 加载驱动
			Class.forName(dbDriver);

			// 2.建立连接
			conn = DriverManager.getConnection(dbURL, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
