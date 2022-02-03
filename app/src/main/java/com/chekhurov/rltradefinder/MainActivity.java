package com.chekhurov.rltradefinder;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.chekhurov.rltradefinder.databinding.ActivityMainLayoutBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainLayoutBinding binding;
    FragmentManager frManager;

    TrendingFragment trendingFragment;
    FavoriteFragment favoriteFragment;
    NewItemFragment newItemFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frManager = getSupportFragmentManager();

        trendingFragment = new TrendingFragment();
        favoriteFragment = new FavoriteFragment();
        newItemFragment = new NewItemFragment();

        frManager.beginTransaction()
                .replace(R.id.fragment_container_view, trendingFragment)
                .commit();

        binding = ActivityMainLayoutBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.bottomNavigationView.setSelectedItemId(R.id.trending_menu_item);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.trending_menu_item:
                    frManager.beginTransaction()
                            .replace(R.id.fragment_container_view, trendingFragment)
                            .commit();
                    break;
                case R.id.favorite_menu_item:
                    frManager.beginTransaction()
                            .replace(R.id.fragment_container_view, favoriteFragment)
                            .commit();
                    break;
                case R.id.new_item_menu:
                    frManager.beginTransaction()
                            .replace(R.id.fragment_container_view, newItemFragment)
                            .commit();
                    break;
                default:
                    return false;
            }
            return true;
        });
    }

}
