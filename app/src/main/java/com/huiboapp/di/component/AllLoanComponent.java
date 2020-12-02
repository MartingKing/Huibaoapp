package com.huiboapp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.huiboapp.di.module.AllLoanModule;
import com.huiboapp.mvp.ui.fragment.AllLoanProFragment;

import dagger.Component;

@FragmentScope
@Component(modules = AllLoanModule.class, dependencies = AppComponent.class)
public interface AllLoanComponent {
    void inject(AllLoanProFragment fragment);
}