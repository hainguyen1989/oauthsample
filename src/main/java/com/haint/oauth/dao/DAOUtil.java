/**
 * 
 */
package com.haint.oauth.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HAINT
 *
 */
public class DAOUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(DAOUtil.class);

	private static final String MYSQL_CONNECTION_STRING = "jdbc:mysql://%s:%d/%s";
	private static final String DB_SERVER = "localhost";
	private static final int DB_PORT = 3306;
	private static final String DB_NAME = "oauth_demo";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "admin1234";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.error(">>> MySQL JDBC Driver is missing!");
		}

		try {
			String connectionString = String.format(MYSQL_CONNECTION_STRING, DB_SERVER, DB_PORT, DB_NAME);
			connection = DriverManager.getConnection(connectionString, DB_USERNAME, DB_PASSWORD);

		} catch (SQLException e) {
			LOGGER.error(">>> Connection Failed! Check output console: " + e.getMessage());
		}

		if (connection == null) {
			LOGGER.error(">>> Failed to make connection!");
		}

		return connection;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] argv) {
		getConnection(); // test
	}
}
