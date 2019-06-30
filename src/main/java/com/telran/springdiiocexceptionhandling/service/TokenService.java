package com.telran.springdiiocexceptionhandling.service;

public interface TokenService {

    UserCredentials decodeToken(String token);
}
