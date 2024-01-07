package com.example.airtime.payments.service;

import com.example.airtime.payments.payload.AirtimeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AirtimeService {
    String airtimePurchase(AirtimeRequest airtimeRequest) throws JsonProcessingException;
}

