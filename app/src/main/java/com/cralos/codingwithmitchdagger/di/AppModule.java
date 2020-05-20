package com.cralos.codingwithmitchdagger.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.cralos.codingwithmitchdagger.R;
import com.cralos.codingwithmitchdagger.util.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Aqui puede ir uns instancia de retrofit, glide, etc
 */

@Module
public class AppModule {

    @Singleton
    @Provides
    public static RequestOptions provideRequestOptions() {
        return RequestOptions.placeholderOf(R.drawable.white_background).error(R.drawable.white_background);
    }

    /**
     * Sola bastará con inyectar glide desde cualquier parte de la applicación para poder descargar una
     * imagen utilizando glide
     */
    @Singleton
    @Provides
    public static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {  //application se obtiene de AppComponent.class
        return Glide.with(application).setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    public static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.logo);
    }

    /**
     * Creamos en esta parte retrofit ya que se utilizará en toda la aplicación el mismo cliente
     */
    @Singleton
    @Provides
    public static Retrofit provideRetrofitInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(45, TimeUnit.SECONDS)
                .connectTimeout(45, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
