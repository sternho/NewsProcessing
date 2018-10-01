package com.nlp.util.parser.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HTMLParser {
	String webContent;
	
	public HTMLParser(String link) throws IOException {
		URL url = new URL(link);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		StringBuffer html = new StringBuffer();
		
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			html.append(inputLine);
		in.close();
		
		webContent = html.toString();
	}
	
	public Elements getDetailsByClass(String classname) {
		Document doc = Jsoup.parse(webContent.toString());
		return doc.select(classname);
	}

}
