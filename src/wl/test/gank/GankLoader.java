package wl.test.gank;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Url;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import com.wl.runzekeji.util.http.ObjectLoader;
import com.wl.runzekeji.util.http.RetrofitServiceManager;

public class GankLoader extends ObjectLoader {
    private static final String GANK_URL = "http://gank.io/api/data/福利/50/2";
    private GankService mGankService ;
    public GankLoader(){
        mGankService = RetrofitServiceManager.getInstance().create(GankService.class);
    }

    /**
     * 获取干货列表
     * @return
     */
    public Observable<List<GankEntry>> getGankList(){
        return observe(mGankService.getGank(GANK_URL)).map(new Function<GankResp, List<GankEntry>>() {
            @Override
            public List<GankEntry> apply(GankResp gankResp) throws Exception {
                return gankResp.results;
            }
        });
    }


    public interface GankService{
        /**
         *
         * @param url
         * @param
         * @param
         * @return
         */
        @GET
        Observable<GankResp> getGank(@Url String url/*, @Path("count")int count,@Path("page")int page*/);
    }
}
