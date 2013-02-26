package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn
{
  private Connection con;
  private Statement stmt;
  private String dbName; 
  private String ip; 
  private String port; 
  private String user; 
  private String password;

  public Conn() {
	  init();
  }
  void init(){
	  ReadInfo.load("sqldata.properties");
	  dbName=ReadInfo.getString("dbName");
	  ip=ReadInfo.getString("ip");
	  port=ReadInfo.getString("port");
	  user=ReadInfo.getString("user");
	  password=ReadInfo.getString("password");
	  
	  setConn();
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }
  static {
	  try {
	      Class.forName("com.mysql.jdbc.Driver");
	    }
	    catch (ClassNotFoundException e)
	    {
	      System.out.print("找不到类");
	      //System.exit(0);
	    }
  }
  public Connection setConn() {
    try { 
    	System.out.println(this.ip+""+  this.port+""+   this.dbName+""+  this.user+""+ this.password);
    	con = DriverManager.getConnection("jdbc:mysql://" + this.ip + ":" + this.port + "/" + this.dbName, this.user, this.password);        	
    	stmt = con.createStatement();
    } catch (SQLException e){
      System.out.println("连接不成功");
      e.printStackTrace();
      }
    return con;
  }
  public void close(){
	  try {
		System.out.println("conn close!");
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public Statement getNewStmt(){
	  Statement stmt=null;
    try {
    	stmt = con.createStatement();
	} catch (SQLException e) {
		con=setConn();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	return stmt;
  }
  public Statement getOldStmt(){
	  if(stmt==null){
		  try {
			stmt=con.createStatement();
		} catch (SQLException e) {
			try {
				setConn().createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	  }
	return stmt;
  }
  public PreparedStatement getPStmt(String sql){
	  PreparedStatement pstmt=null;
    try {
    	pstmt = con.prepareStatement(sql);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return pstmt;
  }
  public static void main(String[] args) {
  }
}