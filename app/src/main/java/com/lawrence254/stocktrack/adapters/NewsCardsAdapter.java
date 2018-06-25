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
import com.lawrence254.stocktrack.model.News;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsCardsAdapter extends RecyclerView.Adapter<NewsCardsAdapter.NewsViewHolder>{
    @NonNull

    private ArrayList<News> mNews = new ArrayList<>();
    private Context mContext;

    public NewsCardsAdapter(Context context, ArrayList<News> news){
        mContext = context;
        mNews = news;
    }

    public NewsCardsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cards, parent, false);
        NewsCardsAdapter.NewsViewHolder viewHolder = new NewsCardsAdapter.NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsCardsAdapter.NewsViewHolder holder, int position) {
        holder.bindNews(mNews.get(position));
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
    public class NewsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.newsImage)ImageView mNewsImage;
        @BindView(R.id.headline)TextView mHeadline;
        @BindView(R.id.description) TextView mDescription;
        @BindView(R.id.txtSource)TextView mSource;
        private Context mContext;

        public NewsViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }
        public void bindNews(News newS){
//            char fchar = stocksModel.getQuote().getCompanyName().charAt(0);
//            String first = Character.toString(fchar);
//
//            ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
//            int color = colorGenerator.getRandomColor();
//
//            TextDrawable drawable = TextDrawable.builder()
//                    .buildRound(first, color);
//            mLetter.setImageDrawable(drawable);


            mHeadline.setText(newS.getHeadline());
            mSource.setText(mContext.getString(R.string.Source)+newS.getSource());
            mDescription.setText(newS.getSummary());
//            mNewsImage.setText(String.valueOf(stocksModel.getQuote().getChangePercent())+"%");

        }
    }
}
