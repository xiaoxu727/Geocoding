package com.champion.crawler.html.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Console;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.champion.crawler.convert.JsonHelper;
import com.champion.crawler.entity.gis.GISImpl;
import com.champion.crawler.entity.gis.Point;
import com.google.gson.*;


public class GISParse extends Parser {

	public static final String KEY_1 = "T956ldjC0PGwStrGG6fhX4YdsnXL5jHa" ;

	public static final String BAIDU_TYPE = "baidu" ;
	public static final String GOOGLE_TYPE = "google" ;
	public static final String GOOGLE_KEY = "AIzaSyDxHApgO93PTTH8J4eCtyrTTIPGM9AMqCc";
	// "7d9fbeb43e975cd1e9477a7e5d5e192a";
	
	/**
	 * WGS84
	 */
	public Point WGS2BaiDu( Point point)
	{
			String url = String.format("http://api.zdoz.net/transmore.ashx?lats=%s&lngs=%s&type=5", point.lat,point.lng);
			Point pointNew = new Point();	
			this.setDocByUrl(url);
			String jsonstr= this.doc.text();
			jsonstr = "{\"items\":"+jsonstr+"}";
			
			try{
		    	JSONObject jsonObject = new JSONObject(jsonstr);                                   
		    	JSONArray changeObjs =  jsonObject.getJSONArray("items");                          
		    	for(int i = 0 ;i<changeObjs.length();i++){                                             
			   		JSONObject object = changeObjs.getJSONObject(i);                                   
			   		jsonstr = object.toString();                                                       
			   		   		
			   		JsonHelper.toJavaBean(pointNew, jsonstr);  
			   		pointNew.setId(point.id);			   		
	 		        System.out.println(pointNew.id);
		   		}                                                                                      
			}catch ( Exception e) {
				// TODO: handle exception
			}
		
		
		return pointNew;
	}
	
	/**
	 * 
	 */
	public List<Point> WGS2BaiDu( List<Point> points ){
		
		List<Point> resultPoints = new ArrayList<Point>();
		while (points!=null &&points.size()>0) {
	
		for(Point point:points){
			Point pointNew = WGS2BaiDu(point);
			resultPoints.add(pointNew);
		}
		GISImpl.addPoints(resultPoints);
		}
		return resultPoints; 
	
	}
	
	
	/**
	 *
	 * key lng(����),lat(γ��)
	 */
	public static Map<String,String> BaiduGeocoding(String address){
		BufferedReader in = null;
		try {
			//����ַת����utf-8��16����
			address = URLEncoder.encode(address, "UTF-8");
			URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?address="+ address +"&output=json&ak="+KEY_1+"&callback=showLocation");

			in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while((res = in.readLine())!=null){
				sb.append(res.trim());
			}
			String str = sb.toString();

			str = str.replace("showLocation&&showLocation(","").replace(")","");
			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(str);
			int status = element.getAsJsonObject().get("status").getAsInt();
			Map<String,String> map = null;
			if(status == 0 ){
				map = new HashMap<String,String>();
				JsonElement resultElement = element.getAsJsonObject().get("result");
				JsonElement locationElement = resultElement.getAsJsonObject().get("location");
				String lng = locationElement.getAsJsonObject().get("lng").getAsString();
				String lat = locationElement.getAsJsonObject().get("lat").getAsString();
				String precise = resultElement.getAsJsonObject().get("precise").getAsString();
				String confidence = resultElement.getAsJsonObject().get("confidence").getAsString();
				String level = resultElement.getAsJsonObject().get("level").getAsString();
				map.put("lng", lng);
				map.put("lat", lat);
				map.put("precise", precise);
				map.put("confidence", confidence);
				map.put("level", level);
				map.put("latLngType",BAIDU_TYPE);
				return map;
			}

		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 *
	 * key lng(����),lat(γ��)
	 */
	public static Map<String,String> getGoogleGeocoderLatLon(String address){
		BufferedReader in = null;
		try {

			address = URLEncoder.encode(address, "UTF-8");
//			URL tirc = new URL("http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=false");
			URL tirc = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key="+GOOGLE_KEY);

			in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while((res = in.readLine())!=null){
				sb.append(res.trim());
			}
			String str = sb.toString();
			Map<String,String> map = null;
			String patternStr = ".+location\" : (.+)},\"location_type.+";
			System.out.println(str);
			Matcher m = Pattern.compile(patternStr).matcher(str);
			String content = null;
			if(m.find()){
				content = (m.group(1));
			}
			if(content != null){
	            int lngStart = content.indexOf("lat\" : ");
				int lngEnd = content.indexOf(",\"lng");
				if(lngStart > 0 && lngEnd > 0 ){
					String lng = content.substring(lngStart+7, lngEnd);
					String lat = content.substring(lngEnd+9);
					map = new HashMap<String,String>();
					map.put("lng", lng);
					map.put("lat", lat);
					return map;
				}
			}
            return  map;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}



	/**
	 *
	 */
	public static void geocoding(String latLngType){
		List<Point> points  = GISImpl.getPoints();
		while(points.size()>0)
		{
			for(Point point :points){
				Point pointNew = new Point(point.id);
				if(point.address==null || point.address==""){
					pointNew.setLat("no data");
					pointNew.setLng("no data");
				}else {
					Map<String,String>  json = null;
					if(latLngType.equals(BAIDU_TYPE)){
						try {
							json = BaiduGeocoding(point.address);
						}catch (Exception e){

						}
					}else if(latLngType.equals(GOOGLE_TYPE)){
						json= getGoogleGeocoderLatLon(point.address);
					}
					if(json==null){
						pointNew.setLat("no data");
						pointNew.setLng("no data");
					}else {
						pointNew.setLat(json.get("lat"));
						pointNew.setLng(json.get("lng"));
						pointNew.setConfidence(json.get("confidence"));
						pointNew.setLevel(json.get("level"));
						pointNew.setPrecise(json.get("precise"));
					}
					pointNew.setLatLngType(latLngType);
				}
				GISImpl.updatePoint(pointNew);
				System.out.println(point.id);
			}
			points  = GISImpl.getPoints();
		}
	}
	
	
	public static void geocoding(int start, String latLngType){
		List<Point> points  = GISImpl.getPoints(start);
		while(points.size()>0)
		{
			for(Point point :points){
				Point pointNew = new Point(point.id);
				if(point.address==null || point.address==""){
					pointNew.setLat("no data");
					pointNew.setLng("no data");
				}else {
					Map<String,String>  json = null;
					if(latLngType.equals(BAIDU_TYPE)){
						json= BaiduGeocoding(point.address);
					}else if(latLngType.equals(GOOGLE_TYPE)){
						json= getGoogleGeocoderLatLon(point.address);
					}
					if(json==null){
						pointNew.setLat("no data");
						pointNew.setLng("no data");
					}else {
						pointNew.setLat(json.get("lat"));
						pointNew.setLng(json.get("lng"));
						pointNew.setConfidence(json.get("confidence"));
						pointNew.setLevel(json.get("level"));
						pointNew.setPrecise(json.get("precise"));
					}
					pointNew.setLatLngType(latLngType);

				}
				GISImpl.updatePoint(pointNew);
				Console cons = System.console();
				if(cons!=null){
					PrintWriter writer = cons.writer();
					writer.write(point.id);
				}
				System.out.println(point.id);
			}
			points  = GISImpl.getPoints();
		}
	}

}
