package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.MyPresevationContract;
import com.huiboapp.mvp.model.MyPresevationModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class MyPresevationModule {
    private MyPresevationContract.View view;

    /**
     * 构建MyPresevationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyPresevationModule(MyPresevationContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyPresevationContract.View provideMyPresevationView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyPresevationContract.Model provideMyPresevationModel(MyPresevationModel model) {
        return model;
    }
}