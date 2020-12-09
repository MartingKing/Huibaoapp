package com.huiboapp.di.component;

import com.huiboapp.di.module.SettingModule;
import com.huiboapp.mvp.ui.activity.TuikuanActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = SettingModule.class, dependencies = AppComponent.class)
public interface TuikuanComponent {
    void inject(TuikuanActivity activity);
}