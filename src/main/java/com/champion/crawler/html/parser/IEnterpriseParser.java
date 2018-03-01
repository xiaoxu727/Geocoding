package com.champion.crawler.html.parser;

import org.jsoup.select.Elements;

public interface IEnterpriseParser {

	/**
	 * 设置搜索的网址
	 * @param url
	 */
	public abstract void setUrl(String url);

	/**
	 * 解析企业的注册号
	 * @param parser
	 * @return
	 */
	public abstract String registrationNumberParser();

	/**
	 * 获取table Element
	 * @return
	 */
	public abstract Elements getTableElements();

	/**
	 * 处理tabele元素
	 * @param tableEles
	 */
	public abstract void processTableEles(Elements tableEles);

}