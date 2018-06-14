package com.lawrence254.stocktrack.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.model.Quote;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuotesListAdapter extends RecyclerView.Adapter<QuotesListAdapter.QuotesViewHolder> {

    private ArrayList<Quote> mQuotes = new ArrayList<>();
    private Context mContext;

    public QuotesListAdapter(Context context, ArrayList<Quote> quotes){
        mContext = context;
        mQuotes = quotes;
    }
    @Override
    public QuotesListAdapter.QuotesViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_card, parent, false);
        QuotesViewHolder viewHolder = new QuotesViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(QuotesListAdapter.QuotesViewHolder holder, int position) {
        holder.bindQuotes(mQuotes.get(position));
    }
    @Override
    public int getItemCount() {
        return mQuotes.size();
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
        public void bindQuotes(Quote quote){
            mstockName.setText(quote.getCompanyName());
            mHigh.setText(quote.getHigh().toString());
            mLow.setText(quote.getLow().toString());
            mChange.setText(quote.getChangePercent().toString()+"%");

        }
    }

}
