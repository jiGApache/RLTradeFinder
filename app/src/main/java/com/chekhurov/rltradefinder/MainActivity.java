package com.chekhurov.rltradefinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.chekhurov.rltradefinder.databinding.ActivityMainLayoutBinding;

public class MainActivity extends Activity {

    ActivityMainLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainLayoutBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
    }
}
