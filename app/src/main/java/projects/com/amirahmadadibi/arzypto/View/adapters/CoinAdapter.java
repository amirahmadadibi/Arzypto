package projects.com.amirahmadadibi.arzypto.View.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.DecimalFormat;
import java.util.List;

import projects.com.amirahmadadibi.arzypto.Model.Coin;
import projects.com.amirahmadadibi.arzypto.R;
import projects.com.amirahmadadibi.arzypto.View.CoinChartActivity;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.ViewHolder> {

    List<Coin> coins;
    Context context;
    DecimalFormat formatter = new DecimalFormat("#,###,###");

    public CoinAdapter(List<Coin> coins, Context context) {
        this.coins = coins;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setOnClickListener();
        viewHolder.coinName.setText(coins.get(i).getFarsiName());//تتر
        viewHolder.coinSymbol.setText(coins.get(i).getCoinSymbol());//USDT
        viewHolder.ivCoinThumbnail.setImageResource(coins.get(i).getCoinResourceFileId());//coin logo
        //if we have not receive any data yet
        if (String.valueOf(coins.get(i).getPrice()).equals("0.0")) {
            switchFromLoadToShow(false, viewHolder);
        } else {
            switchFromLoadToShow(true, viewHolder);
            if (coins.get(i).getPrice() < 1.00) {
                //shows until six decimal digits for some coin's and format them using dollarFormatter
                viewHolder.coinPriceInDollar.setText("$ " + String.format("%.6f", coins.get(i).getPrice()));
            } else {
                //only shows until tow decimal digits
                viewHolder.coinPriceInDollar.setText("$ " + String.format("%.2f", coins.get(i).getPrice()));
            }
            String price = String.format("%.0f", coins.get(i).getPriceInToman());
            viewHolder.coinPriceInToman.setText(String.valueOf(formatter.format(Double.valueOf(price))) + " تومان");
        }

        //check for situation fo price coin is going up or down
        if (coins.get(i).isPriceRaiseFlag()) {
            viewHolder.coinPriceInDollar.setTextColor(context.getResources().getColor(R.color.colorGreen));
            viewHolder.ivCoinPriceStatus.setImageResource(R.drawable.ic_price_up);
            YoYo.with(Techniques.FadeInUp)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.ivCoinPriceStatus);
            YoYo.with(Techniques.FadeInUp)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.coinPriceInDollar);
        } else {
            viewHolder.coinPriceInDollar.setTextColor(context.getResources().getColor(R.color.colorRed));
            viewHolder.ivCoinPriceStatus.setImageResource(R.drawable.ic_price_down);
            YoYo.with(Techniques.FadeInDown)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.ivCoinPriceStatus);
            YoYo.with(Techniques.FadeInDown)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.coinPriceInDollar);
        }
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView coinName;
        TextView coinPriceInDollar;
        TextView coinSymbol;
        TextView coinPriceInToman;
        TextView coinLoadData;
        ImageView ivCoinThumbnail;
        ImageView ivCoinPriceStatus;
        CardView itemCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coinName = itemView.findViewById(R.id.txt_coin_name);
            coinPriceInDollar = itemView.findViewById(R.id.txt_coin_price);
            coinSymbol = itemView.findViewById(R.id.tv_coin_symbol);
            coinLoadData = itemView.findViewById(R.id.txt_load_data);
            coinPriceInToman = itemView.findViewById(R.id.txt_coin_price_in_toman);
            ivCoinThumbnail = itemView.findViewById(R.id.iv_coin_thumnail);
            ivCoinPriceStatus = itemView.findViewById(R.id.iv_icon_price_status);
            itemCardView = itemView.findViewById(R.id.coin_item_card);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/DHannaThin.ttf");
            coinPriceInDollar.setTypeface(typeFace);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            switch (v.getId()){
                case R.id.coin_item_card:
                    Intent intent = new Intent(context, CoinChartActivity.class);
                    intent.putExtra("coinID",coins.get(getAdapterPosition()).getIdName());
                    context.startActivity(intent);
                    break;
                    default:
                        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();

            }
        }

        public void setOnClickListener(){
            itemCardView.setOnClickListener(ViewHolder.this);
        }
    }

    public void switchFromLoadToShow(boolean show, ViewHolder viewHolder) {
        if (show) {
            viewHolder.coinLoadData.setVisibility(View.INVISIBLE);
            viewHolder.coinPriceInDollar.setVisibility(View.VISIBLE);
            viewHolder.ivCoinPriceStatus.setVisibility(View.VISIBLE);
            viewHolder.coinPriceInToman.setVisibility(View.VISIBLE);
        } else {
            viewHolder.coinLoadData.setVisibility(View.VISIBLE);
            viewHolder.coinPriceInDollar.setVisibility(View.INVISIBLE);
            viewHolder.ivCoinPriceStatus.setVisibility(View.INVISIBLE);
            viewHolder.coinPriceInToman.setVisibility(View.INVISIBLE);
        }
    }
}
