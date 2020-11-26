package com.gopal.dxmindschallenge.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gopal.dxmindschallenge.model.NewsSource;
import com.gopal.dxmindschallenge.ui.ArticlesFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private List<NewsSource.Source> sourceArrayList = new ArrayList<>();

    public SectionsPagerAdapter(Context context, FragmentManager fm, List<NewsSource.Source> sourceList) {
        super(fm);
        mContext = context;
        sourceArrayList = sourceList;
    }

    @Override
    public Fragment getItem(int position) {
        return ArticlesFragment.newInstance(sourceArrayList.get(position).getId());
    }

    @Nullable
    @Override
    public String getPageTitle(int position) {
        return (sourceArrayList.get(position).getName().toString());
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 10;
    }
}