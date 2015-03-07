package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Keywords {
	public static final String dbName = "jdbc:sqlite:keywords.db";
	public static final String tableName = "keywords";

	public static void setKeywords(String path, String keywords) {
		try {
			Connection c = DBConnection.getConnection();
			if (c == null)
				return;

			PreparedStatement statement =
				c.prepareStatement("INSERT OR REPLACE INTO " + tableName + " (path, keywords)"
						+ " VALUES(?, ?)");
			statement.setQueryTimeout(10);
			statement.setString(1, path);
			statement.setString(2, keywords);
			statement.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static String getkeywords(String path) {
		String result = "";

		try {
			Connection c = DBConnection.getConnection();
			if (c == null)
				return result;

			PreparedStatement statement = c.prepareStatement(
					"SELECT keywords FROM " + tableName + " WHERE path = ?");
			statement.setString(1, path);
			statement.setQueryTimeout(10);

			ResultSet rs = statement.executeQuery();

			if (rs.next())
				result = rs.getString("keywords");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return result;
	}

	public static List<String> search(String str) {
		ArrayList<String> result = new ArrayList<String>();

		try {
			Connection c = DBConnection.getConnection();
			if (c == null)
				return null;

			PreparedStatement statement = c.prepareStatement(
					"SELECT path FROM " + tableName + " WHERE keywords LIKE ? COLLATE nocase");
			statement.setString(1, "%" + str + "%");
			statement.setQueryTimeout(10);

			ResultSet rs = statement.executeQuery();

			while (rs.next())
				result.add(rs.getString("path"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			result = null;
		}

		return result;
	}
}
