package wl.test;



import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



public class MainTest {

	public static void main(String[] args) {
		Document doc;
		try {
			doc = Jsoup.connect("http://www.chinatax.gov.cn/n810341/n810755/c3497821/content.html").get();
			System.out.println(doc.select(".sv_textcon li").get(2).text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
