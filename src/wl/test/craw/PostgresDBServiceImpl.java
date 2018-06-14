package wl.test.craw;


import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import retrofit2.http.GET;
import retrofit2.http.Url;
import com.google.common.base.Strings;
import com.wl.runzekeji.data.local.sqldata.JdbcUtils;
import com.wl.runzekeji.util.DateUtil;
import com.wl.runzekeji.util.StringRegUtils;
import com.wl.runzekeji.util.http.RetrofitServiceManager;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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
		boolean sf1=webPage.getWebURL().getURL().endsWith("/content.html");
		boolean sf2=false;
		if(!Strings.isNullOrEmpty(webPage.getWebURL().getParentUrl())){
			sf2=webPage.getWebURL().getParentUrl().endsWith("n810755/index.html");
		}
		boolean sf3=webPage.getWebURL().getURL().startsWith("http://www.chinatax.gov.cn/n810341/n810755");;
		if (sf1&&sf2&&sf3&&webPage.getParseData() instanceof HtmlParseData) {
			saveRetrofit(webPage.getWebURL().getURL(),webPage.getWebURL().getParentUrl());
        }
		
	}
	
	private void saveRetrofit(String url,String parentUrl){
		TestService ts=RetrofitServiceManager.getInstance().create(TestService.class);
  		ts.getBody(url)
		.map(new Function<ResponseBody, Document>() {
			@Override
			public Document apply(ResponseBody body) throws Exception {
				Document doc = Jsoup.parse(body.string(),url);
				
				return doc;
			}
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.io())
		.subscribe(new Consumer<Document>() {
			@Override
			public void accept(Document doc) throws Exception {
				String sql="insert into test2 (biaoti,wenhao,shijian,content,text,html,time,url,furl) values (?,?,?,?,?,?,?,?,?)";
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
                try {
					params[2] = DateUtil.formatDateInSql(t, "yyyy-mm-dd");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
                params[3] = doc.select("#tax_content").text();
                params[4] = doc.text();
                params[5] = doc.html();
                params[6] = new Timestamp(new java.util.Date().getTime());
                params[7] = url;
                params[8] = parentUrl;
                JdbcUtils.update(sql, params);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				System.out.println(throwable);
			}
		});
	}
	
	public interface TestService{
        /**
         *
         * @param url
         * @param
         * @param
         * @return
         */
        @GET
        Observable<ResponseBody> getBody(@Url String url/*, @Path("count")int count,@Path("page")int page*/);
    }
}