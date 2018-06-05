package wl.test.gank;

import java.util.Iterator;
import java.util.List;









import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wl.runzekeji.util.http.RetrofitServiceManager;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GankTest {
	public void test1(){
		new GankLoader().getGankList()
		    .subscribeOn(Schedulers.io())
		    .observeOn(Schedulers.io())
		    .subscribe(new Consumer<List<GankEntry>>() {
		        @Override
		        public void accept(List<GankEntry> gankEntries) throws Exception {
		            Iterator<GankEntry> it=gankEntries.listIterator();
		            while(it.hasNext()){
		            	GankEntry gank=it.next();
		            	System.out.println(gank.url);
		            }
		        }
		    }, new Consumer<Throwable>() {
		        @Override
		        public void accept(Throwable throwable) throws Exception {
		
		        }
		    });
	}
	
	public void test2(){
		TestService ts=RetrofitServiceManager.getInstance().create(TestService.class);
		ts.getBody("https://book.qidian.com/info/1006141474#Catalog")
		    .subscribeOn(Schedulers.io())
		    .observeOn(Schedulers.io())
		    .subscribe(new Consumer<ResponseBody>() {
		        @Override
		        public void accept(ResponseBody body) throws Exception {
		        	Document document=Jsoup.parse(body.string(),"https://book.qidian.com/info/1006141474#Catalog");
		        	String fictionName = document.select("h1>em").text();
		        	System.out.println("Ð¡ËµÃû³Æ"+fictionName);
		        	Elements results = document.select("a[data-cid]");
		            for (Element e : results) {
		                String fictionChapter = e.text();
		                String fictionUrl = e.attr("abs:href");
		                System.out.println(fictionChapter+":"+fictionUrl);
		            }
		        }
		    }, new Consumer<Throwable>() {
		        @Override
		        public void accept(Throwable throwable) throws Exception {
		
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
