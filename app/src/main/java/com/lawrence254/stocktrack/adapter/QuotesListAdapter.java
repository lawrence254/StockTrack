package com.lawrence254.stocktrack.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.model.Quote;
import com.lawrence254.stocktrack.model.StocksModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuotesListAdapter extends RecyclerView.Adapter<QuotesListAdapter.QuotesViewHolder> {

    private List<Quote> mStocks = new ArrayList<>();
    private Context mContext;

    public QuotesListAdapter(Context context, List<Quote> quotes){
        mContext = context;
        mStocks = quotes;
    }


    @Override
    public QuotesListAdapter.QuotesViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_card, parent, false);
        QuotesViewHolder viewHolder = new QuotesViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(QuotesListAdapter.QuotesViewHolder holder, int position) {
        holder.bindQuotes(mStocks.get(position));
    }
    @Override
    public int getItemCount() {
        return mStocks.size();
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.stockName)TextView mstockName;
        @BindView(R.id.sHigh)TextView mHigh;
        @BindView(R.id.sLow) TextView mLow;
        @BindView(R.id.sChange)TextView mChange;
        @BindView(R.id.stockLetter)ImageView mLetter;
        private Context mContext;

        public QuotesViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }
        public void bindQuotes(Quote stocksModel){
            char fchar = stocksModel.getCompanyName().charAt(0);
            String first = Character.toString(fchar);

            ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
            int color = colorGenerator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(first, color);
            mLetter.setImageDrawable(drawable);

            mstockName.setText(stocksModel.getCompanyName());
            mHigh.setText(String.valueOf(stocksModel.getHigh()));
            mLow.setText(String.valueOf(stocksModel.getLow()));
            mChange.setText(String.valueOf(stocksModel.getChangePercent())+"%");

        }
    }

}
