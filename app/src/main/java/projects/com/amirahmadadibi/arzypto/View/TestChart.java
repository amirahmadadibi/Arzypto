package projects.com.amirahmadadibi.arzypto.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
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

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import okhttp3.Request;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpGetCall;
import projects.com.amirahmadadibi.arzypto.R;

public class TestChart extends AppCompatActivity {
    LineChart lineChart;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
    List<Entry> chartEntry = new ArrayList<>();
    Typeface typeFace;
    ArrayList<String> days = new ArrayList<>();
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chart);
        lineChart = findViewById(R.id.chart);
        makeGetCall();
        typeFace = Typeface.createFromAsset(this.getAssets(), "fonts/IRANYekanMobileMedium.ttf");
        lineChart.setNoDataText("در حال بارگزاری اطلاعات...");
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);//draw recangle with solid background color
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.setDrawBorders(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.isScaleXEnabled();
        lineChart.isScaleYEnabled();
        //make chart full width
        lineChart.setViewPortOffsets(0f, 20f, 0f, 20f);
        //customizingXAxis();
        //customizingYAxis();

    }

    private void setDataValuesForChart() {
        LineDataSet dataSet = new LineDataSet(chartEntry, ""); // add entries to dataset
        dataSet.setDrawValues(false);
        //chart line thickness
        dataSet.setLineWidth(2.8f);
        dataSet.setDrawCircles(false);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setHighlightEnabled(false);
        dataSet.setDrawFilled(true);
        //make line smooth
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //fill under the line with gradient look
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.chart_gradient_blue);
        dataSet.setFillDrawable(drawable);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
    }

    public void makeGetCall() {
        Request request = new Request.Builder()
                .url("https://api.coincap.io/v2/assets/ripple/history?interval=d1")
                .header("X-CMC_PRO_API_KEY", "5d10358e-e718-4f8b-a973-dab7d737e035")
                .build();
        new OkhttpGetCall(request).sendGetRequest(new OkhttpGetCall.responseImp() {
            @Override
            public void onSuccessFulCall(String response) throws JSONException {
                JSONObject wholePriceResponse = new JSONObject(response);
                JSONArray jsonPricesArray = wholePriceResponse.getJSONArray("data");
                int size = jsonPricesArray.length() - 7;
                for (int i = size; i < jsonPricesArray.length(); i++) {
                    JSONObject jsonPriceForSingleDay = jsonPricesArray.getJSONObject(i);
                    Log.d("jsonLastSeven", "onSuccessFulCall: " + jsonPriceForSingleDay.toString());
                    chartEntry.add(new Entry(i, Float.valueOf(jsonPriceForSingleDay.getString("priceUsd"))));
                    days.add(jsonPriceForSingleDay.getString("date"));
                    //
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDataValuesForChart();
                        lineChart.notifyDataSetChanged();
                        lineChart.invalidate(); // refresh
                    }
                });
            }

            @Override
            public void onFailedCall(IOException e) {

            }
        });
    }

    private void customizingXAxis() {
        XAxis xAxis = lineChart.getXAxis();
        //position of numbers
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        //size of x axis numbers
        xAxis.setTextSize(10f);
        //color of numbers
        xAxis.setTextColor(getResources().getColor(R.color.colorSolidGray));
        //horizontal line along side of number of axis
        xAxis.setDrawAxisLine(false);
        //vertical line for each number value in x row
        xAxis.setDrawGridLines(true);
        //color of vertical line on each number value in x value
        xAxis.setGridColor(getResources().getColor(R.color.colorGridColorDark));
        //x grid for each x number thickness
        xAxis.setGridLineWidth(2f);
        //make chart full width

    }

    private void customizingYAxis() {
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setTextSize(12f);
        yAxisLeft.setGridLineWidth(2f);
        yAxisLeft.setGridColor(getResources().getColor(R.color.colorGridColorDark));
        yAxisLeft.setDrawAxisLine(false);
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setGridColor(getResources().getColor(R.color.colorGridColorDark));
        yAxisRight.setDrawAxisLine(true);
    }
}
