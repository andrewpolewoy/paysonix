package com.paysonix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SignatureService {
    private static final Logger logger = LoggerFactory.getLogger(SignatureService.class);
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String SECRET_KEY = "your-secret-key"; // В реальном приложении должно быть в конфигурации

    public String generateSignature(Map<String, String> params) {
        logger.debug("Generating signature for params: {}", params);
        
        // Сортируем параметры по ключу
        TreeMap<String, String> sortedParams = new TreeMap<>(params);
        
        // Формируем строку для подписи
        StringBuilder stringToSign = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (stringToSign.length() > 0) {
                stringToSign.append("&");
            }
            stringToSign.append(entry.getKey()).append("=").append(entry.getValue());
        }
        
        try {
            // Создаем ключ для HMAC
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            
            // Инициализируем HMAC
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(secretKey);
            
            // Вычисляем подпись
            byte[] hmacData = mac.doFinal(stringToSign.toString().getBytes(StandardCharsets.UTF_8));
            String signature = Base64.getEncoder().encodeToString(hmacData);
            
            logger.debug("Generated signature: {}", signature);
            return signature;
            
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("Error generating signature", e);
            throw new RuntimeException("Error generating signature", e);
        }
    }
} 