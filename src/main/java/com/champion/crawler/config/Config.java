package com.champion.crawler.config;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.nodes.*;
public class Config {
//	private static String configFilePath="config\\config.xml";
	
	private static String configFilePath= System.getProperty("user.dir")+"/config/config.xml";
	private static Document doc ;
	
	public static String getContentByPath(String xmlpath){
		String content= null;
		if(doc==null){
		   Config config = new Config();
		}
		Elements eles = doc.select(xmlpath);
		content = eles.text();
		return content;
	}
	public Config(){
		try {
			this.doc = Jsoup.parse(new File(configFilePath), "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Config config = new Config();
		System.out.print(config.getContentByPath("fileInfo>dataFile>filePath"));
	}
	
}
