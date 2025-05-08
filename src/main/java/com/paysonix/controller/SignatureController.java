package com.paysonix.controller;

import com.paysonix.dto.SignatureResponse;
import com.paysonix.security.RequiresToken;
import com.paysonix.service.SignatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * REST-контроллер для генерации подписей.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Подписи")
public class SignatureController {
    private static final Logger logger = LoggerFactory.getLogger(SignatureController.class);
    
    private final SignatureService signatureService;
    
    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }
    
    @Operation(summary = "Генерация HMAC SHA256 подписи")
    @ApiResponse(responseCode = "200", description = "Успешно")
    @ApiResponse(responseCode = "403", description = "Ошибка аутентификации")
    @PostMapping("/signature/{operationId}")
    @RequiresToken
    public ResponseEntity<SignatureResponse> generateSignature(
            @PathVariable String operationId,
            @RequestParam Map<String, String> formParams) {
        
        logger.info("Получен запрос на генерацию подписи для operationId: {}", operationId);
        
        String signature = signatureService.generateSignature(formParams);
        SignatureResponse response = new SignatureResponse();
        response.setStatus("success");
        
        SignatureResponse.SignatureResult result = new SignatureResponse.SignatureResult();
        result.setSignature(signature);
        response.setResult(Collections.singletonList(result));
        
        logger.debug("Подпись успешно сгенерирована");
        return ResponseEntity.ok(response);
    }
} 