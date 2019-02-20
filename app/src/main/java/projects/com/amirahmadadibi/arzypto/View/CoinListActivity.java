package projects.com.amirahmadadibi.arzypto.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import co.ronash.pushe.Pushe;

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
    Double dollarPrice;
    //some kind of hidden fether for showing dollar price
    public int showMeDollerPriceCounter = 3;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Pushe.initialize(this,true);

        dollarPrice = getIntent().getDoubleExtra(SplashActiviy.EXTRA_DOLLER_PRICE, 0.0);
        CoinListPresenter coinListPresenter = new CoinListPresenter(this, dollarPrice);
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView  = findViewById(R.id.tv_app_title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToShowDollarPrice();
            }
        });
    }

    public void setupCoinListAdatper(List<Coin> coinList) {
        coinAdapter = new CoinAdapter(coinList, this);
    }

    public void tryToShowDollarPrice() {
        showMeDollerPriceCounter--;
        if (showMeDollerPriceCounter == 0) {
            Toast.makeText(this, "قیمت امروز: " + dollarPrice, Toast.LENGTH_SHORT).show();
            showMeDollerPriceCounter = 3;
        }
//        else {
//            Toast.makeText(this, showMeDollerPriceCounter + " بار دیگه امتحان کن!!!", Toast.LENGTH_SHORT).show();
//        }
    }

    public void notifyOnMessageReceviedData() {
        rvMain.postDelayed(new Runnable() {
            @Override
            public void run() {
                coinAdapter.notifyDataSetChanged();
                Log.d("test", "run: activity adapter notifyed");
            }
        }, 1000);
    }
}