package wl.test.craw;

import edu.uci.ics.crawler4j.crawler.CrawlController;


/**
 * Created by rz on 03.06.2016.
 */
public class PostgresCrawlerFactory implements CrawlController.WebCrawlerFactory<PostgresWebCrawler> {

    

    public PostgresCrawlerFactory() {
        
    }

    public PostgresWebCrawler newInstance() throws Exception {
        return new PostgresWebCrawler(new PostgresDBServiceImpl());
    }
}
