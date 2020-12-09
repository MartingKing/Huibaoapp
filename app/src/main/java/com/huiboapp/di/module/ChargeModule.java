package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.ChargeContract;
import com.huiboapp.mvp.model.ChargeModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ChargeModule {
    private ChargeContract.View view;

    /**
     * 构建ChargeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChargeModule(ChargeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChargeContract.View provideChargeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChargeContract.Model provideChargeModel(ChargeModel model) {
        return model;
    }
}