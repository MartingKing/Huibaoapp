package com.huiboapp.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.huiboapp.mvp.contract.UserContract;
import com.huiboapp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class UserModule {
    private UserContract.View view;

    /**
     * 构建UserModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserModule(UserContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    UserContract.View provideUserView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    UserContract.Model provideUserModel(UserModel model) {
        return model;
    }
}