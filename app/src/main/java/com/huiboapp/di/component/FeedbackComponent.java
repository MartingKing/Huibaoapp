package com.huiboapp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.di.module.FeedbackModule;
import com.huiboapp.mvp.ui.activity.FeedbackActivity;

import dagger.Component;

@ActivityScope
@Component(modules = FeedbackModule.class, dependencies = AppComponent.class)
public interface FeedbackComponent {
    void inject(FeedbackActivity activity);
}