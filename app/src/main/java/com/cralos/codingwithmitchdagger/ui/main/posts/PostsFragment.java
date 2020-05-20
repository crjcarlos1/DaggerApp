package com.cralos.codingwithmitchdagger.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cralos.codingwithmitchdagger.R;
import com.cralos.codingwithmitchdagger.models.Post;
import com.cralos.codingwithmitchdagger.ui.main.Resource;
import com.cralos.codingwithmitchdagger.util.VerticalSpacingItemDecoration;
import com.cralos.codingwithmitchdagger.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {
    public static final String TAG = PostsFragment.class.getSimpleName();

    private PostsViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    PostsRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        viewModel = ViewModelProviders.of(this, providerFactory).get(PostsViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case LOADING: {
                            Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Loading ...");
                            break;
                        }
                        case SUCCESS: {
                            Log.e(TAG, "Datos obtenidos: " + listResource.data.size());
                            adapter.setPosts(listResource.data);
                            break;
                        }
                        case ERROR: {
                            Toast.makeText(getContext(), listResource.message, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        /** you could also proview LinearLayoutManager as a dependency, also item decoration*/
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VerticalSpacingItemDecoration decoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
    }

}
