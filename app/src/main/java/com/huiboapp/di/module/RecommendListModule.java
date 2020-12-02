package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.RecommendListContract;
import com.huiboapp.mvp.model.RecommendListModel;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public class RecommendListModule {
    private RecommendListContract.View view;

    /**
     * 构建RecommendListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RecommendListModule(RecommendListContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RecommendListContract.View provideRecommendListView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    RecommendListContract.Model provideRecommendListModel(RecommendListModel model) {
        return model;
    }
}