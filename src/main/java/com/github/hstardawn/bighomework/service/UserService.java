package com.github.hstardawn.bighomework.service;


import com.github.hstardawn.bighomework.dto.response.LoginResponse;

public interface UserService {
    void register(String username, String name, String password, Integer userType);

    LoginResponse login(String username, String password);
}
