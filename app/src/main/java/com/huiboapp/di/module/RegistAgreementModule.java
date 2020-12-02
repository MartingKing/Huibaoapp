package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.RegistAgreementContract;
import com.huiboapp.mvp.model.RegistAgreementModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RegistAgreementModule {
    private RegistAgreementContract.View view;

    /**
     * 构建AgreementModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RegistAgreementModule(RegistAgreementContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RegistAgreementContract.View provideAgreementView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RegistAgreementContract.Model provideAgreementModel(RegistAgreementModel model) {
        return model;
    }
}