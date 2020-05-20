package com.cralos.codingwithmitchdagger.di.auth;

import com.cralos.codingwithmitchdagger.network.auth.AuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    /**  desde esta clase se puede acceder a el parametro retrofit porque la clase ActivityBuildersModule esta dentro de un subsomponente*/
    @Provides
    public static AuthApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }

}
