package com.huiboapp.di.component;

import com.huiboapp.di.module.DetectorModule;
import com.huiboapp.mvp.ui.fragment.DetectorFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = DetectorModule.class, dependencies = AppComponent.class)
public interface DetectorComponent {
    void inject(DetectorFragment fragment);
}