package com.example.airtime.payments.services;

import com.example.airtime.payments.controller.AirtimeController;
import com.example.airtime.payments.controller.UserController;
import com.example.airtime.payments.entity.AirtimePayment;
import com.example.airtime.payments.enums.Status;
import com.example.airtime.payments.payload.AirtimeRequest;
import com.example.airtime.payments.payload.AirtimeResponse;
import com.example.airtime.payments.payload.LoginRequest;
import com.example.airtime.payments.payload.LoginResponseDto;
import com.example.airtime.payments.repository.AirtimeRepository;
import com.example.airtime.payments.service.AirtimeService;
import com.example.airtime.payments.service.AirtimeServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.math.BigDecimal;

import static com.example.airtime.payments.utils.PaymentHash.calculateHMAC512;
import static com.example.airtime.payments.utils.PaymentHash.generatePayload;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

public class AirtimeTest {
    @Mock
    private AirtimeService airtimeService;

//    @MockBean
//    private WebClient webClient;

    @Value("${app.xpress.authorizationHeaderValue}")
    private String authorizationValue;

    @Value("${app.xpress.privateKey}")
    private String privateKey;




    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }
    private MockMvc mockMvc;
//    @Autowired
    AirtimeRepository airtimeRepository = mock(AirtimeRepository.class);
    @InjectMocks
AirtimeController airtimeController;
    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }
    @BeforeEach
    void initialize() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(airtimeController).build();
        LoginRequest loginDto = new LoginRequest("bee@gmail.com", "password123");

        String baseUrl = String.format("http://localhost:9898/api/v1/purchase",
                mockBackEnd.getPort());
//        airtimeService = new AirtimeServiceImpl(baseUrl);
    }

    @Test
    void purchase_airtime() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
//        LoginRequest loginDto = new LoginRequest("bee@gmail.com", "password123");
//
//        ResponseEntity<LoginResponseDto> loginResponseEntity = userController.loginUser(loginDto);
//
//        // Check if login was successful
//        assertThat(loginResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        // Get the token from the login response
//        LoginResponseDto loginResponse = loginResponseEntity.getBody();
//        String token = loginResponse.getToken();
//

        Object data = null;

        // Set up test data and mocks
        AirtimeRequest airtimeRequest = new AirtimeRequest("123456","MTN_24207","08033333333",new BigDecimal("100.00"));
        // Configure your webClient mock here
        AirtimeResponse mockResponse = new AirtimeResponse("123456", "MATT14539722120213053702634214","00","Successful",data); // Successful response

        String payload = generatePayload(airtimeRequest);
        String paymentHash = calculateHMAC512(payload,"hX2YBua742apfMN4nNQFm2nybluaMnrh_CVASPRV" );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+authorizationValue );
        headers.add("PaymentHash", paymentHash);
        headers.add("channel", "pos");
        headers.add("Content-Type", "application/json");
//        headers.set("Bearer Token", token);
//        AirtimePayment savedPayment = airtimeRepository.save(airtimePayment);
AirtimePayment airtimePayment = new AirtimePayment();
airtimePayment.setId(1L);
airtimePayment.setStatus(Status.PENDING);
when(airtimeRepository.save(any())).thenReturn(airtimePayment);
mockMvc.perform(post("")
        .contentType("")
        .content(""))
                .andExpect(status().is2xxSuccessful());

//        mockBackEnd.enqueue(new MockResponse()
//                .setBody(objectMapper.writeValueAsString(airtimeRequest))
//                .addHeader("Authorization", "Bearer "+authorizationValue)
//                .addHeader("PaymentHash", paymentHash)
//                .addHeader("channel", "pos")
//                .addHeader("Content-Type", "application/json"));
////                .addHeader("Bearer Token", token));
//
//        String airtimeRequestMono = airtimeService.airtimePurchase(airtimeRequest);
//
//        StepVerifier.create(Mono.just(airtimeRequestMono))
//                .expectNextMatches(responseString -> {
//                    // Deserialize the response string to AirtimeResponse
//                    AirtimeResponse airtimeResponse = null;
//                    try {
//                        airtimeResponse = objectMapper.readValue(responseString, AirtimeResponse.class);
//                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
//                    }
//
//                    // Add assertions for the specific field you want to verify
//                    return airtimeResponse.getResponseCode().equals("00");
//                })
//                .verifyComplete();
//
//        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
//
//        assertEquals("GET", recordedRequest.getMethod());
//        assertEquals("/employee/100", recordedRequest.getPath());

    }



}
