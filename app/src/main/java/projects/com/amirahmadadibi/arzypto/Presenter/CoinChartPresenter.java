package projects.com.amirahmadadibi.arzypto.Presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpGetCall;
import projects.com.amirahmadadibi.arzypto.View.CoinChartActivity;

public class CoinChartPresenter {
    public static final String INFO_INTERVAL_HOUR = "h1";
    public static final String INFO_INTERVAL_Day = "d1";
    public static final int INFO_INTERVAL_WeekChangePersentage = 14;
    public static final int INFO_INTERVAL_DayChangePersentage = 60;
    public static final int INFO_INTERVAL_MonthChangePersentage = 48;
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
                .url("https://api.coincap.io/v2/assets/" + coinID + "/history?interval=" + interval)
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

    public void setStatusOfDownloadingData() {
        coinChartActivity.watingForDownloading(false);
    }

    public void getChangePersentageInPastTwoWeeks(String coinID, final int pastInterval, final int nowInterval) {
        //for week pastInterval gonna be INFO_INTERVAL_WeekChangePercentage and nowInterval gonna be 7
        final Request request = new Request.Builder()
                .url("https://api.coincap.io/v2/assets/" + coinID + "/history?interval=d1")
                .header("X-CMC_PRO_API_KEY", "5d10358e-e718-4f8b-a973-dab7d737e035")
                .build();
        new OkhttpGetCall(request).sendGetRequest(new OkhttpGetCall.responseImp() {
            @Override
            public void onSuccessFulCall(String response) throws JSONException {

                Double paswWeekPrice = 0.0;
                Double thisWeekPrice = 0.0;
                boolean decreseChange = false;
                JSONArray jsonPricesArray = new JSONObject(response).getJSONArray("data");
                ///interval past for example this with INFO_INTERVAL_MonthChangePersentage
                int size = (jsonPricesArray.length() - pastInterval);

                for (int i = size; i < jsonPricesArray.length() - nowInterval; i++) {
                    JSONObject jsonPriceForSingleDay = jsonPricesArray.getJSONObject(i);
                    paswWeekPrice += Double.valueOf(jsonPriceForSingleDay.getString("priceUsd"));
                }
                ///interval now for example this with INFO_INTERVAL_Day
                size = (jsonPricesArray.length() - nowInterval);
                for (int i = size; i < jsonPricesArray.length(); i++) {
                    JSONObject jsonPriceForSingleDay = jsonPricesArray.getJSONObject(i);
                    thisWeekPrice += Double.valueOf(jsonPriceForSingleDay.getString("priceUsd"));
                }
                if (thisWeekPrice < paswWeekPrice) {
                    decreseChange = true;
                }

                Double result = ((paswWeekPrice - thisWeekPrice) / paswWeekPrice) * 100;
                result = Math.round(result * 100.0) / 100.0;//round to to decimal digits a.00
                Log.d("persent", "onSuccessFulCall: " + result);


            }

            @Override
            public void onFailedCall(IOException e) {
                coinChartActivity.errorInFetchingData();
            }
        });
    }
}
