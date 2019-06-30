package com.telran.springdiiocexceptionhandling.service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public UserCredentials decodeToken(String token) {
        int index = token.indexOf(" ");
        token.substring(index + 1);
        byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
        token = new String(base64DecodeBytes);
        String[] auth = token.split(":");
        return new UserCredentials(auth[0], auth[1]);
    }
}
