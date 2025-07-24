package com.forumhub.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorHash {
    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        String raw = "123456";
        String hash = encoder.encode(raw);
        System.out.println(hash);
    }
}
