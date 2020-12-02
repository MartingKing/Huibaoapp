package com.huiboapp.di.component;

import com.huiboapp.di.module.VerifyPhoneCodeModule;
import com.huiboapp.mvp.ui.activity.VerifyPhoneCodeActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = VerifyPhoneCodeModule.class, dependencies = AppComponent.class)
public interface VerifyPhoneCodeComponent {
    void inject(VerifyPhoneCodeActivity activity);
}