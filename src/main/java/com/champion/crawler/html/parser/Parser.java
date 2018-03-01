package com.champion.crawler.html.parser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.mysql.jdbc.Connection;
//import org.jsoup.parser.Parser;

public class Parser implements IParser {
	
	String htmlContent;
	 public Document doc;
	 String url;
	 String filePath;
	 
	 public void setFilePath(String filePath){
		 this.filePath = filePath;
	 }
	 
	public void setUrl(String url) {
		this.url = url;
//		setDocByUrl();
	}

	public Parser(){}
	 
	public Parser(String htmlContent){
		this.htmlContent= htmlContent;
		if(this.htmlContent !=null){
			this.doc = Jsoup.parse(htmlContent);
		}
	}	
	
	public Element getFirstElementByClassName(String clsName){
		if(this.doc==null){
			return null;
		}
//		this.doc = Jsoup.parse(this.htmlContent);
		Element ele = doc.getElementsByClass(clsName).first();
		return ele;
	}
	
	public Elements GetElementsByClassName(String clsName){
		if(this.doc==null){
			return null;
		}
//		this.doc = Jsoup.parse(this.htmlContent);
		Elements eles = doc.getElementsByClass(clsName);
		return eles;
	}
	
	public String  getContentByXPath(String XPath ){
		Element  ele = this.doc.select(XPath).first();
		if(ele == null){
		 return "";
		}
		String text = ele.text();
		return text;
	}
	
	public String getContengByXPathAndAttr(String XPath, String attr){
		Element ele = this.doc.select(XPath).first();
		if(ele ==null){
			return "";
		}
		String text = ele.attr(attr);
		return text;		
	}
	/**
	 * 根据xpath获取元素列表
	 * @param xpath
	 * @return
	 */
	public Elements getElements(String xpath){
		Elements eles = this.doc.select(xpath);
		
		return eles;
	}
	/**
	 * 获取元素的text
	 * @param ele
	 * @param xpath
	 * @return
	 */
	public String getElementContentByXpath(Element ele ,String xpath){
		Element firstEle = ele.select(xpath).first();
		if(firstEle ==null){
			return "";
		}
		String text = firstEle.text();
		return text;
	}
	/**
	 * 获取元素的属性值
	 * @param ele
	 * @param xpath
	 * @param attr
	 * @return
	 */
	public String getContentByElementAttr(Element ele,String xpath,String attr){
		Element firstEle = ele.select(xpath).first();
		if(firstEle ==null){
			return "";
		}
		String text = firstEle.attr(attr);
		return text;
	}
	
	/* (non-Javadoc)
	 * @see com.champion.crawler.html.IParser#setDocByfilePath(java.lang.String)
	 */
	@Override
	public void setDocByfilePath(String filePath){
		File file = new File(filePath);
		try {
			this.doc = Jsoup.parse(file,"gbk");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.champion.crawler.html.IParser#setDocByUrl(java.lang.String)
	 */
	@Override
	public void setDocByUrl(String url){
		
		
		try {
			this.doc = Jsoup.connect(url)
					.userAgent("Mozilla")
					.timeout(30000)
					.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	@Override
	public void setDocByByUrlPost(String url,Map<String, String> parmas) {
		// TODO Auto-generated method stub
		
		Response  res  = null;
		try {
			  res = Jsoup.connect(url)
					.data(parmas)
					.method(Method.POST)
					.execute();		
			  
			  this.doc = res.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.champion.crawler.html.IParser#setDocByHtml(java.lang.String)
	 */
	@Override
	public void setDocByHtml(String htmlContent){
		this.htmlContent= htmlContent;
		if(this.htmlContent !=null){
			this.doc = Jsoup.parse(htmlContent);
		}
	}
	
	
	public boolean isExsitsNode(String xpath){
		Element ele = this.doc.select(xpath).first();
		if (ele != null){
		  return true;
		}
		return false;
	}
	
	public String GetClassName(Element ele){
		return  ele.select("table>tbody>tr").first().text();
		
	}
	
	
	public static void main(String[] args) throws IOException{
		
		Parser parser = new Parser();
		String url = "http://gsxt.sh.gov.cn/notice/notice/view?uuid=nfc_corvFBzVsKghaCdDcHECUub97E8c&tab=01";
		

		parser.setDocByUrl(url);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String text = parser.doc.text();
		System.out.println(text);
		
//		System.out.println(Jsoup.connect("http://shixin.court.gov.cn/detail?id=1865129").ignoreContentType(true).execute().body());
	}

	@Override
	public void setDocByByUrlPost(String url) {
		// TODO Auto-generated method stub
		
	}
	
}
