package com.huiboapp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.huiboapp.di.module.CalculatorModule;

import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.mvp.ui.activity.CalculatorActivity;

@ActivityScope
@Component(modules = CalculatorModule.class, dependencies = AppComponent.class)
public interface CalculatorComponent {
    void inject(CalculatorActivity activity);
}