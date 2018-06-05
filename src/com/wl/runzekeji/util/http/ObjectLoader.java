package com.wl.runzekeji.util.http;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ObjectLoader {
    protected  <T> Observable<T> observe(Observable<T> observable){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
