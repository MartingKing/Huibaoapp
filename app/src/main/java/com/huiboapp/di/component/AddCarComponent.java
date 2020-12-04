package com.huiboapp.di.component;

import com.huiboapp.di.module.AddCarModule;
import com.huiboapp.mvp.ui.activity.AddCarActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = AddCarModule.class, dependencies = AppComponent.class)
public interface AddCarComponent {
    void inject(AddCarActivity activity);
}