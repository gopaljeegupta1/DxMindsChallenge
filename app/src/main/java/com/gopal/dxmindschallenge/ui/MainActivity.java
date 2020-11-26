package com.gopal.dxmindschallenge.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.gopal.dxmindschallenge.R;
import com.gopal.dxmindschallenge.model.NewsSource;
import com.gopal.dxmindschallenge.networking.AppStatus;
import com.gopal.dxmindschallenge.viewmodels.ArticlesViewModel;
import com.gopal.dxmindschallenge.adapter.SectionsPagerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArticlesViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        /*connection check*/
        if (!AppStatus.getInstance(this).isOnline()) {
            Toast.makeText(this, "Connection Error,Try Again!", Toast.LENGTH_SHORT).show();
            return;
        }

        /*Get Source List from api*/
        newsViewModel.initSourceApi();
        newsViewModel.getSourceRepository().observe(this, newsSource -> {
            initTabs(newsSource.getSources());
        });

    }

    /*Initialize tabs*/
    private void initTabs(List<NewsSource.Source> sourceArrayList) {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), sourceArrayList);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}