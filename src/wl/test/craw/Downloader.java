package wl.test.craw;

import org.apache.http.HttpStatus;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.parser.Parser;
import edu.uci.ics.crawler4j.url.WebURL;
 
/**
 * �������ʾ��crawler4j�����ȡһ����ҳ������
 * �Լ���ȡ��������ı���Ϣ
 */
public class Downloader {
 
  private final Parser parser;
  private final PageFetcher pageFetcher;
 
  public Downloader() throws IllegalAccessException, InstantiationException {
    CrawlConfig config = new CrawlConfig(); // ʵ������������
    parser = new Parser(config); // ʵ����������
    pageFetcher = new PageFetcher(config); // ʵ����ҳ���ȡ��
  }
 
  public static void main(String[] args) throws IllegalAccessException, InstantiationException {
    Downloader downloader = new Downloader();
    downloader.processUrl("http://www.java1234.com/a/yuanchuang/j2sev2/2016/0606/6221.html");
    downloader.processUrl("http://blog.java1234.com/blog/articles/103.html");
  }
 
  public void processUrl(String url) {
    System.out.println("����url:"+url);
    Page page = download(url);
    if (page != null) {
      ParseData parseData = page.getParseData(); // ��ȡ��������
      if (parseData != null) {
        if (parseData instanceof HtmlParseData) { // ������html��������
          HtmlParseData htmlParseData = (HtmlParseData) parseData; // ��ȡ����
           
          System.out.println("����: " + htmlParseData.getTitle());
          System.out.println("���ı�����: " + htmlParseData.getText().length());
          System.out.println("html����: " + htmlParseData.getHtml().length());
        }
      } else {
          System.out.println("���ܽ�����ҳ��");
      }
    } else {
        System.out.println("���ܻ�ȡҳ�������");
    }
  }
 
  /**
   * ����ָ��Url��ҳ������
   * @param url
   * @return
   */
  private Page download(String url) {
    WebURL curURL = new WebURL(); // ʵ����weburl
    curURL.setURL(url); // ����url
    PageFetchResult fetchResult = null;
    try {
      fetchResult = pageFetcher.fetchPage(curURL); // ��ȡ��ȡ���
      if (fetchResult.getStatusCode() == HttpStatus.SC_OK) { // �ж�http״̬�Ƿ���200 
        Page page = new Page(curURL); // ��װPage
        fetchResult.fetchContent(page,1000000); // ��������
        parser.parse(page, curURL.getURL()); // ����page
        return page; // ����page
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fetchResult != null) { // ����jvmû�л��� �ô�����ն��� ��ֹ�ڴ����
        fetchResult.discardContentIfNotConsumed(); // ���ٻ�ȡ����
      }
    }
    return null;
  }
}