package com.blog.api;

import com.blog.model.Comment;
import com.blog.service.CommentService;
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

    @Autowired
    private CommentService commentService;

    @PostMapping("/post")
    public ResponseEntity<?> insert(@RequestBody PostModel postModel){
        Boolean result = postService.insert(postModel);
        if(!result){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(postModel);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> insertComment(@RequestBody Comment comment){
        Boolean result = commentService.insert(comment);
        if (!result){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(comment);
    }

}
