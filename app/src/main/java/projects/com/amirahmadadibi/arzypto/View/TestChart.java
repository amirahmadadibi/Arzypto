package projects.com.amirahmadadibi.arzypto.View;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Request;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpGetCall;
import projects.com.amirahmadadibi.arzypto.R;

public class TestChart extends AppCompatActivity {
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chart);
        lineChart = findViewById(R.id.chart);
        makeGetCall();
    }

    public void makeGetCall() {
        Request request = new Request.Builder()
                .url("https://api.coincap.io/v2/assets/ripple/history?interval=d1")
                .header("X-CMC_PRO_API_KEY", "5d10358e-e718-4f8b-a973-dab7d737e035")
                .build();
        new OkhttpGetCall(request).sendGetRequest(new OkhttpGetCall.responseImp() {
            @Override
            public void onSuccessFulCall(String response) throws JSONException {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray  = jsonObject.getJSONArray("data");
                int size = jsonArray.length() - 7;
                for (int i = size; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Log.d("jsonLastSeven", "onSuccessFulCall: "  + jsonObject1.toString());
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = formatter.format(new Date(Long.parseLong(jsonObject.getString("timestamp"))));
                Log.d("coin" ,"onSuccessFulCall: " + dateString);
            }

            @Override
            public void onFailedCall(IOException e) {

            }
        });
    }
}
