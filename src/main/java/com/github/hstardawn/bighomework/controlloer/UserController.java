package com.github.hstardawn.bighomework.controlloer;

import com.github.hstardawn.bighomework.dto.request.LoginRequest;
import com.github.hstardawn.bighomework.dto.request.RegisterRequest;
import com.github.hstardawn.bighomework.dto.response.LoginResponse;
import com.github.hstardawn.bighomework.result.AjaxResult;
import com.github.hstardawn.bighomework.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        LoginResponse response = userService.login(loginData.getUsername(), loginData.getPassword());
        return AjaxResult.success(response);
    }
}