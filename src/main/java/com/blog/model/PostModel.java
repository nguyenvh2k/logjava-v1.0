package com.blog.model;

import lombok.Data;

@Data
public class PostModel extends AbstractModel{
    private String title;
    private String thumbnail;
    private String shortDescription;
    private String image;
    private String content;
    private Long categoryId;
    private String categoryCode;
    private String categoryName;
    private Long userId;
    private UserModel userModel = new UserModel();
    private Comment comment = new Comment();
    private String date;
}
