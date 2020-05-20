package com.cralos.codingwithmitchdagger.di;

import androidx.lifecycle.ViewModelProvider;

import com.cralos.codingwithmitchdagger.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    /**     y esto va a ser responsable de generar la dependencia que hace a la di  para la clase de factory */

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);

}
