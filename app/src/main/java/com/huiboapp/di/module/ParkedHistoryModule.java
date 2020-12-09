package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.ParkedHistoryContract;
import com.huiboapp.mvp.model.ParkedHistoryModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ParkedHistoryModule {
    private ParkedHistoryContract.View view;


    public ParkedHistoryModule(ParkedHistoryContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ParkedHistoryContract.View provideParkedHistoryView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ParkedHistoryContract.Model provideParkedHistoryModel(ParkedHistoryModel model) {
        return model;
    }
}