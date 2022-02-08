package com.chekhurov.rltradefinder.Main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.chekhurov.rltradefinder.R;
import com.chekhurov.rltradefinder.databinding.ActivityMainLayoutBinding;

public class MainActivity extends FragmentActivity {

    ActivityMainLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainLayoutBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.viewPager.setAdapter(new MainFragmentStateAdapter(this));
        binding.viewPager.setUserInputEnabled(false);

        binding.bottomNavigationView.setSelectedItemId(R.id.trending_menu_item);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.trending_menu_item:
                    binding.viewPager.setCurrentItem(MainFragmentStateAdapter.POPULAR_POSITION);
                    return true;
                case R.id.favorite_menu_item:
                    binding.viewPager.setCurrentItem(MainFragmentStateAdapter.FAVORITE_POSITION);
                    return true;
                case R.id.new_item_menu:
                    binding.viewPager.setCurrentItem(MainFragmentStateAdapter.NEW_ITEM_POSITION);
                    return true;
            }
            return false;
        });
    }

}
