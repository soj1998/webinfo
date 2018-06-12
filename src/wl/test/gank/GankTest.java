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
		        	System.out.println("С˵����:"+fictionName);
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
            // ��Function�����У����������� Observable<Object>���д����˴�ʹ��flatMap�������������ε�����
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // ��ԭʼ Observable ֹͣ�����¼��ı�ʶ��Complete���� /  Error������ת����1�� Object �������ݴ��ݸ�1���±��۲��ߣ�Observable��
                // �Դ˾����Ƿ����¶��� & ����ԭ���� Observable������ѯ
                // �˴���2�������
                // 1. ������1��Complete���� /  Error�����¼��������¶��� & ����ԭ���� Observable������ѯ����
                // 2. �����������¼��������¶��� & ����ԭ���� Observable����������ѯ
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // �����ж�����������ѯ���� = 5�κ󣬾�ֹͣ��ѯ
                        if (temp > 3) {
                            // �˴�ѡ����onError�¼��Խ�����ѯ����Ϊ�ɴ������ι۲��ߵ�onError���������ص�
                            return Observable.error(new Throwable("��ѯ����"));
                        }
                        // ����ѯ������4�Σ�����1Next�¼��Լ�����ѯ
                        // ע���˴�������delay������������ = �ӳ�һ��ʱ�䷢�ͣ��˴����� = 2s������ʵ����ѯ��������
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
				String[] nexturl = next.split("��һ��");
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
					File file = new File("E:\\����״Ԫ.txt");
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
            // ��Function�����У����������� Observable<Object>���д����˴�ʹ��flatMap�������������ε�����
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // ��ԭʼ Observable ֹͣ�����¼��ı�ʶ��Complete���� /  Error������ת����1�� Object �������ݴ��ݸ�1���±��۲��ߣ�Observable��
                // �Դ˾����Ƿ����¶��� & ����ԭ���� Observable������ѯ
                // �˴���2�������
                // 1. ������1��Complete���� /  Error�����¼��������¶��� & ����ԭ���� Observable������ѯ����
                // 2. �����������¼��������¶��� & ����ԭ���� Observable����������ѯ
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // �����ж�����������ѯ���� = 5�κ󣬾�ֹͣ��ѯ
                        if (Strings.isNullOrEmpty(url)) {
                            // �˴�ѡ����onError�¼��Խ�����ѯ����Ϊ�ɴ������ι۲��ߵ�onError���������ص�
                            return Observable.error(new Throwable("��ѯ����"));
                        }
                        // ����ѯ������4�Σ�����1Next�¼��Լ�����ѯ
                        // ע���˴�������delay������������ = �ӳ�һ��ʱ�䷢�ͣ��˴����� = 2s������ʵ����ѯ��������
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
				String[] nexturl = next.split("��һ��");
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
					File file = new File("E:\\����״Ԫ.txt");
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
