package com.ihfazh.parser;

import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Dorar {
    ArrayList<Hadith> hadithList;

    public Dorar(String skey) throws MalformedURLException, ParseException, URISyntaxException {
        hadithList = new Scraper(skey).getResults();
    }

    public ArrayList<String> getRawResults(){
        ArrayList<String> results = new ArrayList<>();

        for (Hadith hadith: hadithList) {
            results.add(hadith.getText() + "\n" + hadith.getInfo());
        }
        return results;
    }

    public ArrayList<String> getTextResults() {
        ArrayList<String> results = new ArrayList<>();
        for (Hadith hadith: hadithList) {
            Document textDoc = Jsoup.parse(hadith.getText());
            Document infoDoc = Jsoup.parse(hadith.getInfo());
            results.add(textDoc.text() + "\n" + infoDoc.text());
        }

        return results;
    }

    public ArrayList<ParsedDorar> getParsedResults(){
        ArrayList<ParsedDorar> parsedDorars = new ArrayList<>();

        for (Hadith hadith: hadithList){
            ParsedDorar parsedDorar = new ParsedDorar();
            Document textDoc = Jsoup.parse(hadith.getText());

            for (Node node:
                 textDoc.body().childNodes()) {

               DorarHadith dorarHadith = new DorarHadith();
               if (node instanceof TextNode){
                   dorarHadith.setText(((TextNode) node).text());
                   dorarHadith.setHighlighted(false);
               } else {
                   TextNode child = (TextNode) node.childNode(0);
                   dorarHadith.setText(child.text());
                   dorarHadith.setHighlighted(true);
               }
               parsedDorar.appendHadith(dorarHadith);

            }
            Document infoDoc = Jsoup.parse(hadith.getInfo());
            // process info
            for (Node node :
                    infoDoc.body().select(".info-subtitle")) {

                DorarInfo dorarInfo = new DorarInfo();
                dorarInfo.setKey(((TextNode) node.childNode(0)).text());

                Node nextSibling = node.nextSibling();

                if (nextSibling == null){
                   Element value = ((Element) node.parent()).selectFirst("span:not(.info-subtitle)");
                   dorarInfo.setValue(value.text());
                }

                if (nextSibling instanceof TextNode){
                    String value = ((TextNode) nextSibling).text();
                    if (value.length() > 1){
                        dorarInfo.setValue(((TextNode) nextSibling).text());
                    } else {
                        Element parent = (Element) node.parent();
                        Element hukumHadith = parent.selectFirst("span:not(.info-subtitle)");
                        dorarInfo.setValue(hukumHadith.text());
                    }
                }
                parsedDorar.appendInfo(dorarInfo);
            }
            parsedDorars.add(parsedDorar);
        }
        return parsedDorars;
    }
}
