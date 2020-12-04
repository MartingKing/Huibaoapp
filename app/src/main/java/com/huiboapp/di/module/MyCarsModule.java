package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.MyCarsContract;
import com.huiboapp.mvp.model.MyCarsModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class MyCarsModule {
    private MyCarsContract.View view;


    public MyCarsModule(MyCarsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyCarsContract.View provideRegistView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyCarsContract.Model provideRegistModel(MyCarsModel model) {
        return model;
    }
}