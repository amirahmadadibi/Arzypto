package projects.com.amirahmadadibi.arzypto;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MainActivity extends AppCompatActivity {
    OkHttpClient okHttpClient;
    public static final String BASE_URL = "wss://ws.coincap.io/prices?assets=bitcoin,ethereum,monero,litecoin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(BASE_URL).build();

        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Log.d("test", "onOpen: isOpeen");
            }

            @Override
            public void onMessage(WebSocket webSocket, final String text) {
                super.onMessage(webSocket, text);
                Log.d("test2", "onMessage: " + text);
                webSocket.cancel();
                JSONObject j1 = null;
                try {
                    j1 = new JSONObject(text);
                    Iterator<String> iterable = j1.keys();
                    while (iterable.hasNext()) {
                        String key = iterable.next();
                        String value = j1.getString(key);
                        Log.d("TAG", "onMessage: " + key + " " + value);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        okHttpClient.newWebSocket(request, webSocketListener);
        okHttpClient.dispatcher().executorService().shutdown();
    }
}