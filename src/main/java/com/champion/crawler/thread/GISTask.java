package com.champion.crawler.thread;

import com.champion.crawler.html.parser.GISParse;


public class GISTask implements Runnable {

	private int start ;
	public GISTask(int start){
		this.start = start;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		GISParse parse = new GISParse();
		parse.geocoding(start, GISParse.BAIDU_TYPE);
		
	}

	public static void main(String[] args){
		GISParse parse = new GISParse();
		parse.geocoding(0, GISParse.BAIDU_TYPE);
	}
}
