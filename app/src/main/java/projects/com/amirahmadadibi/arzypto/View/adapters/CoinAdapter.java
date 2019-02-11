package projects.com.amirahmadadibi.arzypto.View.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
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

        if (coins.get(i).isPriceRaiseFlat()) {
            viewHolder.coinPrice.setTextColor(context.getResources().getColor(R.color.colorGreen));
            viewHolder.ivCoinPriceStatus.setImageResource(R.drawable.ic_price_up);
        }else{
            viewHolder.coinPrice.setTextColor(context.getResources().getColor(R.color.colorRed));
            viewHolder.ivCoinPriceStatus.setImageResource(R.drawable.ic_price_down);
        }
        if (String.valueOf(coins.get(i).getPrice()).equals("0.0")) {
            viewHolder.coinPrice.setText("...");
            viewHolder.ivCoinPriceStatus.setVisibility(View.INVISIBLE);
            viewHolder.coinPrice.setTextColor(context.getResources().getColor(R.color.colorGray));
        } else {
            viewHolder.coinPrice.setText("$ " + String.valueOf(coins.get(i).getPrice()));
            viewHolder.ivCoinPriceStatus.setVisibility(View.VISIBLE);
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
        ImageView ivCoinThumbnail;
        ImageView ivCoinPriceStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coinName = itemView.findViewById(R.id.txt_coin_name);
            coinPrice = itemView.findViewById(R.id.txt_coin_price);
            coinSymbol = itemView.findViewById(R.id.tv_coin_symbol);
            ivCoinThumbnail = itemView.findViewById(R.id.iv_coin_thumnail);
            ivCoinPriceStatus = itemView.findViewById(R.id.iv_icon_price_status);
        }
    }
}
