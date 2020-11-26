package com.gopal.dxmindschallenge.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gopal.dxmindschallenge.R;
import com.gopal.dxmindschallenge.adapter.NewsAdapter;
import com.gopal.dxmindschallenge.model.NewsArticle;
import com.gopal.dxmindschallenge.networking.AppStatus;
import com.gopal.dxmindschallenge.viewmodels.ArticlesViewModel;
import com.gopal.dxmindschallenge.viewmodels.TabPageViewModel;
import com.gopal.dxmindschallenge.web.ClickInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArticlesFragment extends Fragment implements ClickInterface {

    private static final String ARG_SOURCE_ID = "source_id";
    private TabPageViewModel pageViewModel;

    private Context mContext;
    private String sectionId = "";
    private ArrayList<NewsArticle> articleArrayList = new ArrayList<>();
    private RecyclerView rvHeadline;
    private NewsAdapter newsAdapter;

    public static ArticlesFragment newInstance(String itemId) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SOURCE_ID, itemId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(TabPageViewModel.class);

        if (getArguments() != null) {
            sectionId = getArguments().getString(ARG_SOURCE_ID);
        }
        pageViewModel.setId(sectionId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = ArticlesFragment.this.getActivity();
        rvHeadline = root.findViewById(R.id.rvNews);

        /*get source id by clicking tabs*/
        pageViewModel.getId().observe(ArticlesFragment.this.getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                /*api calling*/
                CallApiForArticlesList(s);
            }
        });
        return root;
    }

    private void CallApiForArticlesList(String sourceId) {
        ArticlesViewModel newsViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        /*connection check*/
        if (!AppStatus.getInstance(mContext).isOnline()) {
            Toast.makeText(mContext, "Connection Error,Try Again!", Toast.LENGTH_SHORT).show();
            return;
        }


        /*Get Article List from api*/
        newsViewModel.initArticleApi(sourceId);
        newsViewModel.getArticleRepository().observe(this, newsSource -> {
            /*clear when move to next tab*/
            newsAdapter = null;
            articleArrayList.clear();

            List<NewsArticle> articles = newsSource.getArticles();
            articleArrayList.addAll(articles);
            setupRecyclerView(articleArrayList);

        });

    }

    private void setupRecyclerView(ArrayList<NewsArticle> articleArrayList) {

        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(mContext, articleArrayList, this);
            rvHeadline.setLayoutManager(new LinearLayoutManager(mContext));
            rvHeadline.setAdapter(newsAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void recyclerviewOnClick(View v, int position) {
        String urls = articleArrayList.get(position).getUrl();
        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra("URL", urls);
        startActivity(intent);
    }


}