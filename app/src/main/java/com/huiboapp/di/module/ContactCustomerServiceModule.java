package com.huiboapp.di.module;

import com.huiboapp.mvp.contract.ContactCustomerServiceContract;
import com.huiboapp.mvp.model.ContactCustomerServiceModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ContactCustomerServiceModule {
    private ContactCustomerServiceContract.View view;

    /**
     * 构建ContactCustomerServiceModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ContactCustomerServiceModule(ContactCustomerServiceContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ContactCustomerServiceContract.View provideContactCustomerServiceView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ContactCustomerServiceContract.Model provideContactCustomerServiceModel(ContactCustomerServiceModel model) {
        return model;
    }
}