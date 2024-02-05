package com.example.ghasmea.Jalasat.ui.main;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ghasmea.Jalasat.fragment.*;
import com.example.ghasmea.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewPagerAdapterJ extends FragmentStateAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};

    public ViewPagerAdapterJ(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment;

        switch (position)
        {
            case 0: fragment = JInPersonFragment.newInstance(); break;
            case 1: fragment = JOnlineFragment.newInstance(); break;
            case 2: fragment = new JFavFragment(); break;
            case 3: fragment =  JAllFragment.newInstance(); break;
            default: return null;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}