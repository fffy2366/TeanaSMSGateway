package com.vastrek.nissan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author frank
 * 
 */
public class BaseDao {
	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	/**
	 * @return
	 */
	public Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//String url = "jdbc:mysql://localhost:3306/tianlaihuodong?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8";
		String url = "jdbc:mysql://172.25.241.43:3306/tianlaihuodong?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8";
		try {
			//con = DriverManager.getConnection(url, "root", "1234");
			con = DriverManager.getConnection(url, "tianlai", "tianla3IUa9g");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// try {
		// con = OpenCms.getSqlManager().getConnection("default");
		// } catch (Exception e) {
		//
		// }
		return con;
	}

	/**
	 * @param sql
	 * @param psts
	 * @return
	 */
	public ResultSet ExecuteQuery(String sql, String psts[]) {
		try {
			pst = con.prepareStatement(sql);
			if (psts != null) {
				for (int i = 0; i < psts.length; i++) {
					pst.setObject(i + 1, psts[i]);
				}
			}
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return rs;

	}

	/**
	 * @param sql
	 * @param os
	 * @return
	 * @author frank
	 * @date 2012-12-02
	 */
	public ResultSet ExecuteQuery(String sql, Object os[]) {
		try {
			pst = con.prepareStatement(sql);
			if (os != null) {
				for (int i = 0; i < os.length; i++) {
					pst.setObject(i + 1, os[i]);
				}
			}
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return rs;

	}

	/**
	 * @param sql
	 * @param psts
	 * @return
	 */
	public ResultSet ExecuteQuery_jac(String sql, List<String> psts) {
		try {
			pst = con.prepareStatement(sql);
			if (psts != null && psts.size() > 0) {
				for (int i = 0; i < psts.size(); i++) {
					pst.setObject(i + 1, psts.get(i).toString());
				}
			}
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return rs;

	}

	/**
	 * @param sql
	 * @param psts
	 * @return
	 */
	public int ExecuteUpdate(String sql, String psts[]) {
		int a = 0;
		try {
			pst = con.prepareStatement(sql);
			if (psts != null) {
				for (int i = 1; i <= psts.length; i++) {
					pst.setString(i, psts[i - 1]);
				}
			}
			a = pst.executeUpdate();
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return a;
		}
	}

	/**
	 * @param sql
	 * @param psts
	 * @return
	 */
	public int ExecuteUpdate(String sql, Object os[]) {
		int a = 0;
		try {
			pst = con.prepareStatement(sql);
			if (os != null) {
				for (int i = 1; i <= os.length; i++) {
					pst.setObject(i, os[i - 1]);
				}
			}
			a = pst.executeUpdate();
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return a;
		}
	}

	public void Close() {
		try {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
