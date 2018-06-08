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
	private String rootFolder = "E:/crawl"; // 定义爬虫数据存储位置
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
		CrawlConfig config = new CrawlConfig();  // 实例化爬虫配置文件
 
        config.setCrawlStorageFolder(rootFolder); // 设置爬虫文件存储位置
 
        /*
         * 设置请求的频率
         * 每1000毫秒，也就是两次请求的间隔至少是1秒
         */
        config.setPolitenessDelay(1000);
 
        /*
         * 设置请求的网页的深度（后面专门会讲）  设置2 为两层
         * 默认值-1 无限深度
         */
        config.setMaxDepthOfCrawling(2);
 
        /*
         * 设置爬取的最大网页数 这里设置1000  最多爬取1000次
         * 默认值是-1，表示无限制
         */
        config.setMaxPagesToFetch(1000);
 
        /**
         * 是否爬取二进制文件，比如图片，PDF文档，视频之类的东西 这里设置false 不爬取
         * 默认值true，爬取
         */
        config.setIncludeBinaryContentInCrawling(false);
 
        /*
         * 这里可以设置代理
         * config.setProxyHost("proxyserver.example.com");  // 代理地址
         * config.setProxyPort(8080); // 代理端口
         *
         * 如果使用代理，也可以设置身份认证  用户名和密码
         * config.setProxyUsername(username); config.getProxyPassword(password);
         */
 
        /*
         * 这个配置假如设置成true，当一个爬虫突然终止或者奔溃，我们可以恢复；
         * 默认配置是false；推荐用默认配置，假如设置成true，性能会大打折扣；
         */
        config.setResumableCrawling(false);
 
        /*
         * 实例化爬虫控制器
         */
        PageFetcher pageFetcher = new PageFetcher(config); // 实例化页面获取器
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); // 实例化爬虫机器人配置 比如可以设置 user-agent
         
        // 实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件 规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
         
        // 实例化爬虫控制器
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
 
        /*
         * 配置爬虫种子页面，就是规定的从哪里开始爬，可以配置多个种子页面
         */
        controller.addSeed("http://www.java1234.com/");
        controller.addSeed("http://www.java1234.com/a/kaiyuan/");
        controller.addSeed("http://www.java1234.com/a/bysj/");
 
        /*
         * 启动爬虫，爬虫从此刻开始执行爬虫任务，根据以上配置
         */
        controller.start(BasicCrawler.class, numberOfCrawlers);
      
	}
	
	private void imageCrawler() throws Exception{
        String storageFolder = "E:/crawl/data"; // 定义爬取的图片本地存储位置
 
        CrawlConfig config = new CrawlConfig(); // 实例化爬虫配置
 
        config.setCrawlStorageFolder(rootFolder); // 设置爬虫文件存储位置
 
        /*
         * 设置允许爬取二进制文件 
         * 因为图片属于二进制文件
         */
        config.setIncludeBinaryContentInCrawling(true);
 
        String[] crawlDomains = {"http://www.java1234.com/"};
 
        /*
         * 实例化爬虫控制器
         */
        PageFetcher pageFetcher = new PageFetcher(config); // 实例化页面获取器
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); // 实例化爬虫机器人配置 比如可以设置 user-agent
         
        // 实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件 规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // 实例化爬虫控制器
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
         
        /*
         * 配置爬虫种子页面，就是规定的从哪里开始爬，可以配置多个种子页面
         */
        for (String domain : crawlDomains) {
          controller.addSeed(domain);
        }
 
        ImageCrawler.configure(crawlDomains, storageFolder); // 配置爬虫域名，以及本地存储位置
 
        /*
         * 启动爬虫，爬虫从此刻开始执行爬虫任务，根据以上配置
         */
        controller.start(ImageCrawler.class, numberOfCrawlers);	
	}
	
	private void  localDataCollector() throws Exception{
	    CrawlConfig config = new CrawlConfig(); // 定义爬虫配置
	    config.setCrawlStorageFolder(rootFolder); // 设置爬虫文件存储位置
	    config.setMaxPagesToFetch(10);  // 设置最大页面获取数
	    config.setPolitenessDelay(1000); // 设置爬取策略 1秒爬一次
	 
	    // 实例化爬虫控制器
	    PageFetcher pageFetcher = new PageFetcher(config); // 实例化页面获取器
	    RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); // 实例化爬虫机器人配置 比如可以设置 user-agent
	     
	    // 实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件 规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
	    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	     
	    // 实例化爬虫控制器
	    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
	 
	    controller.addSeed("http://www.zuidaima.com"); // 添加爬虫种子
	     
	    // 启动爬虫，爬虫从此刻开始执行爬虫任务，根据以上配置
	    controller.start(LocalDataCollectorCrawler.class, numberOfCrawlers);
	 
	    List<Object> crawlersLocalData = controller.getCrawlersLocalData(); // 当多个线程爬虫完成任务时，获取爬虫本地数据
	    long totalLinks = 0;
	    long totalTextSize = 0;
	    int totalProcessedPages = 0;
	    for (Object localData : crawlersLocalData) {
	      CrawlStat stat = (CrawlStat) localData;
	      totalLinks += stat.getTotalLinks();
	      totalTextSize += stat.getTotalTextSize();
	      totalProcessedPages += stat.getTotalProcessedPages();
	    }
	 
	    // 打印数据
	    
	    System.out.println("统计数据：");
	    System.out.println("总处理页面："+totalProcessedPages);
	    System.out.println("总链接长度："+totalLinks);
	    System.out.println("总文本长度："+totalTextSize);	
	}
	
	@SuppressWarnings("deprecation")
	private void multiCrawler() throws Exception{
		CrawlConfig config1 = new CrawlConfig(); // 爬虫配置实例1
	    CrawlConfig config2 = new CrawlConfig(); // 爬虫配置实例2
	 
	    // 两个爬虫实例文件分别存储各种文件夹
	    config1.setCrawlStorageFolder(rootFolder + "/crawler1");
	    config2.setCrawlStorageFolder(rootFolder + "/crawler2");
	 
	    config1.setPolitenessDelay(1000); // 设置1秒爬取一次
	    config2.setPolitenessDelay(2000); // 设置2秒爬取一次
	 
	    config1.setMaxPagesToFetch(5); // 设置最大爬取页数5
	    config2.setMaxPagesToFetch(6); // 设置最大爬取页数6
	 
	    // 使用两个PageFetcher实例
	    PageFetcher pageFetcher1 = new PageFetcher(config1);
	    PageFetcher pageFetcher2 = new PageFetcher(config2);
	 
	    // 使用同一个RobotstxtServer实例
	    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher1);
	 
	    CrawlController controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer);
	    CrawlController controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer);
	 
	    // 分别指定目标爬虫域名
	    String[] crawler1Domains = {"http://www.zuidaima.com/"}; 
	    String[] crawler2Domains = {"http://www.java1234.com/"}; 
	 
	    // 设置自定义数据
	    controller1.setCustomData(crawler1Domains); 
	    controller2.setCustomData(crawler2Domains);
	 
	    // 配置爬虫种子页面，就是规定的从哪里开始爬，可以配置多个种子页面
	    controller1.addSeed("http://www.zuidaima.com/");
	    controller1.addSeed("http://www.zuidaima.com/share/p1-s1.htm");
	 
	    controller2.addSeed("http://www.java1234.com/");
	    controller2.addSeed("http://www.java1234.com/a/bysj/javaweb/");
	 
	    // 启动爬虫，爬虫从此刻开始执行爬虫任务，根据以上配置  根据源码  这种启动是无阻塞的
	    controller1.startNonBlocking(BasicCrawler.class, 5);
	    //controller2.startNonBlocking(BasicCrawler.class, 7);
	    // 休息1秒
	    Thread.sleep(1 * 1000);
	     
	    System.out.println("休息1秒");
	 
	    // 停止爬取
	    controller1.shutdown();
	    System.out.println("停止爬取");
	    
	    controller1.waitUntilFinish(); // 直到完成
	    System.out.println("爬虫1任务结束");
	    
	    controller2.waitUntilFinish();  // 直到完成
	    System.out.println("爬虫2任务结束");
	}
}
