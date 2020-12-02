package com.huiboapp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.huiboapp.di.module.RecommendProductModule;

import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.mvp.ui.activity.RecommendProductActivity;

@ActivityScope
@Component(modules = RecommendProductModule.class, dependencies = AppComponent.class)
public interface RecommendProductComponent {
    void inject(RecommendProductActivity activity);
}