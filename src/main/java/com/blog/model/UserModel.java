package com.blog.model;

import lombok.Data;

@Data
public class UserModel extends AbstractModel{
    private String username;
    private String password;
    private Integer role;
    private String fullname;
    private String email;
    private String image;
}
