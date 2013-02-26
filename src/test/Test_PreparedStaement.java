package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.Conn;

public class Test_PreparedStaement {
	public static void main(String[] args) {
		PreparedStatement selectSql = new Conn().getPStmt("select * from t where t like ?");
		try {
			//selectSql.setString(1, "%'"+694+"%'");
			selectSql.setString(1, "%6944%");
			ResultSet rs = selectSql.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
