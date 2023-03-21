package com.blog.repository;

import com.blog.model.Comment;

public interface CommentRepository {
    boolean insert(Comment comment);
}
