package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.AddCarContract;
import com.huiboapp.mvp.model.AddCarModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class AddCarModule {
    private AddCarContract.View view;

    /**
     * 构建AddCarModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AddCarModule(AddCarContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddCarContract.View provideAddCarView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AddCarContract.Model provideAddCarModel(AddCarModel model) {
        return model;
    }
}