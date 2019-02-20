package projects.com.amirahmadadibi.arzypto.View;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        setupChrt();
    }

    private void setupChrt() {
        lineChart = findViewById(R.id.chart);
        //lineChart.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // use your bg color
        lineChart.setDrawGridBackground(true);
        lineChart.setDrawBorders(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setHighlightPerTapEnabled(false);
        lineChart.setDrawBorders(false);
        //make chart full width
        lineChart.setViewPortOffsets(0f, 20f, 0f, 20f);
        //zooming
        lineChart.setScaleEnabled(false);
        //background
        lineChart.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        //
        lineChart.getAxisLeft().setDrawLabels(true);
        lineChart.getAxisRight().setDrawLabels(true);
        List<Entry> entries = new ArrayList<>();

        Request request = new Request.Builder()
                .url("https://api.coincap.io/v2/assets/ripple/history?interval=d1")
                .header("X-CMC_PRO_API_KEY", "5d10358e-e718-4f8b-a973-dab7d737e035")
                .build();
        new OkhttpGetCall(request).sendGetRequest(new OkhttpGetCall.responseImp() {
            @Override
            public void onSuccessFulCall(String response) throws JSONException {
                JSONObject jsonObject = new JSONObject(response);
//                JSONArray jsonArray = new JSONArray(response);
//                for (int i = jsonArray.length() - 1; i >= 0; i--) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Log.d("coincap", "onSuccessFulCall: " + jsonObject.toString() + "\n");
//                }
                //this --> 1550676724855 to *** this --> 20/02/2019
                //this --> 1550608200000
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            }

            @Override
            public void onFailedCall(IOException e) {

            }
        });

        entries.add(new Entry(10, 9));
        entries.add(new Entry(11, 15));
        entries.add(new Entry(12, 40));
        entries.add(new Entry(13, 80));
        entries.add(new Entry(14, 100));
        entries.add(new Entry(15, 110));
        //data to dataset
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        //show number value on chart changes
        lineDataSet.setDrawValues(false);
        //chart line thickness
        lineDataSet.setLineWidth(2.7f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setHighlightEnabled(false);
        //colorize chart
        lineDataSet.setDrawFilled(true);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.chart_gradient_blue);
        lineDataSet.setFillDrawable(drawable);
        //changing color of axis
        //*** everything related to x axis
        customizingXAxis();
        customizingYAxis();
        setupLegends();
        //final
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void setupLegends() {
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
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
        xAxis.setDrawGridLines(false);
        //color of vertical line on each number value in x value
        xAxis.setGridColor(getResources().getColor(R.color.colorGridColorDark));
        //x grid for each x number thickness
        xAxis.setGridLineWidth(0f);
        //make chart full width
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLimitLinesBehindData(false);
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
        yAxisRight.setDrawAxisLine(false);
    }
}
