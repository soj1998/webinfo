package com.wl.runzekeji.data.local.sqldata;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;



public class JdbcUtils {
	
	private static DataSource ds = null;
	static{
		try{
			InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("/config/dbcpconfig.properties");
			Properties prop = new Properties();
			prop.load(in);
			ds = BasicDataSourceFactory.createDataSource(prop);
		}catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
	
	
	public static void release(Connection conn,Statement st,ResultSet rs){
		
		if(rs!=null){
			try{
				rs.close();   //throw new 
			}catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(st!=null){
			try{
				st.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			st = null;
		}
		if(conn!=null){
			try{
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//String sql = "insert into account(id,name,money) values(?,?,?)"   object[]{1,"aaa","10000"};
	public static int update(String sql,Object params[]) throws SQLException{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int r=-1;
		try{
			conn = getConnection();
			st = conn.prepareStatement(sql);
			for(int i=0;i<params.length;i++){
				st.setObject(i+1,params[i]);
			}
			r=st.executeUpdate();
			return r;
		}finally{
			release(conn, st, rs);
		}
	}
	
	//
	public static Object query(String sql,Object params[],ResultSetHandler handler) throws SQLException{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			st = conn.prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					st.setObject(i+1,params[i]);
				}
			}
			rs = st.executeQuery();
			return handler.handler(rs);
		}finally{
			release(conn, st, rs);
		}
	}
}	







