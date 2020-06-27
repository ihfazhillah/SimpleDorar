package com.ihfazh.parser;

import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws MalformedURLException, ParseException, URISyntaxException {
        System.out.println("Hello world, this is a simple package to work with dorar.net site.");
        // High Level Design
        // User search the query from dorar.net
        // user can get data list of:
        // 1. raw (with html tags inside)
        // 2. text (only text without html tags)
        // 3. array of object with text, search_result boolean attribute
        Dorar dorar = new Dorar("اللهُمَّ بيِّضْ وَجهي يومَ تسوَدُّ");
        // this will return raw results
//        System.out.println(dorar.getRawResults().toString());
        // this will return text results
//        System.out.println(dorar.getTextResults().toString());
//        // this will return parsed results
        dorar.getParsedResults();
    }
}
