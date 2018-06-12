package wl.test.craw;


import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import com.wl.runzekeji.data.local.sqldata.JdbcUtils;
import com.wl.runzekeji.util.DateUtil;
import com.wl.runzekeji.util.StringRegUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PostgresDBServiceImpl implements PostgresDBService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PostgresDBServiceImpl.class);

    @Override
    public void store(Page page) {

        if (page.getParseData() instanceof HtmlParseData) {
            try {

                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                String sql="insert into test1 (html,text,url,time) values (?,?,?,?)";
                Object[] params= new Object[4];
                params[0] = htmlParseData.getHtml();
                params[1] = htmlParseData.getText();
                params[2] = page.getWebURL().getURL();
                params[3] = new Timestamp(new java.util.Date().getTime());
                JdbcUtils.update(sql, params);
            } catch (SQLException e) {
                logger.error("SQL Exception while storing webpage for url'{}'", page.getWebURL().getURL(), e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() {
        
    }

	@Override
	public void store2(Page webPage) {
		boolean sf=webPage.getWebURL().getURL().endsWith("n810755/index.html");
		if (!sf&&webPage.getParseData() instanceof HtmlParseData) {
            try {
                HtmlParseData htmlParseData = (HtmlParseData) webPage.getParseData();
                String sql="insert into test2 (biaoti,wenhao,shijian,content,text,html,time,url,furl) values (?,?,?,?,?,?,?,?,?)";
                Document doc=null;
				try {
					doc = Jsoup.connect(webPage.getWebURL().getURL()).get();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                Elements e=doc.select(".sv_textcon li");
                String biaoti=null;
                String wenhao=null;
                if(e.size()>2){
                	biaoti=e.get(1).text();
                	wenhao=e.get(2).text();
                }
                Object[] params= new Object[9];
                params[0] = biaoti;
                params[1] = wenhao;
                String tfbsj=doc.select("#tax_content").text();
                String fbsj=tfbsj.substring(tfbsj.length()-100>0?tfbsj.length()-100:0, tfbsj.length());
                String t=StringRegUtils.getLastDatesReg(fbsj);
                t=t.replace("Äê", "-");
                t=t.replace("ÔÂ", "-");
                t=t.replace("ÈÕ", "");
                System.out.println(t);
                try {
					params[2] = DateUtil.formatDateInSql(t, "yyyy-mm-dd");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
                params[3] = doc.select("#tax_content").text();
                params[4] = htmlParseData.getText();
                params[5] = htmlParseData.getHtml();
                params[6] = new Timestamp(new java.util.Date().getTime());
                params[7] = webPage.getWebURL().getURL();
                params[8] = webPage.getWebURL().getParentUrl();
                JdbcUtils.update(sql, params);
            } catch (SQLException e) {
                logger.error("SQL Exception while storing webpage for url'{}'", webPage.getWebURL().getURL(), e);
                throw new RuntimeException(e);
            }
        }
		
	}
}