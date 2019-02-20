package projects.com.amirahmadadibi.arzypto.Network;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpGetCall {
    private Call call;

    public OkhttpGetCall(Request request) {
        OkHttpClient client = new OkHttpClient();


        call = client.newCall(request);
    }

    public void sendGetRequest(final responseImp responseImp) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseImp.onFailedCall(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    responseImp.onSuccessFulCall(response.body().string());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("test", "onResponse: " + response);
            }
        });
    }


    public interface responseImp {
        void onSuccessFulCall(String response) throws JSONException;
        void onFailedCall(IOException e);
    }
}
