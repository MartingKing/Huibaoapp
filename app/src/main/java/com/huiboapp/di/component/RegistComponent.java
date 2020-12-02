package com.huiboapp.di.component;

import com.huiboapp.di.module.RegistModule;
import com.huiboapp.mvp.ui.activity.RegistActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = RegistModule.class, dependencies = AppComponent.class)
public interface RegistComponent {
    void inject(RegistActivity activity);
}