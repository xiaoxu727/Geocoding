package com.champion.crawler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.champion.crawler.config.Config;

public class JDBCUtil {
	//鍔犲瘑搴�
//		public static String url = "jdbc:mysql://localhost:3306/champion?"
//	            + "user=champion&password=champion&useUnicode=true&characterEncoding=UTF8";
		
//	public  String url = "jdbc:mysql://192.168.1.40:3306/credit-web-crawler?user=apps&password=apps&useUnicode=true&characterEncoding=UTF8";
	    
		private  String url; 			
//		public static String driver = "com.mysql.jdbc.Driver";
	    private String driver;
		private  Connection conn=null ;
		
		public JDBCUtil(){
			this.url = Config.getContentByPath("DatabaseInfo>url");
			this.driver = Config.getContentByPath("DatabaseInfo>driver");
			this.conn = getConnection();
		}
		
//		public JDBCUtil (String  urlPath  ) {
//			this.url =Config.getContentByPath(urlPath);
//			this.driver = Config.getContentByPath("DatabaseInfo>driver");
//			this.conn = getConnection();			
//		}
		
		public JDBCUtil (String  connectURl,String driverName  ) {
		this.url = connectURl;
//		this.driver = Config.getContentByPath("DatabaseInfo>driver");
		this.driver = driverName;
		this.conn = getConnection();			
	}
		
		
		
		/**
		 * 鑾峰彇杩炴帴鍣�
		 * @return
		 */
		public  Connection  getConnection(){
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");// 鍔ㄦ�鍔犺浇mysql椹卞姩
				// 涓�釜Connection浠ｈ〃涓�釜鏁版嵁搴撹繛鎺�
				conn = DriverManager.getConnection(url);
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("获取数据库连接失败！");
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("获取数据库连接失败！");
			}
			
			return conn;
		}
		
	
		
		public void closeConn(){
			try{
			if(conn!=null&&!conn.isClosed()){
				conn.close();
			}
			}catch(SQLException e){
				e.printStackTrace();
				System.out.print("关闭数据库失败！");
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.print("关闭数据库失败！");
			}
		}
		
		private void readyConn(){
			try{
				if(conn== null||conn.isClosed())
				{
					 conn = getConnection();		
				}
			}catch(SQLException e){
				e.printStackTrace();
				System.out.print("连接数据库失败！");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.print("连接数据库失败！");
			}
		}
		
		
		/**
		 * 鎵цsql
		 * @param sql
		 */
		public  void excute(int num,Connection conn,PreparedStatement psts,int totalNum){
			
			try {
				if(100 >= totalNum && num == totalNum){
					psts.executeBatch();
					conn.commit();
					psts.clearBatch();
				}else if(100 < totalNum && 0 == num%100){
					psts.executeBatch();
					conn.commit();
					psts.clearBatch();
				}else if(100 < totalNum && num == totalNum){
					psts.executeBatch();
					conn.commit();
					psts.clearBatch();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 查询操作
		 * @param sql
		 * @return
		 */
		public  ResultSet selectSQL(String sql){
			Statement stmt = null;
			ResultSet rs = null;
			try{
				readyConn();
				stmt = conn.createStatement();
				rs=stmt.executeQuery(sql);
			}catch(SQLException e){
				
				e.printStackTrace();
				System.out.println("error:"+sql+"");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("error:"+sql+"");
			}

			return rs;
		}
		/**
		 * 增删改操作
		 * @param sql
		 * @return
		 */
		public boolean updateSQL(String sql){
			readyConn();
			boolean result = false;
			 PreparedStatement statement = null; 
		       try {  
		            statement = conn.prepareStatement(sql);  
		            statement.executeUpdate();
		            statement.close();		           
		            result=true;
		            closeConn();
		        } catch (SQLException e) {  
		            System.out.println("error:"+sql+"");  
		            e.printStackTrace();  
		        } catch (Exception e) {  
		        	System.out.println("error:"+sql+"");  
		            e.printStackTrace();  
		        }  
		        return result;  
		}
		
		public String getCountByRowCol(String sql,int rowIndex,int colIndex){
			ResultSet rs = selectSQL(sql);
		    int count = 1;
		    String result = null;
		    try{
		    while(rs.next()&&count<=rowIndex){
		    	if(count==rowIndex){
		    	result = rs.getString(colIndex);
		    	rs.close();
		    	}
		    	count++;
		    }
		    }catch(Exception e ){
		    	
		    }
		    closeConn();
		    return result;
		}
		
		public ResultSet getContentByRowIndex(String sql,int rowIndex){
			ResultSet rs = selectSQL(sql);
		    int count = 1;
//		    Array contentArray = null ;
		    try{
		    while(rs.next()&&count<=rowIndex){

		    	count++;
		    }
		    }catch(Exception e ){
		    	
		    }
//		    closeConn();
		    return rs;
		    
		}
		
		public  void excuteQuery(String sql){
			Connection conn = getConnection();
			
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);// executeQuery浼氳繑鍥炵粨鏋滅殑闆嗗悎锛屽惁鍒欒繑鍥炵┖鍊�
//				while (rs.next()) {
////	                System.out .println(rs.getString(1) + "\t" + rs.getString(2)+ "\t" + rs.getString(3));// 鍏ュ鏋滆繑鍥炵殑鏄痠nt绫诲瀷鍙互鐢╣etInt()
//					  System.out .println(rs.getString(1));
//	            }
			} catch(SQLException e){
				
				e.printStackTrace();
				System.out.println("error:"+sql+"");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("error:"+sql+"");
			}finally{
				try {
					if(null != rs){
						rs.close();
					}
					if(null != stmt){
						stmt.close();
					}
					if(null != conn){
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void main(String args[]) throws SQLException{
			
			JDBCUtil jdbc = new JDBCUtil();
//			String sql = "insert into searched_keyword (keyword)  VALUES('test2')";
		   String sql ="select * from searched_keyword;";
			ResultSet rs = jdbc.selectSQL(sql);
			while(rs.next()){
//				System.out .println(rs.getString(1) + "\t" + rs.getString(2)+ "\t" + rs.getString(3));// 鍏ュ鏋滆繑鍥炵殑鏄痠nt绫诲瀷鍙互鐢╣etInt()
//			    System.out .println(rs.getString(1));
			}
			
			System.out.print(jdbc.getCountByRowCol(sql, 1, 1));
			jdbc.closeConn();
			
			sql = "insert into searched_keyword (keyword)  VALUES('test3')";
//			jdbc.updateSQL(sql);				

		}
}
