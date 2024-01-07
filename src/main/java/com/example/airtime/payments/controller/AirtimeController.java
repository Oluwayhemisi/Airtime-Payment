package com.example.airtime.payments.controller;


import com.example.airtime.payments.payload.AirtimeRequest;
import com.example.airtime.payments.payload.UserRequest;
import com.example.airtime.payments.service.AirtimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AirtimeController {

    private final AirtimeService airtimeService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(@Valid @RequestBody AirtimeRequest airtimeRequest) throws JsonProcessingException {
        String response = airtimeService.airtimePurchase(airtimeRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
