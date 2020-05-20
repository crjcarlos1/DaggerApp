package com.cralos.codingwithmitchdagger.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cralos.codingwithmitchdagger.R;
import com.cralos.codingwithmitchdagger.models.User;
import com.cralos.codingwithmitchdagger.ui.auth.AuthResource;
import com.cralos.codingwithmitchdagger.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    private ProfileViewModel viewModel;

    private TextView username,email,website;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "Fragment profile was created...");
        email=view.findViewById(R.id.email);
        username=view.findViewById(R.id.username);
        website=view.findViewById(R.id.website);
        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> authResource) {
                if (authResource!=null){
                    switch (authResource.status){
                        case AUTHENTICATED:{
                            setUserDetails(authResource.data);
                            break;
                        }
                        case ERROR:{
                            setErrorDetails(authResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setUserDetails(User data) {
        username.setText(data.getUsername());
        email.setText(data.getEmail());
        website.setText(data.getWebsite());
    }

    private void setErrorDetails(String message){
        email.setText(message);
        website.setText("error");
        username.setText("error");
    }

}
