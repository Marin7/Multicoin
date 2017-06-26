package com.webserver.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.webserver.domain.CreditCardDTO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marin on 6/24/2017.
 */
@Component
public class TransferManager {

    private final CreditCardDTO multicoinCreditCard = new CreditCardDTO();

    public void transferMoneyFromCreditCard(CreditCardDTO creditCard, double amount) {
        try {
            HttpResponse response = transferMoney(creditCard, multicoinCreditCard, amount);
//        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (SignatureException | IOException | CertificateException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void transferMoneyToCreditCard(CreditCardDTO creditCard, double amount) {
        try {
            HttpResponse response = transferMoney(multicoinCreditCard, creditCard, amount);
//            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (SignatureException | IOException | CertificateException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public HttpResponse transferMoney(CreditCardDTO sourceCreditCard, CreditCardDTO destCreditCardDTO, double amount)
            throws SignatureException, IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String baseUri = "visadirect/";
        String resourcePath = "fundstransfer/v1/pushfundstransactions/";
        String url = "https://sandbox.api.visa.com/" + baseUri + resourcePath;
        String body = getBody(sourceCreditCard, destCreditCardDTO, amount);

        HttpPost request = new HttpPost(url);
        request.setHeader("content-type", "application/json");
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader());
        request.setHeader("ex-correlation-id", getCorrelationId());
        request.setEntity(new StringEntity(body, "UTF-8"));
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(loadClientCertificate(),
                new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
        HttpResponse response = client.execute(request);
        logResponse(response);
        return response;
//        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    private SSLContext loadClientCertificate() throws KeyManagementException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(
                new File("C:\\Users\\Marin\\Facultate\\Multicoin\\src\\main\\resources\\multicoin.p12"),
                "arsenal".toCharArray(),
                "arsenal".toCharArray())
                .build();
        return sslcontext;
    }

    private static String getBasicAuthHeader() {
        String userId = "XMWKRSQU0IW1ZRL5HTTD211jBy73BGDmjGu9NhymEeb9Zo6B4";
        String password = "tbA6DS97B3wrvEod8eQb8uP0eJ4";
        return "Basic " + base64Encode(userId + ":" + password);
    }

    private static String base64Encode(String token) {
        byte[] encodedBytes = Base64.encodeBase64(token.getBytes());
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }

    private String getBody(CreditCardDTO senderCreditCard, CreditCardDTO receiverCreditCard, double amount) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return "{"
                + "\"systemsTraceAuditNumber\":350420,"
                + "\"retrievalReferenceNumber\":\"401010350420\","
                + "\"localTransactionDateTime\":\"" + strDate + "\","
                + "\"acquiringBin\":409999,\"acquirerCountryCode\":\"101\","
                + "\"senderAccountNumber\":\"1234567890123456\","
                + "\"senderCountryCode\":\"USA\","
                + "\"transactionCurrencyCode\":\"USD\","
                + "\"senderName\":\"John Smith\","
                + "\"senderAddress\":\"44 Market St.\","
                + "\"senderCity\":\"San Francisco\","
                + "\"senderStateCode\":\"CA\","
                + "\"recipientName\":\"Adam Smith\","
                + "\"recipientPrimaryAccountNumber\":\"4957030420210454\","
                + "\"amount\":\"112.00\","
                + "\"businessApplicationId\":\"AA\","
                + "\"transactionIdentifier\":234234322342343,"
                + "\"merchantCategoryCode\":6012,"
                + "\"sourceOfFundsCode\":\"03\","
                + "\"cardAcceptor\":{"
                + "\"name\":\"John Smith\","
                + "\"terminalId\":\"13655392\","
                + "\"idCode\":\"VMT200911026070\","
                + "\"address\":{"
                + "\"state\":\"CA\","
                + "\"county\":\"081\","
                + "\"country\":\"USA\","
                + "\"zipCode\":\"94105\""
                + "}"
                + "},"
                + "\"feeProgramIndicator\":\"123\""
                + "}";
    }

    private void logResponse(HttpResponse response) throws IOException {
        Header[] h = response.getAllHeaders();

        // Get the response json object
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        // Print the response details
        System.out.println("Response status : " + response.getStatusLine() + "\n");
        System.out.println("Response Headers: \n");

        for (Header aH : h) {
            System.out.println(aH.getName() + ":" + aH.getValue());
        }
        System.out.println("\n Response Body:");

        if (!StringUtils.isEmpty(result.toString())) {
            ObjectMapper mapper = getObjectMapperInstance();
            Object tree;
            try {
                tree = mapper.readValue(result.toString(), Object.class);
                System.out.println("ResponseBody: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ObjectMapper getObjectMapperInstance() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true); // format json
        return mapper;
    }

    private String getCorrelationId() {
        return RandomStringUtils.random(10, true, true) + "_SC";
    }
}
