package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.CalculatorContract;
import com.huiboapp.mvp.model.CalculatorModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class CalculatorModule {
    private CalculatorContract.View view;

    /**
     * 构建CalculatorModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CalculatorModule(CalculatorContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CalculatorContract.View provideCalculatorView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CalculatorContract.Model provideCalculatorModel(CalculatorModel model) {
        return model;
    }
}