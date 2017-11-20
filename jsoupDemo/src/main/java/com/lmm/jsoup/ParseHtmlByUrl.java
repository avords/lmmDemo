package com.lmm.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Administrator on 2016/12/9.
 */
public class ParseHtmlByUrl {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("http://www.lmm.com").get();
        System.out.println(doc.body());
        Element element = doc.select("#currentCity").first();
        String text = element.text();
        System.out.println(text);
    }
}
