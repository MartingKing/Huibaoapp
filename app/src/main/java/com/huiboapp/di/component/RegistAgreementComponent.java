package com.huiboapp.di.component;

import com.huiboapp.di.module.RegistAgreementModule;
import com.huiboapp.mvp.ui.activity.RegistAgreementActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = RegistAgreementModule.class, dependencies = AppComponent.class)
public interface RegistAgreementComponent {
    void inject(RegistAgreementActivity activity);
}