package com.cralos.codingwithmitchdagger.di;

import com.cralos.codingwithmitchdagger.di.auth.AuthModule;
import com.cralos.codingwithmitchdagger.di.auth.AuthViewModelsModule;
import com.cralos.codingwithmitchdagger.di.main.MainFragmentBuildersModule;
import com.cralos.codingwithmitchdagger.di.main.MainModule;
import com.cralos.codingwithmitchdagger.di.main.MainViewModelsModule;
import com.cralos.codingwithmitchdagger.ui.auth.AuthActivity;
import com.cralos.codingwithmitchdagger.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * SIEMPRE TENDRÁ QUE DECLARAR LAS ACTIVITIES DENTRO DE ESTA ACTIVIDAD (ActivityBuilderModele)
 * ASÍ QUE, EN CASO DE TENER 10 ACTIVITIES, SE AGREGARÍAN 10 (@ContributesAndroidInjector)
 */
@Module
public abstract class ActivityBuildersModule {

    /**
     * este será un subcomponente de la aplicación
     */
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    /**
     * este será un subcomponente de la aplicación
     */
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class})
    abstract MainActivity contributeMainActivity();


}
