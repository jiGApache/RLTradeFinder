package com.chekhurov.rltradefinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekhurov.rltradefinder.databinding.FragmentNewItemsBinding;

public class NewItemFragment extends Fragment {

    FragmentNewItemsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewItemsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

}
