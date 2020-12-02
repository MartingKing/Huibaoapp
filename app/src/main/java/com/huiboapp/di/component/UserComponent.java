package com.huiboapp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.huiboapp.di.module.UserModule;
import com.huiboapp.mvp.ui.fragment.UserFragment;

import dagger.Component;

@FragmentScope
@Component(modules = UserModule.class, dependencies = AppComponent.class)
public interface UserComponent {
    void inject(UserFragment fragment);
}