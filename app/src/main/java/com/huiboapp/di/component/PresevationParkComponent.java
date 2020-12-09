package com.huiboapp.di.component;

import com.huiboapp.di.module.MyPresevationModule;
import com.huiboapp.mvp.ui.activity.PresevationParkActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = MyPresevationModule.class, dependencies = AppComponent.class)
public interface PresevationParkComponent {
    void inject(PresevationParkActivity activity);
}