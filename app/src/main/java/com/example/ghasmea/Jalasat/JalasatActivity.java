package com.example.ghasmea.Jalasat;

import android.os.Bundle;

import com.example.ghasmea.Jalasat.ui.main.ViewPagerAdapterJ;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.example.ghasmea.databinding.ActivityJalasatBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class    JalasatActivity extends AppCompatActivity {

    private ActivityJalasatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityJalasatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapterJ adapterJ = new ViewPagerAdapterJ(getSupportFragmentManager(), getLifecycle());
        ViewPager2 vwPager = binding.viewPager;
        vwPager.setAdapter(adapterJ);
        TabLayout tabs = binding.tabs;
//        tabs.setupWithViewPager(v wPager); --viewpager1 older method!
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabs, vwPager, true, true,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
                            case 0: tab.setText("جلسات حضوری"); break;
                            case 1: tab.setText("جلسات آنلاین"); break;
                            case 2: tab.setText("جلسات من"); break;
                            case 3: tab.setText("همه جلسات "); break;
                        }
                    }
                });
        tabLayoutMediator.attach();

        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Close", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });
    }
}