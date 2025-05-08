package com.paysonix.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignatureServiceTest {

    @Autowired
    private SignatureService signatureService;

    @Test
    void generateSignature_shouldGenerateValidSignature() {
        // Arrange
        Map<String, String> params = new HashMap<>();
        params.put("param1", "value1");
        params.put("param2", "value2");

        // Act
        String signature = signatureService.generateSignature(params);

        // Assert
        assertNotNull(signature);
        assertTrue(signature.length() > 0);
    }

    @Test
    void generateSignature_withSameParams_shouldGenerateSameSignature() {
        // Arrange
        Map<String, String> params1 = new HashMap<>();
        params1.put("param1", "value1");
        params1.put("param2", "value2");

        Map<String, String> params2 = new HashMap<>();
        params2.put("param2", "value2");
        params2.put("param1", "value1");

        // Act
        String signature1 = signatureService.generateSignature(params1);
        String signature2 = signatureService.generateSignature(params2);

        // Assert
        assertEquals(signature1, signature2);
    }
} 