package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.VerifyPhoneCodeContract;
import com.huiboapp.mvp.model.VerifyPhoneCodeModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class VerifyPhoneCodeModule {
    private VerifyPhoneCodeContract.View view;

    /**
     * 构建LoginModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VerifyPhoneCodeModule(VerifyPhoneCodeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VerifyPhoneCodeContract.View provideVerifyPhoneCodeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VerifyPhoneCodeContract.Model provideVerifyPhoneCodeModel(VerifyPhoneCodeModel model) {
        return model;
    }
}