package com.huiboapp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.huiboapp.di.module.RecommendListModule;

import com.jess.arms.di.scope.FragmentScope;
import com.huiboapp.mvp.ui.fragment.RecommendListFragment;

@FragmentScope
@Component(modules = RecommendListModule.class, dependencies = AppComponent.class)
public interface RecommendListComponent {
    void inject(RecommendListFragment fragment);
}