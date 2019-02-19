package projects.com.amirahmadadibi.arzypto.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import projects.com.amirahmadadibi.arzypto.Model.Coin;
import projects.com.amirahmadadibi.arzypto.Presenter.CoinListPresenter;
import projects.com.amirahmadadibi.arzypto.R;
import projects.com.amirahmadadibi.arzypto.View.adapters.CoinAdapter;

public class CoinListActivity extends AppCompatActivity {

    public RecyclerView rvMain;
    public CoinAdapter coinAdapter;
    public Toolbar toolbar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Double dollarPrice = getIntent().getDoubleExtra(SplashActiviy.EXTRA_DOLLER_PRICE,0.0);
        CoinListPresenter coinListPresenter = new CoinListPresenter(this,dollarPrice);
        coinListPresenter.runWebSocket();
        initViews();
    }

    private void initViews() {
        rvMain = findViewById(R.id.rv_main);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMain.setAdapter(coinAdapter);
        rvMain.setLayoutManager(layoutManager);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle("ارزیپتو");
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
        },3000);
    }
}