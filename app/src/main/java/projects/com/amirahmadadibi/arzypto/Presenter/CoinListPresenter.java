package projects.com.amirahmadadibi.arzypto.Presenter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import projects.com.amirahmadadibi.arzypto.Model.Coin;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpPostCall;
import projects.com.amirahmadadibi.arzypto.Network.OkhttpRequestBuilder;
import projects.com.amirahmadadibi.arzypto.Network.OkHttpSocketClient;
import projects.com.amirahmadadibi.arzypto.R;
import projects.com.amirahmadadibi.arzypto.View.CoinListActivity;

public class CoinListPresenter {
    public static final String TAG = "coinlistpresenter";
    CoinListActivity coinListActivity;
    OkHttpSocketClient okHttpSocketClient;
    OkhttpPostCall okhttpPostCall;
    public Queue<String> stringQueue = new LinkedList<>();
    public List<Coin> coinList = new LinkedList<>();
    Double dollerPrice;
    int queueSize = 20;

    public CoinListPresenter(CoinListActivity coinListActivity) {
        this.coinListActivity = coinListActivity;
        this.okHttpSocketClient = new OkHttpSocketClient();
        initializeDollerPrice();
        initializeCoins();
    }

    private void initializeDollerPrice() {
        new OkhttpRequestBuilder()
                .setmUrl("https://currency.arzypto.com/price")
                .createGetRequest()
                .sendGerRequest(new OkhttpPostCall.responseImp() {
                    @Override
                    public void onSuccessFulCall(String response) throws JSONException {
                        JSONObject result = new JSONObject(response);
                        dollerPrice = Double.valueOf(result.getString("sell"));
                    }

                    @Override
                    public void onFailedCall(IOException e) {
                        Log.d("arzypto", "onFailedCall: " + e);
                    }
                });
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
                stringQueue.add(textResponse);
                Log.d(TAG, "onMessage: " + stringQueue.size());
                if (stringQueue.size() >= queueSize) {
                    getLatesPriceFromQueue();
                }
            }
        });


    }


    public void getLatesPriceFromQueue() {
        try {
            //remove's and return's head element
            JSONObject jsonResponseChangedPrices = new JSONObject(stringQueue.poll());
            Iterator<String> nemeListsIterator = jsonResponseChangedPrices.keys();
            while (nemeListsIterator.hasNext()) {
                String coinName = nemeListsIterator.next();
                String coinLastprice = jsonResponseChangedPrices.getString(coinName);
                Iterator<Coin> coinIterator = coinList.iterator();
                while (coinIterator.hasNext()) {
                    Coin c = coinIterator.next();
                    if (c.getName().equals(coinName)) {
                        if (c.getPrice() > Double.valueOf(coinLastprice)) {
                            c.setPriceRaiseFlat(false);
                        } else {
                            c.setPriceRaiseFlat(true);
                        }
                        c.setPrice(Double.valueOf(coinLastprice));
                        c.setPriceInToman(Double.valueOf(coinLastprice) * dollerPrice);
                    }
                }
            }
            notifyAdapter();
        } catch (JSONException e) {
            Log.d("test", "Error JSONException *** --> " + e);
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            Log.d("test", "Error NoSuchElementException *** --> " + e);
            e.printStackTrace();
        }
        //reset size for create some kind of manual offset and set queue of responses for next round
        stringQueue.clear();
    }

    private void notifyAdapter() {
        coinListActivity.notifyOnMessageReceviedData();
    }

    private void initializeCoins() {
        String[] coinName = {
                "bitcoin",
                "ripple",
                "ethereum",
                "tether",
                "bitcoin-cash",
                "eos",
                "stellar",
                "litecoin",
                "bitcoin-sv",
                "tron",
                "monero",
                "cardano",
                "dogecoin"
        };
        String[] coinFarsiNames = {
                "بیت کوین",
                "ریپل",
                "اتریوم",
                "تتر",
                "بیت کوین کش",
                "ای او اس",
                "استلار",
                "لایت کوین",
                "بیت کوین SV",
                "ترون",
                "مونرو",
                "کاردانو",
                "دوج"
        };
        String[] coinSymbol = {
                "BTC",
                "XRP",
                "ETH",
                "USDT",
                "BCH",
                "EOS",
                "XLM",
                "LTC",
                "BSV",
                "TRX",
                "XMR",
                "ADA",
                "DOGE"
        };
        int[] coinThumbnailResources = {
                R.drawable.btc,
                R.drawable.xrp,
                R.drawable.eth,
                R.drawable.usdt,
                R.drawable.bch,
                R.drawable.eos,
                R.drawable.xlm,
                R.drawable.ltc,
                R.drawable.bsv,
                R.drawable.trx,
                R.drawable.xmr,
                R.drawable.ada,
                R.drawable.doge
        };
        for (int i = 0; i < coinName.length; i++) {
            Coin coin = new Coin(coinName[i], 0.0d, false, coinSymbol[i], coinFarsiNames[i], coinThumbnailResources[i], 0.0);
            coinList.add(coin);
        }
        coinListActivity.setupCoinListAdatper(coinList);
    }

}
