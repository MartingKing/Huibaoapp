package com.huiboapp.di.component;

import com.huiboapp.di.module.StopCarDetailModule;
import com.huiboapp.mvp.ui.activity.PayOrderActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = StopCarDetailModule.class, dependencies = AppComponent.class)
public interface PayOrderComponent {
    void inject(PayOrderActivity activity);
}