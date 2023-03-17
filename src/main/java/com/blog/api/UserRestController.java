package com.blog.api;

import com.blog.model.UserModel;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserModel userModel){
        boolean result = userService.insert(userModel);
        if (!result){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(result);
    }

}
