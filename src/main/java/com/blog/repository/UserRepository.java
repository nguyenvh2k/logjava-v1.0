package com.blog.repository;


import com.blog.entity.User;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends CrudRepository<User,Integer> {
    User findByEmail(String email);
}
