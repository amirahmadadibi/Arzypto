package projects.com.amirahmadadibi.arzypto.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import projects.com.amirahmadadibi.arzypto.Coin;
import projects.com.amirahmadadibi.arzypto.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Coin> coins = new ArrayList<>();

    public MyAdapter(List<Coin> coins) {
        this.coins = coins;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.coinName.setText(coins.get(i).getName());
        viewHolder.coinPrice.setText(String.valueOf(coins.get(i).getPrice()));
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView coinName;
        TextView coinPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coinName = itemView.findViewById(R.id.txt_coin_name);
            coinPrice = itemView.findViewById(R.id.txt_coin_price);
        }
    }
}
