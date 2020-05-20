package com.cralos.codingwithmitchdagger.di;

import android.app.Application;

import com.cralos.codingwithmitchdagger.BaseApplication;
import com.cralos.codingwithmitchdagger.SessionManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * ESTA ES LA CLASE QUE SE TIEN QUE COPIAR TAL Y COMO ESTA
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        AppModule.class,
        ViewModelFactoryModule.class})      /**va a ser usado por todos los view models del proyecto*/
public interface AppComponent extends AndroidInjector<BaseApplication> {

    /**sessionManager será accesible a toda la aplicación*/
    SessionManager sessionManager();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
