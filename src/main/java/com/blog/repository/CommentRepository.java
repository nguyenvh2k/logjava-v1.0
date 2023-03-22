package com.blog.repository;

import com.blog.model.Comment;

public interface CommentRepository {
    Long insert(Comment comment);
    Comment findById(Long id);
    void delete(Long id);
}
