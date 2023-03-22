package com.blog.service;

import com.blog.model.Comment;

public interface CommentService {
    Comment insert(Comment comment);
    void delete(Long id);
}
