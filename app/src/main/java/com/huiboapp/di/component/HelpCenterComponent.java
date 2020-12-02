package com.huiboapp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.huiboapp.di.module.HelpCenterModule;

import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.mvp.ui.activity.HelpCenterActivity;

@ActivityScope
@Component(modules = HelpCenterModule.class, dependencies = AppComponent.class)
public interface HelpCenterComponent {
    void inject(HelpCenterActivity activity);
}