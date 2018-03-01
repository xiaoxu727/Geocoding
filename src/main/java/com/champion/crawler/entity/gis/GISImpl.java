package com.champion.crawler.entity.gis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class GISImpl {

	protected  static SqlMapClient sqlMapClient = null;

	static {
		try {
			Reader reader = Resources
			.getResourceAsReader("config/GISSqlMapConfig.xml");
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
			} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 *
	 * @return
	 */
	public static List<Point> getPoints() {
		// TODO Auto-generated method stub
		List <Point> points = null;
		try {
			points = sqlMapClient.queryForList("selectPoints",0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return points;	
	}
	
	public static List<Point> getPoints(int from) {
		// TODO Auto-generated method stub
		List <Point> points = null;
		try {
			points = sqlMapClient.queryForList("selectPoints",from);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return points;	
	}

	 /**
     * 
     * @param
     * @return
     */
    public static boolean  addPoints(List<Point> points){
    	// TODO Auto-generated method stub
		Object object = null;
		boolean flag= false;
		if(points==null||points.size()==0){
			return flag;			 
		}
		try {
			object = sqlMapClient.insert("addPoints",points);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(object!=null){
			flag =true;
		}		
		return flag;
    }
    
    public static boolean  updatePoint(Point  point){
    	// TODO Auto-generated method stub
		Object object = null;
		boolean flag= false;
		if(point==null){
			return flag;			 
		}
		try {
			object = sqlMapClient.insert("updatePoint",point);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(object!=null){
			flag =true;
		}		
		return flag;
    }

	
}
