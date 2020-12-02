package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.RecommendProductContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import com.huiboapp.mvp.model.RecommendProductModel;

@Module
public class RecommendProductModule {
    private RecommendProductContract.View view;

    /**
     * 构建RecommendProductModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RecommendProductModule(RecommendProductContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RecommendProductContract.View provideRecommendProductView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RecommendProductContract.Model provideRecommendProductModel(RecommendProductModel model) {
        return model;
    }
}