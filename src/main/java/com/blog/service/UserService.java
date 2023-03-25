package com.blog.service;

import com.blog.model.UserModel;

public interface UserService {
    UserModel checkLogin(UserModel userModel);
    boolean insert(UserModel userModel);
    void updateAvatar(UserModel userModel);
}
