package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private static Connection con = null;

	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				con = DriverManager.getConnection(Keywords.dbName);

				Statement statement = con.createStatement();
				statement.setQueryTimeout(10);

				statement.executeUpdate("create table if not exists " + Keywords.tableName
						+ " (path text primary key, keywords text)");
			} catch (Exception e) {
				System.err.println(e.getMessage());
				closeConnection();
			}
		}

		return con;
	}

	public static void closeConnection() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
