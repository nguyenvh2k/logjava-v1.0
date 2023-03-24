package com.blog.repository;

import com.blog.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByName(String name);
}
