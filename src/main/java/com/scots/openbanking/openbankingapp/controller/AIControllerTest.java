//package com.scots.openbanking.openbankingapp.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scots.openbanking.openbankingapp.service.AIService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Map;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(AIController.class)
//class AIControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AIService aiService;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    void normalMessage_returnsOkWithReply() throws Exception {
//        when(aiService.askFinancialQuestion("What is my balance?"))
//                .thenReturn("Your balance is $5000.");
//
//        mockMvc.perform(post("/api/ai/chat")
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(Map.of("message", "What is my balance?"))))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.reply").value("Your balance is $5000."));
//    }
//
//    @Test
//    void messageAtCharacterLimit_isAccepted() throws Exception {
//        String message500 = "a".repeat(500);
//        when(aiService.askFinancialQuestion(message500)).thenReturn("ok");
//
//        mockMvc.perform(post("/api/ai/chat")
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(Map.of("message", message500))))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void messageOverCharacterLimit_isRejected() throws Exception {
//        String message501 = "a".repeat(501);
//
//        mockMvc.perform(post("/api/ai/chat")
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(Map.of("message", message501))))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").exists());
//    }
//
//    @Test
//    void emptyMessage_isRejected() throws Exception {
//        mockMvc.perform(post("/api/ai/chat")
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(Map.of("message", ""))))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void missingMessage_isRejected() throws Exception {
//        mockMvc.perform(post("/api/ai/chat")
//                        .contentType(APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isBadRequest());
//    }
//}