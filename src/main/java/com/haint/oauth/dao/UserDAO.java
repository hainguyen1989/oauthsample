/**
 * 
 */
package com.haint.oauth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.haint.oauth.model.User;

/**
 * @author HAINT
 *
 */
public class UserDAO {

	private final String SQL_INSERT_NORMAL_USER = "INSERT INTO tblUser(username,password,fullname,address,mobile) VALUES(?,?,?,?,?)";
	private final String SQL_INSERT_SN_USER = "INSERT INTO tblUser(username,fullname,facebookId,facebookLink) VALUES(?,?,?,?)";
	private final String SQL_SELECT_NORMAL_USER = "SELECT * FROM tblUser WHERE UPPER(username) = ? AND password = ?";
	private final String SQL_SELECT_SN_USER = "SELECT * FROM tblUser WHERE facebookId = ?";

	/**
	 * Register user into our system
	 * 
	 * @param user
	 * @param isSocial
	 * @return
	 */
	public boolean register(User user, boolean isSocial) {
		if (!isSocial)
			return register(user);
		else
			return registerSN(user);
	}

	/**
	 * Verify user login information
	 * 
	 * @param user
	 * @param isSocial
	 * @return
	 */
	public User checkLogin(User user, boolean isSocial) {
		if (!isSocial)
			return checkLogin(user.getUsername(), user.getPassword());
		else
			return checkLoginSN(user.getSocialId());
	}

	/**
	 * Register normal user
	 * 
	 * @param user
	 * @return
	 */
	private boolean register(User user) {

		PreparedStatement statement = null;
		Connection conn = null;

		try {
			conn = DAOUtil.getConnection();
			statement = conn.prepareStatement(SQL_INSERT_NORMAL_USER);
			int paramIndex = 1;
			statement.setString(paramIndex++, user.getUsername());
			statement.setString(paramIndex++, user.getPassword());
			statement.setString(paramIndex++, user.getFullname());
			statement.setString(paramIndex++, user.getAddress());
			statement.setString(paramIndex++, user.getMobile());

			int affectedRow = statement.executeUpdate();

			if (affectedRow > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();

				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Register user who logged in via social network account
	 * 
	 * @param user
	 * @return
	 */
	private boolean registerSN(User user) {

		PreparedStatement statement = null;
		Connection conn = null;

		try {
			conn = DAOUtil.getConnection();
			statement = conn.prepareStatement(SQL_INSERT_SN_USER);
			int paramIndex = 1;
			statement.setString(paramIndex++, user.getUsername());
			statement.setString(paramIndex++, user.getFullname());
			statement.setString(paramIndex++, user.getSocialId());
			statement.setString(paramIndex++, user.getSocialLink());

			int affectedRow = statement.executeUpdate();

			if (affectedRow > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();

				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	private User checkLogin(String username, String password) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = DAOUtil.getConnection();
			statement = conn.prepareStatement(SQL_SELECT_NORMAL_USER);
			statement.setString(1, username);
			statement.setString(2, password);

			rs = statement.executeQuery();

			if (rs != null && rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setAddress(rs.getString("address"));
				user.setMobile(rs.getString("mobile"));

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private User checkLoginSN(String facebookId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = DAOUtil.getConnection();
			statement = conn.prepareStatement(SQL_SELECT_SN_USER);
			statement.setString(1, facebookId);

			rs = statement.executeQuery();

			if (rs != null && rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setFullname(rs.getString("fullname"));
				user.setSocialId(rs.getString("facebookId"));
				user.setSocialLink(rs.getString("facebookLink"));

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
