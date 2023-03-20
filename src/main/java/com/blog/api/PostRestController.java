package com.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.model.PostModel;
import com.blog.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostRestController {
    
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> insert(@RequestBody PostModel postModel){
        Boolean result = postService.insert(postModel);
        if(!result){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(postModel);
    }

}
