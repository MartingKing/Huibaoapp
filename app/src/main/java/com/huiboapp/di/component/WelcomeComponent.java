package com.huiboapp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.huiboapp.di.module.WelcomeModule;

import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.mvp.ui.activity.WelcomeActivity;

@ActivityScope
@Component(modules = WelcomeModule.class, dependencies = AppComponent.class)
public interface WelcomeComponent {
    void inject(WelcomeActivity activity);
}