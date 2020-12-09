package com.huiboapp.di.component;

import com.huiboapp.di.module.ParkHistoryModule;
import com.huiboapp.mvp.ui.fragment.ParkHistoryFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = ParkHistoryModule.class, dependencies = AppComponent.class)
public interface ParkHistoryComponent {
    void inject(ParkHistoryFragment fragment);
}