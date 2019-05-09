package com.news.sina.util;

public class XSSFilter {
    public static String filterBrackets(String s) {
        s = s.replace("<","&lt;");
        s = s.replace(">", "&gt;");
        return s;
    }
}
