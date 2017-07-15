package com.webserver.transactions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Marin on 6/26/2017.
 */
@Component
public class MultichainManager {

    private static final String CHAIN_NAME = "chain3";
    private static final String PATH = "src/main/resources/multichain-cli.exe";
    private static final String MULTICHAIN_ADDRESS = "18U8sWWCaxYV7zPuX3u8KJhwspctjFhALq6BF6";

    public String createNewAddress() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String multichainCommand = "getnewaddress";
        String command = PATH + " " + CHAIN_NAME + " " + multichainCommand.toLowerCase();

        Process process = runtime.exec(command);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = stdInput.readLine();

        process.destroy();
        return result;
    }

    public double getUserBalance(String multichainAddress) throws IOException, JSONException {
        Runtime runtime = Runtime.getRuntime();
        String multichainCommand = "getaddressbalances";
        String command = PATH + " " + CHAIN_NAME + " " + multichainCommand + " " + multichainAddress;

        Process process = runtime.exec(command);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = "";
        String s;
        while ((s = stdInput.readLine()) != null) {
            result = result.concat(s + "\n");
        }

        JSONArray jsonArray = new JSONArray(result);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String asset = jsonObject.getString("assetref");
        Double qty = jsonObject.getDouble("qty");
        Double raw = jsonObject.getDouble("raw");

        System.out.println("asset: " + asset + " qty=" + qty + " raw=" + raw);

        process.destroy();
        return qty;
    }

    public void sendMulticoinsToAddress(String multichainAddress, double amount) throws IOException, JSONException {
        Runtime runtime = Runtime.getRuntime();
        String multichainCommand = "sendfrom";
        String command = PATH + " " + CHAIN_NAME + " " + multichainCommand + " " + MULTICHAIN_ADDRESS + " " +
                multichainAddress + " " + amount;

        Process process = runtime.exec(command);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = "";
        String s;
        while ((s = stdInput.readLine()) != null) {
            result = result.concat(s + "\n");
        }

        System.out.println(result);

        process.destroy();
    }

    public void sendMulticoinsFromAddress(String multichainAddress, double amount) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String multichainCommand = "sendfrom ";
        String command = PATH + " " + CHAIN_NAME + " " + multichainCommand + " " + multichainAddress + " " + MULTICHAIN_ADDRESS + " " + amount;

        Process process = runtime.exec(command);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = "";
        String s;
        while ((s = stdInput.readLine()) != null) {
            result = result.concat(s + "\n");
        }

        System.out.println(result);

        process.destroy();
    }
}
