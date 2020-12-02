package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.AllLoanContract;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.huiboapp.mvp.model.AllLoanModel;


@Module
public class AllLoanModule {
    private AllLoanContract.View view;

    /**
     * 构建RecommendModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AllLoanModule(AllLoanContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    AllLoanContract.View provideRecommendView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    AllLoanContract.Model provideRecommendModel(AllLoanModel model) {
        return model;
    }
}