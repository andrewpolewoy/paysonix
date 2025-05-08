package com.paysonix.security;

import com.paysonix.controller.SignatureController;
import com.paysonix.service.SignatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignatureController.class)
class TokenSecurityAspectTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignatureService signatureService;

    @Test
    void validateToken_withValidToken_shouldAllowAccess() throws Exception {
        mockMvc.perform(post("/api/v1/signature/test")
                .header("Token", "test-token")
                .param("param1", "value1"))
                .andExpect(status().isOk());
    }

    @Test
    void validateToken_withInvalidToken_shouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/signature/test")
                .header("Token", "invalid-token")
                .param("param1", "value1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void validateToken_withoutToken_shouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/signature/test")
                .param("param1", "value1"))
                .andExpect(status().isForbidden());
    }
} 