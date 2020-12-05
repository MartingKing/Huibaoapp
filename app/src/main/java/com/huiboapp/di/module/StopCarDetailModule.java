package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.StopCarDetailContract;
import com.huiboapp.mvp.model.StopCarDetailModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class StopCarDetailModule {
    private StopCarDetailContract.View view;

    /**
     * 构建StopCarDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public StopCarDetailModule(StopCarDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StopCarDetailContract.View provideStopCarDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StopCarDetailContract.Model provideStopCarDetailModel(StopCarDetailModel model) {
        return model;
    }
}