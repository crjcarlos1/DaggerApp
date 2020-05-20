package com.cralos.codingwithmitchdagger.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cralos.codingwithmitchdagger.SessionManager;
import com.cralos.codingwithmitchdagger.models.User;
import com.cralos.codingwithmitchdagger.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    public static final String TAG = ProfileViewModel.class.getSimpleName();

    private SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.e(TAG, "ProfileViewModel: viewmodel is ready...");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser() {
        return sessionManager.getAuthUser();
    }


}
