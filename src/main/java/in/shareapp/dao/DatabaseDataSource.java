package in.shareapp.dao;

import java.sql.*;

public class DatabaseDataSource {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfEx) {
			cnfEx.printStackTrace();
		}
	}
	
	public Connection getDbConnection() {
		Connection dbCon = null;
		
		try {
			dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/shareapp", "root", "root");
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		
		return dbCon;
	}
	
	public void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}
	
	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}
	
	public void closeDbConnection(Connection dbCon) {
		try {
			if (dbCon != null) {
				dbCon.close();
			}
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}
}