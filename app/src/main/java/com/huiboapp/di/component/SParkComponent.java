package com.huiboapp.di.component;

import com.huiboapp.di.module.SParkModule;
import com.huiboapp.mvp.ui.activity.SearchParkActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = SParkModule.class, dependencies = AppComponent.class)
public interface SParkComponent {
    void inject(SearchParkActivity activity);
}