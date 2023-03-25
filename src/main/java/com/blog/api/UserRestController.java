package com.blog.api;

import com.blog.model.UserModel;
import com.blog.service.UserService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * Đăng ký tài khoản mới - thêm mới user
     *
     * @param userModel
     * @return ResponseEntity.ok().body(result)
     */
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserModel userModel){
        boolean result = userService.insert(userModel);
        if (!result){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserModel userModel,HttpSession session){
        UserModel user = userService.checkLogin(userModel);
        if (user==null){
            session.setAttribute("message","Tài khoản hoặc mật khẩu không đúng!");
            return ResponseEntity.notFound().build();
        }
        session.setAttribute("userSession",user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/user/profile/avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody UserModel userModel){
        userService.updateAvatar(userModel);
        return ResponseEntity.ok().build();
    }

}
