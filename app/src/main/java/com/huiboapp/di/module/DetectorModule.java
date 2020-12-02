package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.DetectorContract;
import com.huiboapp.mvp.model.DetectorModel;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public class DetectorModule {
    private DetectorContract.View view;

    /**
     * 构建DetectorModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DetectorModule(DetectorContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    DetectorContract.View provideDetectorView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    DetectorContract.Model provideDetectorModel(DetectorModel model) {
        return model;
    }
}