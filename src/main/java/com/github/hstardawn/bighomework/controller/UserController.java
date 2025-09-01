package com.github.hstardawn.bighomework.controller;

import com.github.hstardawn.bighomework.dto.request.LoginRequest;
import com.github.hstardawn.bighomework.dto.request.RegisterRequest;
import com.github.hstardawn.bighomework.dto.response.LoginResponse;
import com.github.hstardawn.bighomework.result.AjaxResult;
import com.github.hstardawn.bighomework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/reg")
    public AjaxResult<Void> register(@Valid @RequestBody RegisterRequest registerData) {
        userService.register(registerData.getUsername(), registerData.getName(), registerData.getPassword(),
                registerData.getUserType());
        return AjaxResult.success();
    }

    @PostMapping("/login")
    public AjaxResult<LoginResponse> login(@Valid @RequestBody LoginRequest loginData) {
        return AjaxResult.success(userService.login(loginData.getUsername(), loginData.getPassword()));
    }
}