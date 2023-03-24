package com.blog.repository;

import com.blog.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepo")
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUserName(String userName);
    User findByEmail(String email);
}
