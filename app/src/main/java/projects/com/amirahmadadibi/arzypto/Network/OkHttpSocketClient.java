package projects.com.amirahmadadibi.arzypto.Network;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class OkHttpSocketClient {
    public static final String SOCKET_URL = "wss://ws.coincap.io/prices?assets=bitcoin,ripple,ethereum,tether,bitcoin-cash,eos,stellar,litecoin,tron,monero,cardano,dogecoin";
    OkHttpClient okHttpClient;
    Request request;

    public OkHttpSocketClient() {
        okHttpClient = new OkHttpClient();
        request = new Request.Builder().url(SOCKET_URL).build();
    }

    public void runSocketConncetion(final ImpSocketStatus ImpSocketStatus) {
        if (request == null) return;
        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                ImpSocketStatus.onSuccess();
            }

            @Override
            public void onMessage(WebSocket webSocket, final String text) {
                super.onMessage(webSocket, text);
                ImpSocketStatus.onMessage(text);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                String fallBackStringCodeAndError = reason + " code" + code;
                ImpSocketStatus.onClose(fallBackStringCodeAndError);
            }
        };
        okHttpClient.newWebSocket(request, webSocketListener);
        okHttpClient.dispatcher().executorService().shutdown();


    }

    public interface ImpSocketStatus {
        void onClose(String errorMessageAndErrorCode);

        void onSuccess();

        void onMessage(String textResponse);
    }


}
