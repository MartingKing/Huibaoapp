package com.huiboapp.di.component;

import com.huiboapp.di.module.ResetPwdModule;
import com.huiboapp.mvp.ui.activity.ResetPwdActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = ResetPwdModule.class, dependencies = AppComponent.class)
public interface ResetPwdComponent {
    void inject(ResetPwdActivity activity);
}