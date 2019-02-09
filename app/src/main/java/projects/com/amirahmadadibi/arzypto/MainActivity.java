package projects.com.amirahmadadibi.arzypto;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MainActivity extends AppCompatActivity {
    OkHttpClient okHttpClient;
    public static final String BASE_URL = "wss://ws.coincap.io/prices?assets=bitcoin,ethereum,monero,litecoin";
    Queue<String> stringQueue = new LinkedList<>();

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
                if (!text.isEmpty()) {
                    stringQueue.add(text);
                    getLatesPriceFromQueue();
                }
            }
        };
        okHttpClient.newWebSocket(request, webSocketListener);
        okHttpClient.dispatcher().executorService().shutdown();
    }

    public void getLatesPriceFromQueue() {
        Log.d("test", "getLastPriceFromQueue: ---------------------------------- *** QueueSize " + stringQueue.size());
        try {
            //remove's and return's head element
            JSONObject jsonObject = new JSONObject(stringQueue.poll());
            if (jsonObject != null) {
                Iterator<String> stringIterator = jsonObject.keys();
                while (stringIterator.hasNext()) {
                    String name = stringIterator.next();
                    String date = jsonObject.getString(name);
                    Log.d("test", "getLastPriceFromQueue: *** Data " + name  + " " + date);
                }
            }
        } catch (JSONException e) {
            Log.d("test", "Error JSONException *** --> " + e);
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            Log.d("test", "Error NoSuchElementException *** --> " + e);
            e.printStackTrace();
        }
    }
}