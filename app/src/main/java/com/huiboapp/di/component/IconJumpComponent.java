package com.huiboapp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.di.module.IconJumpModule;
import com.huiboapp.mvp.ui.activity.IconJumpActivity;

import dagger.Component;

@ActivityScope
@Component(modules = IconJumpModule.class, dependencies = AppComponent.class)
public interface IconJumpComponent {
    void inject(IconJumpActivity activity);
}