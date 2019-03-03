package projects.com.amirahmadadibi.arzypto.Presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpGetCall;
import projects.com.amirahmadadibi.arzypto.View.CoinChartActivity;

public class CoinChartPresenter {
    public static final String INFO_INTERVAL_HOUR = "h1";
    public static final String INFO_INTERVAL_Day = "d1";
    List<Entry> chartEntry = new ArrayList<>();
    Context context;
    CoinChartActivity coinChartActivity;

    public CoinChartPresenter(Context context, CoinChartActivity coinChartActivity) {
        this.context = context;
        this.coinChartActivity = coinChartActivity;
    }

    public void getChartInfoWithInterval(final String coinID, final String interval, final int unitQuantityOfInterval) {
        chartEntry.clear();
        coinChartActivity.watingForDownloading(true);
        Request request = new Request.Builder()
                .url("https://api.coincap.io/v2/assets/"+coinID+"/history?interval=" + interval)
                .header("X-CMC_PRO_API_KEY", "5d10358e-e718-4f8b-a973-dab7d737e035")
                .build();
        new OkhttpGetCall(request).sendGetRequest(new OkhttpGetCall.responseImp() {
            @Override
            public void onSuccessFulCall(String response) throws JSONException {
                JSONArray jsonPricesArray = new JSONObject(response).getJSONArray("data");
                int size = (jsonPricesArray.length() - unitQuantityOfInterval);
                for (int i = size; i < jsonPricesArray.length(); i++) {
                    JSONObject jsonPriceForSingleDay = jsonPricesArray.getJSONObject(i);
                    Log.d("coinMonth", "onSuc.3cessFulCall: coin info " + jsonPriceForSingleDay.toString());
                    chartEntry.add(new Entry(Float.valueOf(jsonPriceForSingleDay.getString("time")),
                            Float.valueOf(jsonPriceForSingleDay.getString("priceUsd"))));
                }
                coinChartActivity.onGettingNewData(chartEntry);
                setStatusOfDownloadingData();

            }

            @Override
            public void onFailedCall(IOException e) {
                coinChartActivity.errorInFetchingData();
            }
        });
    }

    public List<Entry> getChartEntry() {
        return chartEntry;
    }

    public void setChartEntry(List<Entry> chartEntry) {
        this.chartEntry = chartEntry;
    }

    public void setStatusOfDownloadingData(){
        coinChartActivity.watingForDownloading(false);
    }
}
