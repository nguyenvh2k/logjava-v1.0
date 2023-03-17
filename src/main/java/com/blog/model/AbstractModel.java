package com.blog.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public abstract class AbstractModel {
    private Long id;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private Long[] ids;
    private int page;
    private int maxPageItem;
    private int totalPage;
    private int totalItem;
    private String sortName;
    private String sortBy;
    private String type;
}
