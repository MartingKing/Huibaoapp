package com.huiboapp.di.component;

import com.huiboapp.di.module.ChargeModule;
import com.huiboapp.mvp.ui.activity.ChargeActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = ChargeModule.class, dependencies = AppComponent.class)
public interface ChargeComponent {
    void inject(ChargeActivity activity);
}