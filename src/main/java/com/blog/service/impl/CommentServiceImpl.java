package com.blog.service.impl;

import com.blog.repository.CommentRepository;
import com.blog.model.Comment;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment insert(Comment comment) {
       Long id =  commentRepository.insert(comment);
       Comment commentNew  = commentRepository.findById(id);
       return commentNew;
    }

    @Override
    public void delete(Long id) {
        commentRepository.delete(id);
    }
}
