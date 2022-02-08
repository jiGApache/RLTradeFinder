package com.chekhurov.rltradefinder.popular_items;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chekhurov.rltradefinder.RLItem;
import com.chekhurov.rltradefinder.Threads.Threads;
import com.chekhurov.rltradefinder.databinding.FragmentPopularBinding;

import java.util.ArrayList;
import java.util.List;

public class PopularFragment extends Fragment {

    private FragmentPopularBinding binding;
    private PopularItemsAdapter adapter;
    private List<RLItem> popularItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPopularBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularItems = new ArrayList<>();

        adapter = new PopularItemsAdapter(popularItems);
        binding.rvTrending.setAdapter(adapter);
        binding.rvTrending.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false)
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        if (popularItems == null || popularItems.size() == 0)
            Threads.loadPopularItemsThread(popularItems, adapter).start();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAG", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy");
    }
}
