package com.huiboapp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.di.module.AgreementWebModule;
import com.huiboapp.mvp.ui.activity.AgreementWebActivity;

import dagger.Component;

@ActivityScope
@Component(modules = AgreementWebModule.class, dependencies = AppComponent.class)
public interface AgreementWebComponent {
    void inject(AgreementWebActivity activity);
}