package projects.com.amirahmadadibi.arzypto.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.imanx.State;
import com.github.imanx.StateLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import projects.com.amirahmadadibi.arzypto.Presenter.CoinChartPresenter;
import projects.com.amirahmadadibi.arzypto.R;

public class CoinChartActivity extends AppCompatActivity {
    LineChart lineChart;
    List<Entry> chartEntry = new ArrayList<>();
    Typeface typeFace;
    CoinChartPresenter coinChartPresenter;
    StateLayout stateLayout;
    String coinID = "";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_chart);
        Intent intent = getIntent();
        coinID = intent.getStringExtra("coinID");
        coinChartPresenter = new CoinChartPresenter(this, CoinChartActivity.this);
        initComponents();
        chartInit();
        showDayValuesOfCoinAsStartingPoint();
        TextView txt_one_month = findViewById(R.id.txt_one_month);
        txt_one_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinChartPresenter.getChartInfoWithInterval(coinID, CoinChartPresenter.INFO_INTERVAL_Day, 30);
            }
        });

        TextView txt_one_day = findViewById(R.id.txt_one_day);
        txt_one_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinChartPresenter.getChartInfoWithInterval(coinID, CoinChartPresenter.INFO_INTERVAL_HOUR, 24);
            }
        });

        TextView txt_one_week = findViewById(R.id.txt_one_week);
        txt_one_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinChartPresenter.getChartInfoWithInterval(coinID, CoinChartPresenter.INFO_INTERVAL_Day, 7);
            }
        });

    }

    private void initComponents() {
        stateLayout = findViewById(R.id.state_layout_crypto_chart);
        lineChart = findViewById(R.id.chart);
        typeFace = Typeface.createFromAsset(this.getAssets(), "fonts/IRANYekanMobileMedium.ttf");

    }

    private void showDayValuesOfCoinAsStartingPoint() {
        //select 1 day interval as default showing sduation
        coinChartPresenter.getChartInfoWithInterval(coinID, coinChartPresenter.INFO_INTERVAL_HOUR, 24);
        setDataValuesForChart();
    }

    private void chartInit() {
        lineChart.setNoDataText("در حال بارگزاری اطلاعات...");
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);   // Hide the legend
        lineChart.setDrawGridBackground(false);//draw recangle with solid background color
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.setDrawBorders(false);
        //make chart full width
        lineChart.setViewPortOffsets(0f, 20f, 0f, 20f);
        customizingXAxis();
        customizingYAxis();
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

    private void customizingXAxis() {
        XAxis xAxis = lineChart.getXAxis();
        //position of numbers
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.WHITE);
        //size of x axis numbers
        xAxis.setTextSize(10f);
        //horizontal line along side of number of axis
        xAxis.setDrawAxisLine(false);
        //vertical line for each number value in x row
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelRotationAngle(90f); // rotates label so we can see it all TODO remove after tests

    }

    private void customizingYAxis() {
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setTextSize(12f);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawGridLines(false);
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setGridColor(getResources().getColor(R.color.colorGridColorDark));
        yAxisRight.setDrawAxisLine(true);
    }


    public void reFreshChart() {
        setDataValuesForChart();
        lineChart.notifyDataSetChanged();
    }

    public void errorInFetchingData() {
        //Toast.makeText(this, "عدم توانایی در برقراری ارتباط ...", Toast.LENGTH_SHORT).show();
        Log.d("fail", "errorInFetchingData: ");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stateLayout.setState(State.Failure);
            }
        });
    }

    public void onGettingNewData(List<Entry> chartEntry) {
        this.chartEntry = chartEntry;
        reFreshChart();
    }

    public void watingForDownloading(final boolean isWating) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isWating) {
                    stateLayout.setState(State.Loading);
                } else {
                    lineChart.invalidate();
                    stateLayout.setState(State.Normal);
                }
            }
        });
    }
}
