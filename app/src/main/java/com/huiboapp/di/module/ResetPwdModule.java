package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.ResetPwdContract;
import com.huiboapp.mvp.model.ResetPwdModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ResetPwdModule {
    private ResetPwdContract.View view;

    /**
     * 构建LoginModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ResetPwdModule(ResetPwdContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ResetPwdContract.View provideResetPwdView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ResetPwdContract.Model provideResetPwdModel(ResetPwdModel model) {
        return model;
    }
}