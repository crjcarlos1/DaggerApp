package com.cralos.codingwithmitchdagger.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.cralos.codingwithmitchdagger.SessionManager;
import com.cralos.codingwithmitchdagger.models.User;
import com.cralos.codingwithmitchdagger.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    public static final String TAG = AuthViewModel.class.getSimpleName();

    private AuthApi authApi;

    private SessionManager sessionManager;

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        if (authApi == null) Log.e(TAG, "ES NULL");
        else Log.e(TAG, "NO ES NULL");
    }

    private LiveData<AuthResource<User>> queryUserId(int id) {
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(id)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                Log.e("ERROR_THROWABLE", "ERROR: " + throwable.getMessage());
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(user -> {
                            if (user.getId() == -1)
                                return AuthResource.error("could not autenticated", (User) null);
                            else
                                return AuthResource.authenticated(user);
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public void authenticateWithId(int id) {
        Log.d(TAG, "Authenticated: attemting login");
        sessionManager.authenticatedWithId(queryUserId(id));
    }

}
