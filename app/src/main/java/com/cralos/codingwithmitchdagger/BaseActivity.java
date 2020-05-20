package com.cralos.codingwithmitchdagger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.cralos.codingwithmitchdagger.models.User;
import com.cralos.codingwithmitchdagger.ui.auth.AuthActivity;
import com.cralos.codingwithmitchdagger.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriberObserverst();
    }

    /**
     * Dentro de este metodo es donde queremos observar el
     * estado de la autenticación (loggin -> success or failture)
     */
    private void subscriberObserverst() {
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> authResource) {
                if (authResource != null) {
                    switch (authResource.status) {
                        case LOADING: {
                            break;
                        }
                        case AUTHENTICATED: {
                            Log.e(TAG, "SUCCESS LOGIN: " + authResource.data.getName());
                            break;
                        }
                        case ERROR: {
                            Log.e(TAG, "Error: " + authResource.message);
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            navLoginScreen();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

}
