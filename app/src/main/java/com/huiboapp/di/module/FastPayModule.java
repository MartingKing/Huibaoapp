package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.FastPayContract;
import com.huiboapp.mvp.model.FastPayModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class FastPayModule {
    private FastPayContract.View view;

    /**
     * 构建ChargeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FastPayModule(FastPayContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FastPayContract.View provideFastPayView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FastPayContract.Model provideChargeModel(FastPayModel model) {
        return model;
    }
}