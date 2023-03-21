package com.blog.model;

import lombok.Data;

@Data
public class Comment extends AbstractModel{
    private String content;
    private Long userId;
    private Long postId;
}
