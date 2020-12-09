package com.huiboapp.di.component;

import com.huiboapp.di.module.SharedParkingModule;
import com.huiboapp.mvp.ui.activity.SharedParkingActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = SharedParkingModule.class, dependencies = AppComponent.class)
public interface SharedParkingComponent {
    void inject(SharedParkingActivity activity);
}