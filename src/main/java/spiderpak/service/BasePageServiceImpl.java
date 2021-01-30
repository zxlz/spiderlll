
package spiderpak.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BasePageServiceImpl<E> extends BaseService<E>{


	public BasePageServiceImpl() {

	}
 
	
	
	public void init()  {


		try {
			preViUrl();
			preUnViUrl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 
		
	}

	public void destroy() throws SQLException {


		System.out.println("destroy");

	}

	@Override
	public int addData(E info) throws SQLException {

		return 0;
	}


	private void destoryAddUnVisitedUrlOnDB() {


	}

	//
	
	  private void preViUrl() throws SQLException  {


		}

		private void preUnViUrl() throws SQLException {
			int i=1;
//			while(i<173){
//			while(i<2){
//				task.getUrlManager().addUnvisitedUrl("https://adcdsa.com/html/amateur/list_5_"+i+++".html");
//			}
//			http://www.glitterandvodka.com/zuqiu/list_12_1.html

		};






}
