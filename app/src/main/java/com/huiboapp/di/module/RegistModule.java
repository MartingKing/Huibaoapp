package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.RegistContract;
import com.huiboapp.mvp.model.RegistModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class RegistModule {
    private RegistContract.View view;


    public RegistModule(RegistContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RegistContract.View provideRegistView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RegistContract.Model provideRegistModel(RegistModel model) {
        return model;
    }
}