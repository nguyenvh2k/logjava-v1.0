package com.blog.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date createdDate;

    @Column
    private Date modifiedDate;

    @Column
    private String createdBy;

    @Column
    private String modifiedBy;
}
