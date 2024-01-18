package com.example.airtime.payments.services;

import com.example.airtime.payments.controller.AirtimeController;
import com.example.airtime.payments.payload.AirtimeRequest;
import com.example.airtime.payments.service.AirtimeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;


public class AirtimeTest {
    private MockMvc mockMvc;

    @Mock
    private AirtimeService airtimeService;

    @InjectMocks
    private AirtimeController airtimeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(airtimeController).build();
    }

    @Test
    void testPurchase() throws Exception {
        Mockito.when(airtimeService.airtimePurchase(Mockito.any(AirtimeRequest.class)))
                .thenReturn("Airtime recharged");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"phoneNumber\":\"test@example.com\",\"amount\":\"100\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Airtime recharged"));
        verify(airtimeService, times(1)).airtimePurchase(Mockito.any(AirtimeRequest.class));
    }

}
