package projects.com.amirahmadadibi.arzypto.Configuration;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import projects.com.amirahmadadibi.arzypto.R;

public class Configuration extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/IRANYekanMobileMedium.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        //....
    }
}
