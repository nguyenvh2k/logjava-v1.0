package com.blog.service;

import com.blog.model.UserModel;

public interface UserService {
    boolean checkLogin(UserModel userModel);
    boolean insert(UserModel userModel);
}
