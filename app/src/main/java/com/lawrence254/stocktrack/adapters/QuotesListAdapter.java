package com.lawrence254.stocktrack.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.model.Quote;
import com.lawrence254.stocktrack.model.StocksModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuotesListAdapter extends RecyclerView.Adapter<QuotesListAdapter.QuotesViewHolder> {

    private ArrayList<StocksModel> mStocks = new ArrayList<>();
    private Context mContext;

    public QuotesListAdapter(Context context, ArrayList<StocksModel> quotes){
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
        private Context mContext;

        public QuotesViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }
        public void bindQuotes(StocksModel stocksModel){
            mstockName.setText(stocksModel.getQuote().getCompanyName());
            mHigh.setText(String.valueOf(stocksModel.getQuote().getHigh()));
            mLow.setText(String.valueOf(stocksModel.getQuote().getLow()));
            mChange.setText(String.valueOf(stocksModel.getQuote().getChangePercent())+"%");

        }
    }

}
