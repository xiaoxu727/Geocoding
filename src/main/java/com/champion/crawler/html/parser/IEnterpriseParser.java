package com.champion.crawler.html.parser;

import org.jsoup.select.Elements;

public interface IEnterpriseParser {

	/**
	 * ������������ַ
	 * @param url
	 */
	public abstract void setUrl(String url);

	/**
	 * ������ҵ��ע���
	 * @param parser
	 * @return
	 */
	public abstract String registrationNumberParser();

	/**
	 * ��ȡtable Element
	 * @return
	 */
	public abstract Elements getTableElements();

	/**
	 * ����tabeleԪ��
	 * @param tableEles
	 */
	public abstract void processTableEles(Elements tableEles);

}