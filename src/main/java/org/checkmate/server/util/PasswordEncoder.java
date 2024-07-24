package org.checkmate.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Pw 암호화 클래스
 * HISTORY1: 최초 생성                              [이준희  2024.07.24]
 */
public class PasswordEncoder {
    public static String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}