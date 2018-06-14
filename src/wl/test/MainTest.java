package wl.test;



import java.io.IOException;


import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



public class MainTest {

	public static void main(String[] args) {
		Document doc;
		try {
			String url="http://www.chinatax.gov.cn/n810341/n810780/c3498512/content.html";
			doc = Jsoup.connect("http://www.chinatax.gov.cn/n810341/n810780/c3498512/content.html").get();
			System.out.println(doc.html());
			Document doc2=Jsoup.parse(new URL(url), 5000);
			System.out.println(doc2.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
