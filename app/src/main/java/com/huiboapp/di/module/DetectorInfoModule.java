package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.DetectorInfoContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;



@Module
public class DetectorInfoModule {
    private DetectorInfoContract.View view;

    /**
     * 构建DetectorInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DetectorInfoModule(DetectorInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DetectorInfoContract.View provideDetectorInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DetectorInfoContract.Model provideDetectorInfoModel(com.huiboapp.mvp.model.DetectorInfoModel model) {
        return model;
    }
}