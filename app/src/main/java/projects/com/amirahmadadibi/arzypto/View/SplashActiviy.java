package projects.com.amirahmadadibi.arzypto.View;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpPostCall;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpRequestBuilder;
import projects.com.amirahmadadibi.arzypto.Presenter.SplashPresenter;
import projects.com.amirahmadadibi.arzypto.R;

public class SplashActiviy extends AppCompatActivity {
    public static final String EXTRA_DOLLER_PRICE = "EXTRA_DOLLER_PRICE";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activiy);
        ImageView logo = findViewById(R.id.iv_logo_splash);
        TextView title = findViewById(R.id.txt_splash_title);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(1)
                .playOn(logo);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(1)
                .playOn(title);

        SplashPresenter splashPresenter = new SplashPresenter(SplashActiviy.this);
        splashPresenter.initializeDollerPrice(new SplashPresenter.ImpReceive() {
            @Override
            public void onResponse(double dollarPrice) {
                startApplication(dollarPrice);
            }
        });
    }

    public void startApplication(final double dollarPrice) {

                Intent intent = new Intent(SplashActiviy.this, CoinListActivity.class);
                intent.putExtra(EXTRA_DOLLER_PRICE, dollarPrice);
                startActivity(intent);
    }


}
