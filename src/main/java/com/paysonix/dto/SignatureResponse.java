package com.paysonix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignatureResponse {
    private String status;
    private List<SignatureResult> result;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignatureResult {
        private String signature;
    }
} 