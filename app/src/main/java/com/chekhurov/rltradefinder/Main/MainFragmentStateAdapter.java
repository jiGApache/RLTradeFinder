package com.chekhurov.rltradefinder.Main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.chekhurov.rltradefinder.FavoriteItems.FavoriteFragment;
import com.chekhurov.rltradefinder.NewItems.NewItemFragment;
import com.chekhurov.rltradefinder.PopularItems.PopularFragment;

import java.util.Arrays;
import java.util.List;

public class MainFragmentStateAdapter extends FragmentStateAdapter {

    private static final List<Fragment> bottomNavFragments =
            Arrays.asList(new PopularFragment(), new FavoriteFragment(), new NewItemFragment());

    public static final int POPULAR_POSITION = 0;
    public static final int FAVORITE_POSITION = 1;
    public static final int NEW_ITEM_POSITION = 2;

    public MainFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return bottomNavFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return bottomNavFragments.size();
    }
}
