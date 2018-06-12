package wl.test.gank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Strings;
import com.wl.runzekeji.util.http.RetrofitServiceManager;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GankTest {
	private String title;
	private String text;
	private String url = "http://www.biquge5200.com/31_31746/12331189.html";
	private static final int SERVICE_LATENCY_IN_MILLIS = 5000;
	private int temp = 0 ;
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
			.map(new Function<ResponseBody, Document>() {
	            @Override
	            public Document apply(ResponseBody body) throws Exception {
	                return Jsoup.parse(body.string(),"https://book.qidian.com/info/1006141474#Catalog");
	            }
	        })
		    .subscribeOn(Schedulers.io())
		    .observeOn(Schedulers.io())
		    .subscribe(new Consumer<Document>() {
		        @Override
		        public void accept(Document doc) throws Exception {
		        	String fictionName = doc.select("h1>em").text();
		        	System.out.println("小说名称:"+fictionName);
		        	Elements results = doc.select("a[data-cid]");
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
	
	public void test3(){
		//url = "http://www.biquge5200.com/31_31746/12331189.html";
		url = "https://www.biquge5200.cc/31_31746/12331190.html";
		TestService ts=RetrofitServiceManager.getInstance().create(TestService.class);
		Observable.just(url)
		.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (temp > 3) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS);
                    }
                });

            }
        })
        .flatMap(new Function<String, ObservableSource<ResponseBody>>(){

			@Override
			public ObservableSource<ResponseBody> apply(String t) throws Exception {
				// TODO Auto-generated method stub
				return ts.getBody(t);
			}})
		.map(new Function<ResponseBody, String>() {
			@Override
			public String apply(ResponseBody body) throws Exception {
				Document doc = Jsoup.parse(body.string(),url);
				title = doc.title();
				text = doc.select("#content").text();
				Elements nextdiv = doc.select(".bottem2");
				String next = nextdiv.toString();
				String[] nexturl = next.split("下一章");
				next = nexturl[0];
				nexturl = next.split("\u2192");
				next = nexturl[1];
				next = next.substring(12, next.length() - 2); 
				return next;
			}
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.io())
		.subscribe(new Consumer<String>() {
			@Override
			public void accept(String doc) throws Exception {
				FileWriter fileWriter = null;
				try {
					File file = new File("E:\\寒门状元.txt");
					fileWriter = new FileWriter(file,true);
					fileWriter.write(title);
					fileWriter.write(text);
					fileWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					fileWriter.close();
				}
				temp++;
				url=doc;
				System.out.println(url);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				System.out.println(throwable);
			}
		});
		
	}
	
	
	public void test4(){
		url = "http://www.biquge5200.com/31_31746/12331189.html";
		TestService ts=RetrofitServiceManager.getInstance().create(TestService.class);
  		Observable.just("11")        
		.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (Strings.isNullOrEmpty(url)) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS);
                    }
                });

            }
        })
        .flatMap(new Function<String,Observable<ResponseBody>>(){

			@Override
			public Observable<ResponseBody> apply(String t) throws Exception {
				System.out.println(url);
				return ts.getBody(url);
			}})
		.map(new Function<ResponseBody, String>() {
			@Override
			public String apply(ResponseBody body) throws Exception {
				Document doc = Jsoup.parse(body.string(),url);
				title = doc.title();
				text = doc.select("#content p").toString();
				text = text.replace("<p>","  ");
				text = text.replace("</p>","\n\r");
				Elements nextdiv = doc.select(".bottem2");
				String next = nextdiv.toString();
				String[] nexturl = next.split("下一章");
				next = nexturl[0];
				nexturl = next.split("\u2192");
				next = nexturl[1];
				next = next.substring(12, next.length() - 2); 
				return next;
			}
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.io())
		.subscribe(new Consumer<String>() {
			@Override
			public void accept(String doc) throws Exception {
				temp++;
				url=doc;
				FileWriter fileWriter = null;
				try {
					File file = new File("E:\\寒门状元.txt");
					fileWriter = new FileWriter(file,true);
					fileWriter.write(title);
					fileWriter.write(text);
					fileWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					fileWriter.close();
				}
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
