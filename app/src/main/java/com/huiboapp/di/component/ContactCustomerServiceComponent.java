package com.huiboapp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.huiboapp.di.module.ContactCustomerServiceModule;

import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.mvp.ui.activity.ContactCustomerServiceActivity;

@ActivityScope
@Component(modules = ContactCustomerServiceModule.class, dependencies = AppComponent.class)
public interface ContactCustomerServiceComponent {
    void inject(ContactCustomerServiceActivity activity);
}