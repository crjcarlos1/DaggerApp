package com.cralos.codingwithmitchdagger;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.cralos.codingwithmitchdagger.models.User;
import com.cralos.codingwithmitchdagger.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    public static final String TAG = SessionManager.class.getSimpleName();

    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    /**
     * Agregamos la notacion Inject por que se estrá inyectando en varios lugares de la aplicación
     */
    @Inject
    public SessionManager() {
    }

    public void authenticatedWithId(LiveData<AuthResource<User>> source) {
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.loading((User) null));
            cachedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> authResource) {
                    cachedUser.setValue(authResource);
                    cachedUser.removeSource(source);
                }
            });
        } else {
            Log.e("SESSION_MANAGER", "Authenticated: previous session detected. Retreiving user from cache");
        }
    }

    public void logout() {
        cachedUser.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return cachedUser;
    }

}
