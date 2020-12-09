package com.huiboapp.di.component;

import com.huiboapp.di.module.FastPayModule;
import com.huiboapp.mvp.ui.activity.FastPayActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = FastPayModule.class, dependencies = AppComponent.class)
public interface FastPayComponent {
    void inject(FastPayActivity activity);
}