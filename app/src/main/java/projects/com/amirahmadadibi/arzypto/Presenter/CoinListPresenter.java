package projects.com.amirahmadadibi.arzypto.Presenter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import projects.com.amirahmadadibi.arzypto.Model.Coin;
import projects.com.amirahmadadibi.arzypto.Network.OkHttpSocketClient;
import projects.com.amirahmadadibi.arzypto.View.CoinListActivity;

public class CoinListPresenter {
    public static final String TAG = "coinlistpresenter";
    CoinListActivity coinListActivity;
    OkHttpSocketClient okHttpSocketClient;
    public Queue<String> stringQueue = new LinkedList<>();
    public List<Coin> coinList = new LinkedList<>();

    public CoinListPresenter(CoinListActivity coinListActivity) {
        this.coinListActivity = coinListActivity;
        this.okHttpSocketClient = new OkHttpSocketClient();
        initializeCoins();
        runWebSocket();
    }

    public void runWebSocket() {
        okHttpSocketClient.runSocketConncetion(new OkHttpSocketClient.ImpSocketStatus() {
            @Override
            public void onClose(String errorMessageAndErrorCode) {
                Log.d(TAG, "onClose: " + errorMessageAndErrorCode);
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: webSocket Connected");
            }

            @Override
            public void onMessage(String textResponse) {
                Log.d(TAG, "onMessage: " + textResponse);
                stringQueue.add(textResponse);
                getLatesPriceFromQueue();
            }
        });
    }


    private void initializeCoins() {
        String[] coins = {
                "bitcoin",
                "ripple",
                "ethereum",
                "tether",
                "bitcoin-cash",
                "eos",
                "stellar",
                "litecoin",
                "BSV",
                "tron",
                "monero",
                "cardano",
                "dogecoin"
        };
        for (int i = 0; i < coins.length; i++) {
            Coin coin = new Coin(coins[i], 0.0d,false);
            coinList.add(coin);
        }
        coinListActivity.setupCoinListAdatper(coinList);
    }


    public void getLatesPriceFromQueue() {
        Log.d("test", "getLastPriceFromQueue: ---------------------------------- *** QueueSize " + stringQueue.size());
        try {
            //remove's and return's head element
            JSONObject jsonObject = new JSONObject(stringQueue.poll());
            if (jsonObject != null) {
                Iterator<String> stringIterator = jsonObject.keys();
                while (stringIterator.hasNext()) {
                    String coinName = stringIterator.next();
                    String coinLastprice = jsonObject.getString(coinName);
                    Iterator<Coin> coinIterator = coinList.iterator();
                    while (coinIterator.hasNext()) {
                        Coin c = coinIterator.next();
                        if (c.getName().equals(coinName)) {
                            if(c.getPrice() > Double.valueOf(coinLastprice)){
                                c.setPriceRaiseFlat(false);
                            }else{
                                c.setPriceRaiseFlat(true);
                            }
                            c.setPrice(Double.valueOf(coinLastprice));
                            Log.d("test", "getLastPriceFromQueue: *** Data " + c.getName() + " " + c.getPrice());
                        }
                    }
                }
                notifyAdapter();
            }
        } catch (JSONException e) {
            Log.d("test", "Error JSONException *** --> " + e);
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            Log.d("test", "Error NoSuchElementException *** --> " + e);
            e.printStackTrace();
        }
    }

    private void notifyAdapter() {
        coinListActivity.notifyOnMessageReceviedData();
    }

}
