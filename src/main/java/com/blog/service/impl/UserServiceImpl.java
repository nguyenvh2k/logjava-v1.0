package com.blog.service.impl;

import com.blog.model.UserModel;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;
import com.blog.utils.HashPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean checkLogin(UserModel userModel) {
        UserModel userCheck = userRepository.findByUsername(userModel);
        if (userCheck!=null){
            if (userCheck.getPassword().equals(HashPassword.toSHA2(userModel.getPassword()))){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean insert(UserModel userModel) {
        userModel.setRole(1);
        Boolean result = userRepository.insert(userModel);
        return result;
    }
}
