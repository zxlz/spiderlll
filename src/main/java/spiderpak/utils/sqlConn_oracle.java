package spiderpak.utils;


import spiderpak.spider.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class sqlConn_oracle {
	public static final String DBDRIVER = "oracle.jdbc.OracleDriver";
	public static final String DBURL = "jdbc:oracle:thin:@192.168.0.3:1521:orcl";
	public static final String DBUSER = "data1";
	public static final String DBPASS = "data1_";
	private Connection conn = null;

	private sqlConn_oracle() {

		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		int trycount=0;
		boolean ready = false ;
		while(!ready){
			try {
				this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
				ready=true;
			}catch (Exception e){
				//连接失败，阻塞重试
				Server.pushMessage("wait connection: "+ ++trycount+" "+e.getMessage());
				try {
					Thread.sleep(10000);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
			}
		}



			Server.pushMessage("oracle connection ready---------");


	}  
	private static final sqlConn_oracle single = new sqlConn_oracle();
    public static sqlConn_oracle getSqlConn() {
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
