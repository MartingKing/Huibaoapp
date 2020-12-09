package com.huiboapp.di.component;

import com.huiboapp.di.module.ParkedHistoryModule;
import com.huiboapp.mvp.ui.activity.ParkedHistoryActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = ParkedHistoryModule.class, dependencies = AppComponent.class)
public interface ParkedHistoryComponent {
    void inject(ParkedHistoryActivity activity);
}