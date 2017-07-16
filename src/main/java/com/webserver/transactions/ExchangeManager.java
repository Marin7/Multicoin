package com.webserver.transactions;

import javafx.util.Pair;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Marin on 5/31/2017.
 */
@Component
public class ExchangeManager {

    private static final String key = "DAA9AD52FD11E65D8C1131FF323363CE";
    private static final String secret = "5FB0EDB6FAADF61C058912D07C05FA05";
    private static AtomicLong nonce = new AtomicLong(System.currentTimeMillis());

    private final HashMap<String, Double> coins = new HashMap<String, Double>() {
        {
            put("btc", 0.001);//Bitcoin
            put("eth", 0.005);//Ethereum
            put("xvg", 250.0);//Verge
            put("ltc", 0.02);//Litecoin
            put("dash", 0.005);//Dash
            put("doge", 400.0);//Dogecoin
            put("soar", 20.0);//Soar
        }
    };

    public void buyCoins(double amount) {
        for (String coin : coins.keySet()) {
            try {
                tradeCoins(String.valueOf(BigDecimal.valueOf((amount * coins.get(coin)))), String.valueOf(getCoinBuyPrice(coin)), coin, "usd");
                System.out.println("Bought " + amount + " " + coin);
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sellCoins(double amount) {
        for (String coin : coins.keySet()) {
            try {
                tradeCoins(String.valueOf(BigDecimal.valueOf(amount * coins.get(coin))), String.valueOf(getCoinSellPrice(coin)), "usd", coin);
                System.out.println("Sold " + amount + " " + coin);
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void tradeCoins(String amount, String rate, String coin1, String coin2) throws InvalidKeyException, NoSuchAlgorithmException, IOException, JSONException {
        String nonceString = String.valueOf(nonce.getAndIncrement() % 2147483646);
        String queryArgs = "https://c-cex.com/t/api.html?a=buylimit&apikey=" + key + "&market=" + coin1 + "-" + coin2 + "&quantity="
                + amount + "&rate=" + rate + "&nonce=" + nonceString;
        String sign = hmac512Digest(queryArgs);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(queryArgs);
        post.addHeader("apisign", sign);

        CloseableHttpResponse response = httpClient.execute(post);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String result = "";
        String s;
        while ((s = stdInput.readLine()) != null) {
            result = result.concat(s + "\n");
        }
        System.out.println(result);
    }

    private static String hmac512Digest(String queryArgs) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac shaMac = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
        shaMac.init(keySpec);
        final byte[] macData = shaMac.doFinal(queryArgs.getBytes());
        return Hex.encodeHexString(macData);
    }

    private JSONObject getCoinTicker(String coin) throws JSONException, IOException {
        String queryArgs = "https://c-cex.com/t/" + coin + "-usd" + ".json";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost(queryArgs);

        CloseableHttpResponse response = httpClient.execute(post);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String result = stdInput.readLine();
        System.out.println(coin + " TICKER: " + result);

        return new JSONObject(result);
    }

    private double getCoinBuyPrice(String coin) throws IOException, JSONException {
        return getCoinTicker(coin).getJSONObject("ticker").getDouble("high");
    }

    private double getCoinSellPrice(String coin) throws IOException, JSONException {
        return getCoinTicker(coin).getJSONObject("ticker").getDouble("low");
    }

    public Pair<BigDecimal, BigDecimal> getPrice() throws IOException, JSONException {
        BigDecimal buyPrice = new BigDecimal(0);
        BigDecimal sellPrice = new BigDecimal(0);
        for (String coin : coins.keySet()) {
            JSONObject jsonObject = getCoinTicker(coin).getJSONObject("ticker");
            buyPrice = buyPrice.add(new BigDecimal(jsonObject.getDouble("high") * coins.get(coin)).setScale(2, RoundingMode.HALF_UP));
            sellPrice = sellPrice.add(new BigDecimal(jsonObject.getDouble("low") * coins.get(coin)).setScale(2, RoundingMode.HALF_UP));
        }
        return new Pair<>(buyPrice, sellPrice);
    }

}
