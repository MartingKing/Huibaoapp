package com.huiboapp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.huiboapp.di.module.WebviewModule;

import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.mvp.ui.activity.WebviewActivity;

@ActivityScope
@Component(modules = WebviewModule.class, dependencies = AppComponent.class)
public interface WebviewComponent {
    void inject(WebviewActivity activity);
}