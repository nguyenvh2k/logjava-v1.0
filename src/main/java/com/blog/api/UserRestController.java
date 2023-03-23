package com.blog.api;

import com.blog.model.UserModel;
import com.blog.service.UserService;
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

}
