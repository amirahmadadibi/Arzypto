package projects.com.amirahmadadibi.arzypto.Presenter;

import android.os.CountDownTimer;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import projects.com.amirahmadadibi.arzypto.Network.OkhttpGetCall;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpPostCall;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpRequestBuilder;
import projects.com.amirahmadadibi.arzypto.View.SplashActiviy;

public class SplashPresenter {
    SplashActiviy splashActiviy;

    public SplashPresenter(SplashActiviy splashActiviy) {
        this.splashActiviy = splashActiviy;
    }

    public void initializeDollerPrice(final ImpReceive impReceive) {
        new OkhttpGetCall("https://www.arzypto.com/coin/dollarPrice").sendGetRequest(new OkhttpGetCall.responseImp() {
            @Override
            public void onSuccessFulCall(String response) throws JSONException {
                Log.d("qqqq", "onSuccessFulCall: " + response);
                JSONObject jsonObject = new JSONObject(response);
                impReceive.onResponse(Double.valueOf(jsonObject.getString("toman")));

            }

            @Override
            public void onFailedCall(IOException e) {

            }
        });

    }

    public interface ImpReceive {
        void onResponse(double dollarPrice);
    }
}
