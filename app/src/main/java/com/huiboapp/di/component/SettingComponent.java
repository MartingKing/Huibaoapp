package com.huiboapp.di.component;

import com.huiboapp.mvp.ui.activity.SettingActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.di.module.SettingModule;

import dagger.Component;

@ActivityScope
@Component(modules = SettingModule.class, dependencies = AppComponent.class)
public interface SettingComponent {
    void inject(SettingActivity activity);
}