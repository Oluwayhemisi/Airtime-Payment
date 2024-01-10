package com.example.airtime.payments.service;

import com.example.airtime.payments.entity.AirtimeDetails;
import com.example.airtime.payments.entity.AirtimePayment;
import com.example.airtime.payments.enums.Status;
import com.example.airtime.payments.exceptions.AirtimeException;
import com.example.airtime.payments.payload.AirtimeRequest;
import com.example.airtime.payments.payload.AirtimeResponse;
import com.example.airtime.payments.repository.AirtimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.SecureRandom;

import static com.example.airtime.payments.utils.PaymentHash.calculateHMAC512;
import static com.example.airtime.payments.utils.PaymentHash.generatePayload;


@Service
@RequiredArgsConstructor
public class AirtimeServiceImpl implements AirtimeService {

    private final   WebClient webClient;

    private final   AirtimeRepository airtimeRepository;



    @Value("${app.xpress.authorizationHeaderValue}")
    private String authorizationValue;

    @Value("${app.xpress.privateKey}")
    private String privateKey;


    public String airtimePurchase(AirtimeRequest airtimeRequest) throws JsonProcessingException {


        AirtimeDetails airtimeDetails = new AirtimeDetails();
        airtimeDetails.setPhoneNumber(airtimeRequest.getPhoneNumber());
        airtimeDetails.setAmount(airtimeRequest.getAmount());

        AirtimePayment airtimePayment = new AirtimePayment();
        airtimePayment.setUniqueCode(airtimeRequest.getUniqueCode());
        airtimePayment.setAirtimeDetails(airtimeDetails);
        airtimePayment.setRequestId(generateRequestId());
        airtimePayment.setStatus(Status.PENDING);

        AirtimePayment savedPayment = airtimeRepository.save(airtimePayment);



        String payload = generatePayload(airtimeRequest);
        String paymentHash = calculateHMAC512(payload,privateKey );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+authorizationValue );
        headers.add("PaymentHash", paymentHash);
        headers.add("channel", "pos");
        headers.add("Content-Type", "application/json");



        AirtimeResponse response = webClient.post()
                .uri("https://billerstest.xpresspayments.com:9603/api/v1/airtime/fulfil")
                .headers(h -> h.addAll(headers))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .bodyToMono(AirtimeResponse.class)
                .block();


        if(!response.getResponseCode().equals("00")){
            savedPayment.setStatus(Status.FAILED);
            airtimeRepository.save(savedPayment);
            throw new AirtimeException("Airtime purchase is not successful", HttpStatus.BAD_REQUEST);
        }
        savedPayment.setStatus(Status.SUCCESSFUL);
        airtimeRepository.save(savedPayment);

        return "Airtime recharged";

    }


    private String generateRequestId(){
        SecureRandom secureRandom = new SecureRandom();
        long minValue = 10000L;
        long maxValue = 99999L;
        long randomNumber = secureRandom.nextLong(minValue, maxValue + 1);


        String result = String.valueOf(randomNumber);
        return result;


    }
}


