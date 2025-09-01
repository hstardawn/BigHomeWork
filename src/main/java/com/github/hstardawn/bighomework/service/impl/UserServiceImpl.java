package com.github.hstardawn.bighomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.hstardawn.bighomework.constant.ExceptionEnum;
import com.github.hstardawn.bighomework.dto.response.LoginResponse;
import com.github.hstardawn.bighomework.entity.User;
import com.github.hstardawn.bighomework.exception.ApiException;
import com.github.hstardawn.bighomework.mapper.UserMapper;
import com.github.hstardawn.bighomework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public void register(String username, String name, String password, Integer userType) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, username);

        User user = userMapper.selectOne(userQueryWrapper);
        if (user != null) {
            throw new ApiException(ExceptionEnum.USER_ALREADY_EXISTED);
        }
        user = User.builder()
                .username(username)
                .name(name)
                .password(password)
                .userType(userType)
                .build();

        userMapper.insert(user);
    }

    @Override
    public LoginResponse login(String username, String password) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, username);

        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        if (!user.getPassword().equals(password)) {
            throw new ApiException(ExceptionEnum.WRONG_USERNAME_OR_PASSWORD);
        }

        return LoginResponse.builder()
                .userId(user.getId())
                .userType(user.getUserType())
                .build();
    }
}
