package com.webserver.services;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marin on 5/31/2017.
 */
public class ExchangeManager {

    private final String key;
    private final String secret;
    private final List<String> coins = Arrays.asList("btc", "ltc");
    private long nonce;

    public ExchangeManager(String key, String secret) {
        this.key = key;
        this.secret = secret;
        nonce = 1;
    }

    public void buyCoins(double amount) {
        for (String coin : coins) {
            try {
                buyCoin(String.valueOf(BigDecimal.valueOf(amount)), coin);
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sellCoins(double amount) {
        for (String coin : coins) {
            try {
                String address = getDepositAddress(coin);
                sellCCoin(String.valueOf(BigDecimal.valueOf(amount)), coin, address);
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void buyCoin(String amount, String coin) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        String nonce = String.valueOf(System.currentTimeMillis() % 2147483646);
        String queryArgs = "https://c-cex.com/t/api.html?a=buylimit&apikey=" + key + "&market=" + coin + "-usd" + "&quantity="
                + amount + "&rate=1000000" + "&nonce=" + nonce;
        String sign = hmac512Digest(queryArgs);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(queryArgs);
        get.addHeader("apisign", sign);

        CloseableHttpResponse response = httpClient.execute(get);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    public void sellCCoin(String amount, String coin, String account) throws IOException {
        String url = "https://c-cex.com/t/api.html?a=selllimit&apikey=" + key + "&market=" + "btc" + "-usd" + "&quantity="
                + amount + "&rate=0.00001";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        CloseableHttpResponse response = httpClient.execute(post);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    private String getDepositAddress(String coin) throws InvalidKeyException, NoSuchAlgorithmException, IOException, JSONException {
        String url = "https://yobit.net/tapi/";
        String queryArgs = "method=GetDepositAddress&coinName=" + coin + "&nonce=" + nonce++;
        String sign = hmac512Digest(queryArgs);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.addHeader("Key", key);
        post.addHeader("Sign", sign);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("method", "GetDepositAddress"));
        params.add(new BasicNameValuePair("coinName", coin));
        params.add(new BasicNameValuePair("nonce", Long.toString(nonce)));
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity responseEntity = response.getEntity();

        String responseString = EntityUtils.toString(responseEntity);
        JSONObject jsonObject = new JSONObject(responseString);
        String address = ((JSONObject) jsonObject.get("return")).getString("address");
        System.out.println("Address for " + coin + ": " + address);
        return address;
    }

    private String hmac512Digest(String queryArgs) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac shaMac = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
        shaMac.init(keySpec);
        final byte[] macData = shaMac.doFinal(queryArgs.getBytes());
        return Hex.encodeHexString(macData);
    }
}
