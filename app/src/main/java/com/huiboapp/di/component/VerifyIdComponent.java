package com.huiboapp.di.component;

import com.huiboapp.di.module.UserDetailModule;
import com.huiboapp.mvp.ui.activity.VerifyIdActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = UserDetailModule.class, dependencies = AppComponent.class)
public interface VerifyIdComponent {
    void inject(VerifyIdActivity activity);
}