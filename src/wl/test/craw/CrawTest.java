package wl.test.craw;

import java.util.HashSet;
import java.util.List;

import org.apache.http.message.BasicHeader;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawTest {
	private String rootFolder = "E:/crawl"; // �����������ݴ洢λ��
    private int numberOfCrawlers = 7;
	public void test() {
		try {
			begincrawler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test2() {
		try {
			basicCrawler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test3() {
		try {
			imageCrawler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void test4() {
		try {
			localDataCollector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void test5() {
		try {
			multiCrawler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void begincrawler() throws Exception {
        CrawlConfig config = new CrawlConfig();  
        config.setCrawlStorageFolder(rootFolder);  
        HashSet<BasicHeader> collections = new HashSet<BasicHeader>();
        collections.add(new BasicHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3192.0 Safari/537.36"));
        collections.add(new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"));
        collections.add(new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"));
        collections.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6"));
        collections.add(new BasicHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"));
        collections.add(new BasicHeader("Connection", "keep-alive"));
        collections.add(new BasicHeader("Cookie", "bid=fp-BlwmyeTY; __yadk_uid=dLpMqMsIGD1N38NzhbcG3E6QA33NQ9bE; ps=y; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1506515077%2C%22https%3A%2F%2Faccounts.douban.com%2Flogin%3Falias%3D793890838%2540qq.com%26redir%3Dhttps%253A%252F%252Fwww.douban.com%26source%3DNone%26error%3D1013%22%5D; ll=\"108296\"; ue=\"793890838@qq.com\"; __utmt=1; _ga=GA1.2.388925103.1505404043; _gid=GA1.2.1409223546.1506515083; dbcl2=\"161927939:ZDwWtUnYaH4\"; ck=rMaO; ap=1; push_noty_num=0; push_doumail_num=0; __utma=30149280.388925103.1505404043.1506510959.1506515077.8; __utmb=30149280.22.9.1506516374528; __utmc=30149280; __utmz=30149280.1506510959.7.5.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/login; __utmv=30149280.16192; _pk_id.100001.8cb4=1df4f52fdf296b72.1505404042.8.1506516380.1506512502.; _pk_ses.100001.8cb4=*"));
        config.setDefaultHeaders(collections);


        /* 
         * Instantiate the controller for this crawl. 
         */  
        PageFetcher pageFetcher = new PageFetcher(config);  
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();  
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);  
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);  
   
        /* 
         * For each crawl, you need to add some seed urls. These are the first 
         * URLs that are fetched and then the crawler starts following links 
         * which are found in these pages 
         */  
        
        controller.addSeed("http://www.java1234.com/");
        controller.addSeed("http://www.java1234.com/a/kaiyuan/");
        controller.addSeed("http://www.java1234.com/a/bysj/"); 
   
        /* 
         * Start the crawl. This is a blocking operation, meaning that your code 
         * will reach the line after this only when crawling is finished. 
         */  
        controller.start(MyCrawler.class, numberOfCrawlers);  
		
	}

	private void basicCrawler() throws Exception{
		CrawlConfig config = new CrawlConfig();  // ʵ�������������ļ�
 
        config.setCrawlStorageFolder(rootFolder); // ���������ļ��洢λ��
 
        /*
         * ���������Ƶ��
         * ÿ1000���룬Ҳ������������ļ��������1��
         */
        config.setPolitenessDelay(1000);
 
        /*
         * �����������ҳ����ȣ�����ר�Żὲ��  ����2 Ϊ����
         * Ĭ��ֵ-1 �������
         */
        config.setMaxDepthOfCrawling(2);
 
        /*
         * ������ȡ�������ҳ�� ��������1000  �����ȡ1000��
         * Ĭ��ֵ��-1����ʾ������
         */
        config.setMaxPagesToFetch(1000);
 
        /**
         * �Ƿ���ȡ�������ļ�������ͼƬ��PDF�ĵ�����Ƶ֮��Ķ��� ��������false ����ȡ
         * Ĭ��ֵtrue����ȡ
         */
        config.setIncludeBinaryContentInCrawling(false);
 
        /*
         * ����������ô���
         * config.setProxyHost("proxyserver.example.com");  // �����ַ
         * config.setProxyPort(8080); // ����˿�
         *
         * ���ʹ�ô���Ҳ�������������֤  �û���������
         * config.setProxyUsername(username); config.getProxyPassword(password);
         */
 
        /*
         * ������ü������ó�true����һ������ͻȻ��ֹ���߱��������ǿ��Իָ���
         * Ĭ��������false���Ƽ���Ĭ�����ã��������ó�true�����ܻ����ۿۣ�
         */
        config.setResumableCrawling(false);
 
        /*
         * ʵ�������������
         */
        PageFetcher pageFetcher = new PageFetcher(config); // ʵ����ҳ���ȡ��
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); // ʵ����������������� ����������� user-agent
         
        // ʵ������������˶�Ŀ������������ã�ÿ����վ����һ��robots.txt�ļ� �涨�˸���վ��Щҳ�����������Щҳ���ֹ���������Ƕ�robots.txt�淶��ʵ��
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
         
        // ʵ�������������
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
 
        /*
         * ������������ҳ�棬���ǹ涨�Ĵ����￪ʼ�����������ö������ҳ��
         */
        controller.addSeed("http://www.java1234.com/");
        controller.addSeed("http://www.java1234.com/a/kaiyuan/");
        controller.addSeed("http://www.java1234.com/a/bysj/");
 
        /*
         * �������棬����Ӵ˿̿�ʼִ���������񣬸�����������
         */
        controller.start(BasicCrawler.class, numberOfCrawlers);
      
	}
	
	private void imageCrawler() throws Exception{
        String storageFolder = "E:/crawl/data"; // ������ȡ��ͼƬ���ش洢λ��
 
        CrawlConfig config = new CrawlConfig(); // ʵ������������
 
        config.setCrawlStorageFolder(rootFolder); // ���������ļ��洢λ��
 
        /*
         * ����������ȡ�������ļ� 
         * ��ΪͼƬ���ڶ������ļ�
         */
        config.setIncludeBinaryContentInCrawling(true);
 
        String[] crawlDomains = {"http://www.java1234.com/"};
 
        /*
         * ʵ�������������
         */
        PageFetcher pageFetcher = new PageFetcher(config); // ʵ����ҳ���ȡ��
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); // ʵ����������������� ����������� user-agent
         
        // ʵ������������˶�Ŀ������������ã�ÿ����վ����һ��robots.txt�ļ� �涨�˸���վ��Щҳ�����������Щҳ���ֹ���������Ƕ�robots.txt�淶��ʵ��
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // ʵ�������������
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
         
        /*
         * ������������ҳ�棬���ǹ涨�Ĵ����￪ʼ�����������ö������ҳ��
         */
        for (String domain : crawlDomains) {
          controller.addSeed(domain);
        }
 
        ImageCrawler.configure(crawlDomains, storageFolder); // ���������������Լ����ش洢λ��
 
        /*
         * �������棬����Ӵ˿̿�ʼִ���������񣬸�����������
         */
        controller.start(ImageCrawler.class, numberOfCrawlers);	
	}
	
	private void  localDataCollector() throws Exception{
	    CrawlConfig config = new CrawlConfig(); // ������������
	    config.setCrawlStorageFolder(rootFolder); // ���������ļ��洢λ��
	    config.setMaxPagesToFetch(10);  // �������ҳ���ȡ��
	    config.setPolitenessDelay(1000); // ������ȡ���� 1����һ��
	 
	    // ʵ�������������
	    PageFetcher pageFetcher = new PageFetcher(config); // ʵ����ҳ���ȡ��
	    RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); // ʵ����������������� ����������� user-agent
	     
	    // ʵ������������˶�Ŀ������������ã�ÿ����վ����һ��robots.txt�ļ� �涨�˸���վ��Щҳ�����������Щҳ���ֹ���������Ƕ�robots.txt�淶��ʵ��
	    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	     
	    // ʵ�������������
	    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
	 
	    controller.addSeed("http://www.zuidaima.com"); // �����������
	     
	    // �������棬����Ӵ˿̿�ʼִ���������񣬸�����������
	    controller.start(LocalDataCollectorCrawler.class, numberOfCrawlers);
	 
	    List<Object> crawlersLocalData = controller.getCrawlersLocalData(); // ������߳������������ʱ����ȡ���汾������
	    long totalLinks = 0;
	    long totalTextSize = 0;
	    int totalProcessedPages = 0;
	    for (Object localData : crawlersLocalData) {
	      CrawlStat stat = (CrawlStat) localData;
	      totalLinks += stat.getTotalLinks();
	      totalTextSize += stat.getTotalTextSize();
	      totalProcessedPages += stat.getTotalProcessedPages();
	    }
	 
	    // ��ӡ����
	    
	    System.out.println("ͳ�����ݣ�");
	    System.out.println("�ܴ���ҳ�棺"+totalProcessedPages);
	    System.out.println("�����ӳ��ȣ�"+totalLinks);
	    System.out.println("���ı����ȣ�"+totalTextSize);	
	}
	
	@SuppressWarnings("deprecation")
	private void multiCrawler() throws Exception{
		CrawlConfig config1 = new CrawlConfig(); // ��������ʵ��1
	    CrawlConfig config2 = new CrawlConfig(); // ��������ʵ��2
	 
	    // ��������ʵ���ļ��ֱ�洢�����ļ���
	    config1.setCrawlStorageFolder(rootFolder + "/crawler1");
	    config2.setCrawlStorageFolder(rootFolder + "/crawler2");
	 
	    config1.setPolitenessDelay(1000); // ����1����ȡһ��
	    config2.setPolitenessDelay(2000); // ����2����ȡһ��
	 
	    config1.setMaxPagesToFetch(5); // ���������ȡҳ��5
	    config2.setMaxPagesToFetch(6); // ���������ȡҳ��6
	 
	    // ʹ������PageFetcherʵ��
	    PageFetcher pageFetcher1 = new PageFetcher(config1);
	    PageFetcher pageFetcher2 = new PageFetcher(config2);
	 
	    // ʹ��ͬһ��RobotstxtServerʵ��
	    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher1);
	 
	    CrawlController controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer);
	    CrawlController controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer);
	 
	    // �ֱ�ָ��Ŀ����������
	    String[] crawler1Domains = {"http://www.zuidaima.com/"}; 
	    String[] crawler2Domains = {"http://www.java1234.com/"}; 
	 
	    // �����Զ�������
	    controller1.setCustomData(crawler1Domains); 
	    controller2.setCustomData(crawler2Domains);
	 
	    // ������������ҳ�棬���ǹ涨�Ĵ����￪ʼ�����������ö������ҳ��
	    controller1.addSeed("http://www.zuidaima.com/");
	    controller1.addSeed("http://www.zuidaima.com/share/p1-s1.htm");
	 
	    controller2.addSeed("http://www.java1234.com/");
	    controller2.addSeed("http://www.java1234.com/a/bysj/javaweb/");
	 
	    // �������棬����Ӵ˿̿�ʼִ���������񣬸�����������  ����Դ��  ������������������
	    controller1.startNonBlocking(BasicCrawler.class, 5);
	    //controller2.startNonBlocking(BasicCrawler.class, 7);
	    // ��Ϣ1��
	    Thread.sleep(1 * 1000);
	     
	    System.out.println("��Ϣ1��");
	 
	    // ֹͣ��ȡ
	    controller1.shutdown();
	    System.out.println("ֹͣ��ȡ");
	    
	    controller1.waitUntilFinish(); // ֱ�����
	    System.out.println("����1�������");
	    
	    controller2.waitUntilFinish();  // ֱ�����
	    System.out.println("����2�������");
	}
}
