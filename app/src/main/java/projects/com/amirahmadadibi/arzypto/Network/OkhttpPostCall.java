package projects.com.amirahmadadibi.arzypto.Network;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpPostCall {
    String mUrl;
    private Call call;

    public OkhttpPostCall(String url) {
        this.mUrl = url;
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("code", "usd")
                .build();

        Request request = new Request.Builder()
                .url(mUrl)
                .header("Content-Type","application/json")
                .header("Connection","keep-alive")
                .post(formBody)
                .build();
        call = client.newCall(request);
    }

    public void sendGerRequest(final responseImp responseImp) {
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
