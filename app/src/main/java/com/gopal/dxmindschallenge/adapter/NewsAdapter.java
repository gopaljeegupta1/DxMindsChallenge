package com.gopal.dxmindschallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gopal.dxmindschallenge.R;
import com.gopal.dxmindschallenge.model.NewsArticle;
import com.gopal.dxmindschallenge.web.ClickInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<NewsArticle> articles;

    // Define listener member variable
    private ClickInterface onClickListener;

    public NewsAdapter(Context context, ArrayList<NewsArticle> articles, ClickInterface listener) {
        this.context = context;
        this.articles = articles;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        holder.tvName.setText(articles.get(position).getTitle().toString());
        holder.tvDesCription.setText(articles.get(position).getDescription());
        Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.ivNews);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDesCription;
        ImageView ivNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDesCription = itemView.findViewById(R.id.tvDesCription);
            ivNews = itemView.findViewById(R.id.ivNews);

            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.recyclerviewOnClick(v, getAdapterPosition());
                        }
                    }
                }
            });


        }
    }
}