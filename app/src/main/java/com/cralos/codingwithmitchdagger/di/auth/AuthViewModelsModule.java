package com.cralos.codingwithmitchdagger.di.auth;

import androidx.lifecycle.ViewModel;

import com.cralos.codingwithmitchdagger.di.ViewModelKey;
import com.cralos.codingwithmitchdagger.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);


}
