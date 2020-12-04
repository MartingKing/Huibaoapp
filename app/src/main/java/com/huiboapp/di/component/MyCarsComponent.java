package com.huiboapp.di.component;

import com.huiboapp.di.module.MyCarsModule;
import com.huiboapp.mvp.ui.activity.MyCarsActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = MyCarsModule.class, dependencies = AppComponent.class)
public interface MyCarsComponent {
    void inject(MyCarsActivity activity);
}