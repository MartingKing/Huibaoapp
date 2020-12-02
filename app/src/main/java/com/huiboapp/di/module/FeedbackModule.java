package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.FeedbackContract;
import com.huiboapp.mvp.model.FeedbackModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class FeedbackModule {
    private FeedbackContract.View view;

    /**
     * 构建FeedbackModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FeedbackModule(FeedbackContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FeedbackContract.View provideFeedbackView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FeedbackContract.Model provideFeedbackModel(FeedbackModel model) {
        return model;
    }
}