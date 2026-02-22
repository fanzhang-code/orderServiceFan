package com.fandy.orderservicefan.utils;

import com.fandy.orderservicefan.requests.CreateOrderRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class FingerprintUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private FingerprintUtil() {}

    public static String makeFingerprint(CreateOrderRequest request) {
        try {
            // Convert object to JSON
            String json = objectMapper.writeValueAsString(request);

            // Hash it using SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(json.getBytes(StandardCharsets.UTF_8));

            // Convert to hex string
            return bytesToHex(hash);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate fingerprint", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
