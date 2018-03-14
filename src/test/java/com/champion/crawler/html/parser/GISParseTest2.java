package com.champion.crawler.html.parser;

import junit.framework.TestCase;
import org.junit.Test;

public class GISParseTest2 {
    @Test
    public void test(){
        GISParse parse = new GISParse();
        parse.geocoding(GISParse.BAIDU_TYPE);
    }

}