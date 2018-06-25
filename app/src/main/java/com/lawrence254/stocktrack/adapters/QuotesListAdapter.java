package com.lawrence254.stocktrack.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.fragments.NewsFragment;
import com.lawrence254.stocktrack.fragments.StockDetailsFragment;
import com.lawrence254.stocktrack.model.News;
import com.lawrence254.stocktrack.model.Quote;
import com.lawrence254.stocktrack.model.StocksModel;
import com.lawrence254.stocktrack.ui.StockDetailsActivity;

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
        @BindView(R.id.stockLetter)ImageView mLetter;
        private Context mContext;

        public QuotesViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();


        }
        public void bindQuotes(StocksModel stocksModel){
            final String symbol = stocksModel.getQuote().getSymbol();

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
//                    Intent intent = new Intent(v.getContext(), StockDetailsFragment.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("symbol",symbol);

                    StockDetailsFragment fragment = new StockDetailsFragment();
                    Toast.makeText(mContext, "Sent Data "+symbol+" to StockDetailsFraagment", Toast.LENGTH_SHORT).show();
                    fragment.setArguments(bundle);
//                    intent.putExtra("symbol",symbol);
//                    v.getContext().startActivity(intent);

                }
            });

            char fchar = stocksModel.getQuote().getCompanyName().charAt(0);
            String first = Character.toString(fchar);

            ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
            int color = colorGenerator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(first, color);
            mLetter.setImageDrawable(drawable);

            mstockName.setText(stocksModel.getQuote().getCompanyName());
            mHigh.setText(String.valueOf(stocksModel.getQuote().getHigh()));
            mLow.setText(String.valueOf(stocksModel.getQuote().getLow()));
            mChange.setText(String.valueOf(stocksModel.getQuote().getChangePercent())+"%");



        }
    }

}
