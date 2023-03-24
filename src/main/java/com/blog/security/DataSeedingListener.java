package com.blog.security;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Roles
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        if (roleRepository.findByName("ROLE_MEMBER") == null) {
            roleRepository.save(new Role("ROLE_MEMBER"));
        }

        // Admin account
        if (userRepository.findByUserName("admin") == null) {
            User admin = new User();
            admin.setUserName("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
            roles.add(roleRepository.findByName("ROLE_MEMBER"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // Member account
        if (userRepository.findByUserName("member") == null) {
            User user = new User();
            user.setEmail("member");
            user.setPassword(passwordEncoder.encode("123456"));
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_MEMBER"));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
