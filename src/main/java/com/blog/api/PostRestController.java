package com.blog.api;

import com.blog.model.Comment;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.model.PostModel;
import com.blog.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostRestController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    /**
     * Thêm mới bài viết
     *
     * @param postModel
     * @return ResponseEntity<?>
     */
    @PostMapping("/post")
    public ResponseEntity<?> insertPost(@RequestBody PostModel postModel){
        Boolean result = postService.insert(postModel);
        if(!result){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(postModel);
    }

    /**
     * Cập nhật bài viết
     *
     * @param postModel
     * @return ResponseEntity<?>
     */
    @PutMapping ("/post")
    public ResponseEntity<?> updatePost(@RequestBody PostModel postModel){
        postService.update(postModel);
        return ResponseEntity.ok().body(postModel);
    }


    /**
     * Thêm mới bình luận bài viết
     *
     * @param
     * @return ResponseEntity<?>
     */
    @PostMapping("/comment")
    public ResponseEntity<?> insertComment(@RequestBody Comment comment){
        Comment result = commentService.insert(comment);
        if (result==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(result);
    }

    /**
     * Xoá bình luận bài viết
     *
     * @param comment
     * @return
     */
    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestBody Comment comment){
        commentService.delete(comment.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestBody PostModel postModel){
        postService.delete(postModel.getId());
        return ResponseEntity.ok().build();
    }

}
