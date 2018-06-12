package wl.test.craw;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.regex.Pattern;
 


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
 
/**
 * ������ʾ������ڶ��߳���ͳ�ƺͷ�������
 * @author user
 *
 */
public class LocalDataCollectorCrawler extends WebCrawler {
 
  // ����ƥ���׺
  private static final Pattern FILTERS = Pattern.compile(
      ".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
      "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
  private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);
  CrawlStat myCrawlStat; // ��������״̬�����û�ͳ�ƺͷ���
 
  /**
   * ���췽��
   */
  public LocalDataCollectorCrawler() {
    myCrawlStat = new CrawlStat(); // ʵ��������״̬����
  }
  
  /**
   * ���������Ҫ�Ǿ�����Щurl������Ҫץȡ������true��ʾ��������Ҫ�ģ�����false��ʾ����������Ҫ��Url
   * ��һ������referringPage��װ�˵�ǰ��ȡ��ҳ����Ϣ
   * �ڶ�������url��װ�˵�ǰ��ȡ��ҳ��url��Ϣ
   */
  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase(); // ��ȡurlСд
    return !FILTERS.matcher(href).matches() && href.startsWith("http://www.zuidaima.com"); // ������java1234����
  }
 
  /**
   * ����������������Ҫ��ҳ�棬��������ᱻ���ã����ǿ��Ծ���Ĵ������ҳ��
   * page������װ������ҳ����Ϣ
   */
  @Override
  public void visit(Page page) {
    System.out.println("������ȡҳ�棺"+page.getWebURL().getURL());
    myCrawlStat.incProcessedPages(); // ����ҳ���1
 
    if (page.getParseData() instanceof HtmlParseData) { // ������html����
      HtmlParseData parseData = (HtmlParseData) page.getParseData(); // ��ȡHtml����
      Set<WebURL> links = parseData.getOutgoingUrls(); // ��ȡ�������
      myCrawlStat.incTotalLinks(links.size()); // �����Ӽ�link.size��
      try {
        myCrawlStat.incTotalTextSize(parseData.getText().getBytes("UTF-8").length); // �ı���������
      } catch (UnsupportedEncodingException ignored) {
        // Do nothing
      }
    }
    // ÿ��ȡ3��ҳ������ ���Ǵ���������
    if ((myCrawlStat.getTotalProcessedPages() % 3) == 0) {
      dumpMyData();
    }
  }
 
  /**
   * ��ȡ������״̬
   */
  @Override
  public Object getMyLocalData() {
    return myCrawlStat;
  }
 
  /**
   * ���������ʱ����
   */
  @Override
  public void onBeforeExit() {
    dumpMyData(); // ������
  }
 
  /**
   * ��������
   */
  public void dumpMyData() {
    int id = getMyId();
    
    logger.debug("��ǰ����ʵ��id:"+id);
    System.out.println("��ǰ����ʵ��id:"+id);
    System.out.println("�ܴ���ҳ�棺"+myCrawlStat.getTotalProcessedPages());
    System.out.println("�����ӳ��ȣ�"+myCrawlStat.getTotalLinks());
    System.out.println("���ı����ȣ�"+myCrawlStat.getTotalTextSize());
  }
}