package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.SharedParkingContract;
import com.huiboapp.mvp.model.SharedParkingModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class SharedParkingModule {
    private SharedParkingContract.View view;

    public SharedParkingModule(SharedParkingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SharedParkingContract.View provideKDJRecommendView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SharedParkingContract.Model provideKDJRecommendModel(SharedParkingModel model) {
        return model;
    }
}