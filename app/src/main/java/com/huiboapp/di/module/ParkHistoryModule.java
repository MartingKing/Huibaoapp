package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.ParkHistoryContract;
import com.huiboapp.mvp.model.ParkHistoryModel;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ParkHistoryModule {
    private ParkHistoryContract.View view;

    /**
     * 构建ParkHistoryModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ParkHistoryModule(ParkHistoryContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ParkHistoryContract.View provideParkHistoryView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ParkHistoryContract.Model provideParkHistoryModel(ParkHistoryModel model) {
        return model;
    }
}