package wl.test.craw;

import java.util.Set;
import java.util.regex.Pattern;
 
import org.apache.http.Header;
 
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
 
/**
 * �Զ�����������Ҫ�̳�WebCrawler�࣬������Щurl���Ա����Լ�������ȡ��ҳ����Ϣ
 * @author user
 *
 */
public class BasicCrawler extends WebCrawler {
 
     /**
     * ����ƥ��ָ���ĺ�׺�ļ�  ָ��ͼƬ��׺
     */
      private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
 
      /**
       * ���������Ҫ�Ǿ�����Щurl������Ҫץȡ������true��ʾ��������Ҫ�ģ�����false��ʾ����������Ҫ��Url
       * ��һ������referringPage��װ�˵�ǰ��ȡ��ҳ����Ϣ
       * �ڶ�������url��װ�˵�ǰ��ȡ��ҳ��url��Ϣ
       */
      @Override
      public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase(); // �õ�Сд��url
        // ���˵�����ͼƬ��׺��url
        if (IMAGE_EXTENSIONS.matcher(href).matches()) {
          return false;
        }
 
        // ֻ����www.java1234.com��ͷ��url
        return href.startsWith("http://www.java1234.com/");
      }
 
      /**
       * ����������������Ҫ��ҳ�棬��������ᱻ���ã����ǿ��Ծ���Ĵ������ҳ��
       * page������װ������ҳ����Ϣ
       */
      @Override
      public void visit(Page page) {
        int docid = page.getWebURL().getDocid(); // ��ȡdocid url��Ψһʶ�� ��������
        String url = page.getWebURL().getURL();  // ��ȡurl
        String domain = page.getWebURL().getDomain(); // ��ȡ����
        String path = page.getWebURL().getPath();  // ��ȡ·�� 
        String subDomain = page.getWebURL().getSubDomain(); // ��ȡ������
        String parentUrl = page.getWebURL().getParentUrl(); // ��ȡ�ϼ�Url
        String anchor = page.getWebURL().getAnchor(); // ��ȡê��
 
        System.out.println("docid:"+docid);
        System.out.println("url:"+url);
        System.out.println("domain:"+domain);
        System.out.println("path:"+path);
        System.out.println("subDomain:"+subDomain);
        System.out.println("parentUrl:"+parentUrl);
        System.out.println("anchor:"+anchor);
         
 
        if (page.getParseData() instanceof HtmlParseData) { // �ж��Ƿ���html����
          HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); // ǿ������ת������ȡhtml���ݶ���
          String text = htmlParseData.getText();  // ��ȡҳ�洿�ı�����html��ǩ��
          String html = htmlParseData.getHtml();  // ��ȡҳ��Html
          Set<WebURL> links = htmlParseData.getOutgoingUrls();  // ��ȡҳ���������
 
          System.out.println("���ı�����: " + text.length());
          System.out.println("html����: " + html.length());
          System.out.println("������Ӹ���: " + links.size());
        }
 
        Header[] responseHeaders = page.getFetchResponseHeaders(); // ��ȡ��Ӧͷ��Ϣ
        if (responseHeaders != null) {
          System.out.println("��Ӧ��ͷ��Ϣ");
          for (Header header : responseHeaders) {
           System.out.println(header.getName()+"+"+header.getValue());
          }
        }
 
 
 
      }
    }