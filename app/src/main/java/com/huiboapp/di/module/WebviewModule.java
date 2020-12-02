package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.WebviewContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class WebviewModule {
    private WebviewContract.View view;

    /**
     * 构建WebviewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WebviewModule(WebviewContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WebviewContract.View provideWebviewView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WebviewContract.Model provideWebviewModel(com.huiboapp.mvp.model.WebviewModel model) {
        return model;
    }
}