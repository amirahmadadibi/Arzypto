package projects.com.amirahmadadibi.arzypto.View;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import projects.com.amirahmadadibi.arzypto.Model.Coin;
import projects.com.amirahmadadibi.arzypto.Presenter.CoinListPresenter;
import projects.com.amirahmadadibi.arzypto.R;
import projects.com.amirahmadadibi.arzypto.View.adapters.CoinAdapter;

public class CoinListActivity extends AppCompatActivity {

    public RecyclerView rvMain;
    public CoinAdapter coinAdapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CoinListPresenter coinListPresenter = new CoinListPresenter(this);
        coinListPresenter.runWebSocket();
        rvMain = findViewById(R.id.rv_main);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMain.setAdapter(coinAdapter);
        rvMain.setLayoutManager(layoutManager);

    }

    public void setupCoinListAdatper(List<Coin> coinList) {
        coinAdapter = new CoinAdapter(coinList, this);
    }


    public void notifyOnMessageReceviedData() {
        rvMain.postDelayed(new Runnable() {
            @Override
            public void run() {
                coinAdapter.notifyDataSetChanged();
            }
        }, 4000);
    }
}