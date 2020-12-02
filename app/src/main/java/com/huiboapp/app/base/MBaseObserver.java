package com.huiboapp.app.base;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaojian on 2018/5/28 15:18
 */
public class MBaseObserver {

    public static <T> ObservableTransformer<T, T> io_main() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> new_main() {
        return upstream -> upstream.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

}
