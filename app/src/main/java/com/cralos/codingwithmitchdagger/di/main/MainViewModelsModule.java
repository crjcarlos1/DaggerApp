package com.cralos.codingwithmitchdagger.di.main;

import androidx.lifecycle.ViewModel;

import com.cralos.codingwithmitchdagger.di.ViewModelKey;
import com.cralos.codingwithmitchdagger.ui.main.posts.PostsViewModel;
import com.cralos.codingwithmitchdagger.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfleViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel postsViewModel);

}
