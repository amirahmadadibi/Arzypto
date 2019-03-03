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
import projects.com.amirahmadadibi.arzypto.Network.OkhttpPostCall;
import projects.com.amirahmadadibi.arzypto.Network.OkHttpSocketClient;
import projects.com.amirahmadadibi.arzypto.R;
import projects.com.amirahmadadibi.arzypto.View.CoinListActivity;

public class CoinListPresenter {
    public static final String TAG = "coinlistpresenter";
    CoinListActivity coinListActivity;
    OkHttpSocketClient okHttpSocketClient;
    OkhttpPostCall okhttpPostCall;
    public Queue<String> responseQueue = new LinkedList<>();
    public List<Coin> coinList = new LinkedList<>();
    double dollerPrice;
    //fill queue to this size and use data and then clearing it again - like manual buffer
    int queueSize = 20;
    //response to these many of onMessage quickly
    int loadTurboBost = 3;

    public CoinListPresenter(CoinListActivity coinListActivity, double dollerPrice) {
        this.coinListActivity = coinListActivity;
        this.okHttpSocketClient = new OkHttpSocketClient();
        this.dollerPrice = dollerPrice;
        initializeCoins();
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
                responseQueue.add(textResponse);
                //first update right out of bad for fast loading data
                //and then when we hit zero boost, we going to load data lazy way using queue size to max and
                //clearing it agin
                if (loadTurboBost > 0) {
                    getLatesPriceFromQueue(false);
                    loadTurboBost--;
                } else {
                    if (responseQueue.size() >= queueSize) {
                        getLatesPriceFromQueue(true);
                        //reset size for create some kind of manual offset and set queue of responses for next round
                    }
                }

            }
        });


    }


    public void getLatesPriceFromQueue(boolean restResponseQueue) {
        try {
            //remove's and return's head element
            JSONObject jsonResponseChangedPrices = new JSONObject(responseQueue.poll());
            Iterator<String> nemeListsIterator = jsonResponseChangedPrices.keys();
            while (nemeListsIterator.hasNext()) {
                String coinName = nemeListsIterator.next();
                String coinLastprice = jsonResponseChangedPrices.getString(coinName);
                Iterator<Coin> coinIterator = coinList.iterator();
                while (coinIterator.hasNext()) {
                    Coin c = coinIterator.next();
                    if (c.getIdName().equals(coinName)) {
                        if (c.getPrice() > Double.valueOf(coinLastprice)) {
                            c.setPriceRaiseFlag(false);
                        } else {
                            c.setPriceRaiseFlag(true);
                        }
                        c.setPrice(Double.valueOf(coinLastprice));
                        c.setPriceInToman(Double.valueOf(coinLastprice) * dollerPrice);
                    }
                }
            }
            notifyAdapter();
            if (restResponseQueue) {
                responseQueue.clear();
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
        //initialize tether coin because it's very stable and we need show 1 dollar price before any changes
        coinList.get(3).setPrice(1.0);
        coinList.get(3).setPriceInToman(1.0 * dollerPrice);
        coinListActivity.setupCoinListAdatper(coinList);
    }

}
