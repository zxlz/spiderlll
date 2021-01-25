package spiderpak.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import spiderpak.spider.Server;


public class sqlConn {
	public static final String DBDRIVER = "com.mysql.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/zxl_pachongAcfun";
	public static final String DBUSER = "root";
	public static final String DBPASS = "123";
	private Connection conn = null;

	private  sqlConn() {
		try {
			Class.forName(DBDRIVER);
			this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			Server.pushMessage("--------����sql�ɹ�");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}  
	private static final sqlConn single = new sqlConn();
    public static sqlConn getSqlConn() {  
	        return single;  
	    }  
	public  Connection getConn(){
		return conn;
	}
	 
	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
				Server.pushMessage("con�ر�");
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		// }
		// public ResultSet GetResult(String sql){ //��ѯ
		// try{
		// PreparedStatement pstmt = conn.prepareStatement(sql);
		// ResultSet rs = pstmt.executeQuery();
		// return rs;
		// }
		// catch(SQLException e){
		// System.out.println(e);
		// }
		// return null;
		// }
		// //updare or insert
		// public void ExeSql(String sql){
		// try{
		// PreparedStatement pstmt = conn.prepareStatement(sql);
		// pstmt.executeUpdate();
		// }
		// catch(SQLException e){
		// System.out.println(e);
		// }
		// }
		//
	}

}
