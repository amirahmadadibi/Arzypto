package projects.com.amirahmadadibi.arzypto.Presenter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import projects.com.amirahmadadibi.arzypto.Network.OkhttpPostCall;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpRequestBuilder;
import projects.com.amirahmadadibi.arzypto.View.SplashActiviy;

public class SplashPresenter {
    SplashActiviy splashActiviy;

    public SplashPresenter(SplashActiviy splashActiviy) {
        this.splashActiviy = splashActiviy;
        initializeDollerPrice();
    }

    private void initializeDollerPrice() {
        new OkhttpRequestBuilder()
                .setmUrl("https://currency.arzypto.com/price")
                .createGetRequest()
                .sendGerRequest(new OkhttpPostCall.responseImp() {
                    @Override
                    public void onSuccessFulCall(String response) throws JSONException {
                        JSONObject result = new JSONObject(response);
                        launchCoinListActivity(Double.valueOf(result.getString("sell")));
                    }

                    @Override
                    public void onFailedCall(IOException e) {
                        Log.d("arzypto", "onFailedCall: " + e);
                    }
                });
    }

    public void launchCoinListActivity(double dollerPrice) {
        splashActiviy.startApplication(dollerPrice);
    }
}
