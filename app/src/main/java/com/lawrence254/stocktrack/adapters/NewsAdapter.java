package com.lawrence254.stocktrack.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.model.Articles;
import com.lawrence254.stocktrack.model.News;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    @NonNull

    private ArrayList<Articles> mNews = new ArrayList<>();
    private Context mContext;

    public NewsAdapter(Context context, ArrayList<Articles> news) {
        mContext = context;
        mNews = news;
    }

    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_news, parent, false);
        NewsAdapter.NewsViewHolder viewHolder = new NewsAdapter.NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bindNews(mNews.get(position));
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newsImage)
        ImageView mNewsImage;
        @BindView(R.id.headline)
        TextView mHeadline;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.txtSource)
        TextView mSource;
        private Context mContext;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindNews(Articles newS) {

            mHeadline.setText(newS.getTitle());
            mSource.setText(mContext.getString(R.string.Source) + newS.getSource().getName());
            mDescription.setText(newS.getDescription());

        }
    }
}


