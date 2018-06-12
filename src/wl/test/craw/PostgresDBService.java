package wl.test.craw;

import edu.uci.ics.crawler4j.crawler.Page;

public interface PostgresDBService {

    void store(Page webPage);

    void close();
    
    void store2(Page webPage);
}