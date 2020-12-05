package com.huiboapp.di.component;

import com.huiboapp.di.module.StopCarDetailModule;
import com.huiboapp.mvp.ui.activity.StopCarDetailActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = StopCarDetailModule.class, dependencies = AppComponent.class)
public interface StopCarDetailComponent {
    void inject(StopCarDetailActivity activity);
}