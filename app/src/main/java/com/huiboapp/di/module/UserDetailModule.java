package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.UserDetailContract;
import com.huiboapp.mvp.model.UserDetailModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class UserDetailModule {
    private UserDetailContract.View view;

    /**
     * 构建UserDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserDetailModule(UserDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserDetailContract.View provideUserDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserDetailContract.Model provideUserDetailModel(UserDetailModel model) {
        return model;
    }
}