package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.HelpCenterContract;
import com.huiboapp.mvp.model.HelpCenterModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class HelpCenterModule {
    private HelpCenterContract.View view;

    /**
     * 构建HelpCenterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HelpCenterModule(HelpCenterContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HelpCenterContract.View provideHelpCenterView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HelpCenterContract.Model provideHelpCenterModel(HelpCenterModel model) {
        return model;
    }
}