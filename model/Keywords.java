package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Keywords
{
	public static final String dbName = "jdbc:sqlite:keywords.db";
	public static final String tableName = "keywords";

	public static void setKeywords(String path, String keywords)
	{
		try {
			Connection c = DBConnection.getConnection();
			if (c == null)
				return;

			PreparedStatement statement = c.prepareStatement("insert or replace into " + tableName + " (path, keywords)"
					+ " values(?, ?)");
			statement.setQueryTimeout(10);
			statement.setString(1, path);
			statement.setString(2, keywords);
			statement.executeUpdate();
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}

	public static String getkeywords(String path)
	{
		String result = "";

		try {
			Connection c = DBConnection.getConnection();
			if (c == null)
				return result;

			Statement statement = c.createStatement();
			statement.setQueryTimeout(10);

			ResultSet rs = statement.executeQuery(
					"select keywords from " + tableName + " where path = '" + path + "'");

			if (rs.next())
				result = rs.getString("keywords");
		}
		catch (Exception e) {
			System.err.println(e);
		}

		return result;
	}
}
