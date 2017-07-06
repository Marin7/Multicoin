package com.webserver.transactions;

import javafx.util.Pair;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by Marin on 5/31/2017.
 */
@Component
public class ExchangeManager {

    private static final String key = "DAA9AD52FD11E65D8C1131FF323363CE";
    private static final String secret = "5FB0EDB6FAADF61C058912D07C05FA05";

    private final HashMap<String, Double> coins = new HashMap<String, Double>() {
        {
            put("btc", 0.001);//Bitcoin
            put("eth", 0.005);//Ethereum
            put("xvg", 250.0);//Verge
            put("ltc", 0.02);//Litecoin
            put("dash", 0.005);//Dash
            put("doge", 400.0);//Dogecoin
            put("omni", 0.015);//OMNI
        }
    };

    public void buyCoins(double amount) {
        for (String coin : coins.keySet()) {
            try {
                tradeCoins(String.valueOf(BigDecimal.valueOf((amount * coins.get(coin)))), String.valueOf(getCoinBuyPrice(coin)), coin, "usd");
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sellCoins(double amount) {
        for (String coin : coins.keySet()) {
            try {
                tradeCoins(String.valueOf(BigDecimal.valueOf(amount * coins.get(coin))), String.valueOf(getCoinSellPrice(coin)), "usd", coin);
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void tradeCoins(String amount, String rate, String coin1, String coin2) throws InvalidKeyException, NoSuchAlgorithmException, IOException, JSONException {
        String nonce = String.valueOf(System.currentTimeMillis() % 2147483646);
        String queryArgs = "https://c-cex.com/t/api.html?a=buylimit&apikey=" + key + "&market=" + coin1 + "-" + coin2 + "&quantity="
                + amount + "&rate=" + rate + "&nonce=" + nonce;
        String sign = hmac512Digest(queryArgs);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(queryArgs);
        get.addHeader("apisign", sign);

        CloseableHttpResponse response = httpClient.execute(get);
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

        HttpGet get = new HttpGet(queryArgs);

        CloseableHttpResponse response = httpClient.execute(get);

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

    public Pair<Double, Double> getPrice() throws IOException, JSONException {
        double buyPrice = 0d, sellPrice = 0d;
        for (String coin : coins.keySet()) {
            JSONObject jsonObject = getCoinTicker(coin).getJSONObject("ticker");
            buyPrice += jsonObject.getDouble("high") * coins.get(coin);
            sellPrice += jsonObject.getDouble("low") * coins.get(coin);
        }
        return new Pair<>(buyPrice, sellPrice);
    }

}
