package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.SParkContract;
import com.huiboapp.mvp.model.SParkModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class SParkModule {
    private SParkContract.View view;

    /**
     * 构建SParkModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SParkModule(SParkContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SParkContract.View provideSParkView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SParkContract.Model provideSParkModel(SParkModel model) {
        return model;
    }
}