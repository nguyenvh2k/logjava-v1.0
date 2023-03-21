package com.blog.service.impl;

import com.blog.model.Comment;
import com.blog.repository.CommentRepository;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public boolean insert(Comment comment) {
        return commentRepository.insert(comment);
    }
}
