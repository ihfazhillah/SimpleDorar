package com.ihfazh.parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.management.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;


public class Scraper {
    private URL url;

    public  Scraper(String key) throws MalformedURLException, URISyntaxException {
        URI uri = new URI("https", null, "//dorar.net/dorar_api.json", "skey=" + key, null);
        url = uri.toURL();
    }

    private String getResponse(){
        StringBuilder content = new StringBuilder();

        try {
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null){
                content.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return content.toString();
    }

    private String getHTML(String resp) throws ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resp);

        JSONObject jsonObject = (JSONObject)obj;

        JSONObject ahadith = (JSONObject)jsonObject.get("ahadith");

        return (String)ahadith.get("result");
    }

    private ArrayList<Hadith> getResultArray(String html){
        Document doc = Jsoup.parse(html);

        Elements hadith = doc.select(".hadith");
        Elements hadithInfo = doc.select(".hadith-info");

        ArrayList<Hadith> results = new ArrayList<>();

        int i = 0;
        for (;i<hadith.size(); i++){
            Hadith h = new Hadith();
            h.setInfo(hadithInfo.get(i).html());
            h.setText(hadith.get(i).html());
            results.add(h);
        }
        return results;
    }

    public ArrayList<Hadith> getResults() throws ParseException {
        String resp = getResponse();
        String html = getHTML(resp);
        return getResultArray(html);
    }

    public static void main (String[] args) throws IOException, ParseException, URISyntaxException {
        Scraper scraper = new Scraper("تسعى");
        System.out.println(scraper.getResults().get(0).getInfo());
    }
}
