package wl.test.craw;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;
 
import com.google.common.io.Files;
 
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;
 
/*
 * �������Ҫ����ȡͼƬ�����Ҵ洢��ָ���ļ���
 */
public class ImageCrawler extends WebCrawler {
 
  /*
   * ָ���ļ���׺����
   */
  private static final Pattern filters = Pattern
      .compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
 
  /*
   * ����ƥ��ͼƬ�ļ�
   */
  private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");
 
  private static File storageFolder; // ��ȡ��ͼƬ���ش洢��ַ
  private static String[] crawlDomains; // ָ��Ҫ��ȡ������
 
  /**
   * ���÷��� ָ�������ͱ��ش洢�ļ�
   * @param domain
   * @param storageFolderName
   */
  public static void configure(String[] domain, String storageFolderName) {
    crawlDomains = domain;
 
    storageFolder = new File(storageFolderName); // ʵ����
    if (!storageFolder.exists()) { // �����ļ�������
      storageFolder.mkdirs(); // ���Ǵ���һ��
    }
  }
 
  /**
   * ���������Ҫ�Ǿ�����Щurl������Ҫץȡ������true��ʾ��������Ҫ�ģ�����false��ʾ����������Ҫ��Url
   * ��һ������referringPage��װ�˵�ǰ��ȡ��ҳ����Ϣ
   * �ڶ�������url��װ�˵�ǰ��ȡ��ҳ��url��Ϣ
   */
  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase(); // �õ�Сд��url
    if (filters.matcher(href).matches()) { // ����ָ����׺url
      return false;
    }
 
    if (imgPatterns.matcher(href).matches()) { // ƥ��ָ��ͼƬ��׺�ļ�
      return true;
    }
 
    for (String domain : crawlDomains) { // ƥ��ָ������
      if (href.startsWith(domain)) {
        return true;
      }
    }
    return false;
  }
 
  /**
   * ����������������Ҫ��ҳ�棬��������ᱻ���ã����ǿ��Ծ���Ĵ������ҳ��
   * page������װ������ҳ����Ϣ
   */
  @Override
  public void visit(Page page) {
    String url = page.getWebURL().getURL(); // ��ȡurl
 
    // ֻ��ȡ���ڵ���10kB��ͼƬ�ļ�
    if (!imgPatterns.matcher(url).matches() ||
        !((page.getParseData() instanceof BinaryParseData) || (page.getContentData().length < (10 * 1024)))) {
      return;
    }
 
    // ��ȡͼƬ��׺
    String extension = url.substring(url.lastIndexOf('.'));
    String hashedName = UUID.randomUUID() + extension; // ͨ��uuid ƴ�ӳ�ΨһͼƬ����
 
    // ����洢�ļ�
    String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
    try {
      Files.write(page.getContentData(), new File(filename)); // ����ȡ�����ļ��洢��ָ���ļ�
      System.out.println("��ȡͼƬ��url:"+url);
    } catch (IOException iox) {
       iox.printStackTrace();
    }
  }
}
