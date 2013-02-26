package conn;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class DBoperate_ {
	private static Conn conn;
	public DBoperate_(){
		//System.out.println("DBoperate_");
	}
	static {   //同时多个new方法时，这样做可以避免连接多次数据库
		//System.out.println("static");
		conn=new Conn();
	}
	public static void close(){
		conn.close();
	}
	public static boolean delete(String sql){
		if(conn==null){
			conn=new Conn();
		}
		try {
			conn.getOldStmt().executeUpdate(sql);
			return true;
		} catch(MySQLIntegrityConstraintViolationException e1){
			return false;
		} catch (SQLException e) {
			//System.out.println(sql+"出错");
			e.printStackTrace();
		}
		return true;
	}
	public static boolean insert(String sql){
		if(conn==null){
			conn=new Conn();
		}
		try {
			conn.getOldStmt().executeUpdate(sql);
			return true;
			}catch(MySQLIntegrityConstraintViolationException e1){
			return false;
		} catch (SQLException e) {
			conn.setConn();
			try {
				conn.getOldStmt().executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return true;
	}
	public static void update(String sql){
		if(conn==null){
			conn=new Conn();
		}
		try {
			conn.getOldStmt().executeUpdate(sql);
		} catch(MySQLIntegrityConstraintViolationException e1){
			
		} catch (SQLException e) {
			conn.setConn();
			try {
				conn.getOldStmt().executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static ResultSet select(String sql){
		if(conn==null){
			conn=new Conn();
		}
		try {
			return conn.getNewStmt().executeQuery(sql);
		} catch (SQLException e) {
			conn.setConn();
			try {
				conn.getNewStmt().executeQuery(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}
	public static void main(String[] args) {
		new DBoperate_();
		new Thread(){
			public void run(){
				new DBoperate_();
			}
		}.start();
		new Thread(){
			public void run(){
				new DBoperate_();
			}
		}.start();
		new Thread(){
			public void run(){
				new DBoperate_();
			}
		}.start();
		new Thread(){
			public void run(){
				new DBoperate_();
			}
		}.start();
		System.out.println("结束");
		DBoperate_.close();
	}
}

