package com.champion.crawler.html.parser;

import java.util.Map;


public interface IParser {
	

	/**
	 * ?????????????????Documnet
	 * @param filePath
	 */
	public abstract void setDocByfilePath(String filePath);

	/**
	 * ???url????Documnet
	 * @param url
	 */
	public abstract void setDocByUrl(String url);

	/**
	 * ????????????Documnet
	 * @param htmlContent
	 */
	public abstract void setDocByHtml(String htmlContent);
	
	
	/**
	 * ???url????document post????????
	 * @param url
	 */
	public abstract void setDocByByUrlPost(String url);

	void setDocByByUrlPost(String url, Map<String, String> parmas);

}