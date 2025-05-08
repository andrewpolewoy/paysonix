package com.paysonix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void generateSignature_shouldReturnSignature() throws Exception {
        mockMvc.perform(post("/api/v1/signature/test")
                .header("Token", "test-token")
                .param("param1", "value1")
                .param("param2", "value2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.result[0].signature").exists());
    }

    @Test
    void generateSignature_withoutToken_shouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/signature/test")
                .param("param1", "value1"))
                .andExpect(status().isForbidden());
    }
} 