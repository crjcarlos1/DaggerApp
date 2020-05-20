package com.cralos.codingwithmitchdagger.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.cralos.codingwithmitchdagger.R;
import com.cralos.codingwithmitchdagger.models.User;
import com.cralos.codingwithmitchdagger.ui.main.MainActivity;
import com.cralos.codingwithmitchdagger.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private EditText userIdEdt;
    private Button btnLogin;

    private AuthViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManagerGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        progressBar = findViewById(R.id.progress_bar);
        userIdEdt = findViewById(R.id.user_id_input);
        btnLogin = findViewById(R.id.login_button);
        btnLogin.setOnClickListener(this);
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);
        setLogo();
        subscribeObservers();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void subscribeObservers() {
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case AUTHENTICATED: {
                            showProgressBar(false);
                            Log.e("USER_AUTH", "SUCCESS LOGIN: " + userAuthResource.data.getName());
                            onLogginSuccess();
                            break;
                        }
                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(getApplicationContext(), userAuthResource.message + "\nDid you enter a number between 0-10?", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void onLogginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userIdEdt.getText().toString()))
            Toast.makeText(getApplicationContext(), "Ingresar id", Toast.LENGTH_SHORT).show();
        else
            viewModel.authenticateWithId(Integer.valueOf(userIdEdt.getText().toString()));
    }

    private void setLogo() {
        requestManagerGlide.load(logo).into((ImageView) findViewById(R.id.login_logo));
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

}
