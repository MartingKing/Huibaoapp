package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.IconJumpContract;
import com.jess.arms.di.scope.ActivityScope;
import com.huiboapp.mvp.model.IconJumpModel;
import dagger.Module;
import dagger.Provides;


@Module
public class IconJumpModule {
    private IconJumpContract.View view;

    /**
     * 构建KDJRecommendModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public IconJumpModule(IconJumpContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    IconJumpContract.View provideKDJRecommendView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IconJumpContract.Model provideKDJRecommendModel(IconJumpModel model) {
        return model;
    }
}