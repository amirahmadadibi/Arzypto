package projects.com.amirahmadadibi.arzypto.View.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import projects.com.amirahmadadibi.arzypto.Model.Coin;
import projects.com.amirahmadadibi.arzypto.R;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.ViewHolder> {

    List<Coin> coins;
    Context context;

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
        viewHolder.coinName.setText(coins.get(i).getFarsiName());
        viewHolder.coinSymbol.setText(coins.get(i).getCoinSymbol());

        //check for situation fo price coin is going up or down
        if (coins.get(i).isPriceRaiseFlat()) {
            viewHolder.coinPrice.setTextColor(context.getResources().getColor(R.color.colorGreen));
            viewHolder.ivCoinPriceStatus.setImageResource(R.drawable.ic_price_up);
            YoYo.with(Techniques.FadeInUp)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.ivCoinPriceStatus);
            YoYo.with(Techniques.FadeInUp)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.coinPrice);
        } else {
            viewHolder.coinPrice.setTextColor(context.getResources().getColor(R.color.colorRed));
            viewHolder.ivCoinPriceStatus.setImageResource(R.drawable.ic_price_down);
            YoYo.with(Techniques.FadeInDown)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.ivCoinPriceStatus);
            YoYo.with(Techniques.FadeInDown)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.coinPrice);
        }

        //if we have not receive any data yet
        if (String.valueOf(coins.get(i).getPrice()).equals("0.0")) {
            viewHolder.coinPrice.setText("دریافت آخرین قیمت ...");
            YoYo.with(Techniques.FadeIn)
                    .duration(2000)
                    .repeat(1)
                    .playOn(viewHolder.coinPrice);
            viewHolder.coinPrice.setTextColor(context.getResources().getColor(R.color.colorGray));
            viewHolder.ivCoinPriceStatus.setVisibility(View.INVISIBLE);
            viewHolder.coinPriceInToman.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.coinPrice.setText("$ " + String.format("%.2f", coins.get(i).getPrice()));
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String price = String.format("%.0f", coins.get(i).getPriceInToman());
            viewHolder.coinPriceInToman.setText(String.valueOf(formatter.format(Double.valueOf(price))) + " تومان");
            YoYo.with(Techniques.FadeIn)
                    .duration(700)
                    .repeat(1)
                    .playOn(viewHolder.coinPriceInToman);
            viewHolder.ivCoinPriceStatus.setVisibility(View.VISIBLE);
            viewHolder.coinPriceInToman.setVisibility(View.VISIBLE);
        }

        viewHolder.ivCoinThumbnail.setImageResource(coins.get(i).getCoinResourceFileId());

    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView coinName;
        TextView coinPrice;
        TextView coinSymbol;
        TextView coinPriceInToman;
        ImageView ivCoinThumbnail;
        ImageView ivCoinPriceStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coinName = itemView.findViewById(R.id.txt_coin_name);
            coinPrice = itemView.findViewById(R.id.txt_coin_price);
            coinSymbol = itemView.findViewById(R.id.tv_coin_symbol);
            coinPriceInToman = itemView.findViewById(R.id.txt_coin_price_in_toman);
            ivCoinThumbnail = itemView.findViewById(R.id.iv_coin_thumnail);
            ivCoinPriceStatus = itemView.findViewById(R.id.iv_icon_price_status);
        }
    }
}
