package com.example.airtime.payments.utils;

import com.example.airtime.payments.payload.AirtimeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;

import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PaymentHash {


    @Value("${app.xpress.privateKey}")
    private static String privateKey;

    public static String generatePayload(AirtimeRequest airtimeRequest) {
       return "{\n" +
       "\"requestId\":\"" + generateRequestId() + "\",\n" +
       "\"uniqueCode\":\"" + airtimeRequest.getUniqueCode() + "\",\n" +
       "\"details\":{\n" +
       "\"phoneNumber\":\"" + airtimeRequest.getPhoneNumber() + "\",\n" +
       "\"amount\":" + airtimeRequest.getAmount() + "\n" +
       "}\n" +
       "}";
    }


    public static String calculateHMAC512(String data, String key) {


        String HMAC_SHA512 = "HmacSHA512";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);

        Mac mac = null;

        try {

            mac = Mac.getInstance(HMAC_SHA512);

            mac.init(secretKeySpec);

            return Hex.encodeHexString(mac.doFinal(data.getBytes()));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {

            e.printStackTrace();

            throw new RuntimeException(e.getMessage());

        }

    }
    private static String generateRequestId(){
        SecureRandom secureRandom = new SecureRandom();
        long minValue = 10000L;
        long maxValue = 99999L;
        long randomNumber = secureRandom.nextLong(minValue, maxValue + 1);


        String result = String.valueOf(randomNumber);
        return result;


    }
}
