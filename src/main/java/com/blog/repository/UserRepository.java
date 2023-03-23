package com.blog.repository;

import com.blog.model.UserModel;

public interface UserRepository {
    UserModel findByUsername(UserModel userModel);
    Boolean insert(UserModel userModel);
    void updateAvatar(UserModel userModel);
}
