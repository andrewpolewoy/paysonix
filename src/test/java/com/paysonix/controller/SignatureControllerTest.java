package com.paysonix.controller;

import com.paysonix.dto.SignatureResponse;
import com.paysonix.service.SignatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignatureController.class)
class SignatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignatureService signatureService;

    @Test
    void generateSignature_shouldReturnSignature() throws Exception {
        // Arrange
        String operationId = "test-operation";
        Map<String, String> params = new HashMap<>();
        params.put("param1", "value1");
        params.put("param2", "value2");

        String expectedSignature = "test-signature";
        when(signatureService.generateSignature(any())).thenReturn(expectedSignature);

        // Act & Assert
        mockMvc.perform(post("/api/v1/signature/{operationId}", operationId)
                .header("Token", "test-token")
                .param("param1", "value1")
                .param("param2", "value2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.result[0].signature").value(expectedSignature));
    }

    @Test
    void generateSignature_withoutToken_shouldReturnForbidden() throws Exception {
        // Arrange
        String operationId = "test-operation";

        // Act & Assert
        mockMvc.perform(post("/api/v1/signature/{operationId}", operationId)
                .param("param1", "value1"))
                .andExpect(status().isForbidden());
    }
} 