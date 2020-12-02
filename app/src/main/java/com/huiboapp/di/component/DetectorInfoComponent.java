package com.huiboapp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.huiboapp.di.module.DetectorInfoModule;

import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.mvp.ui.activity.DetectorInfoActivity;

@ActivityScope
@Component(modules = DetectorInfoModule.class, dependencies = AppComponent.class)
public interface DetectorInfoComponent {
    void inject(DetectorInfoActivity activity);
}