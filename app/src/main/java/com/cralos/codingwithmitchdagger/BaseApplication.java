package com.cralos.codingwithmitchdagger;

import com.cralos.codingwithmitchdagger.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();      /**El metodo application se encuentra dentro de la interface AppComponent*/
    }

}
