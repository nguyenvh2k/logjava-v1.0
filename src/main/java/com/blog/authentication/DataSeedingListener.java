package com.blog.authentication;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

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
        if (roleRepository.findByName("ADMIN") == null) {
            roleRepository.save(new Role("ADMIN"));
        }

        if (roleRepository.findByName("MEMBER") == null) {
            roleRepository.save(new Role("MEMBER"));
        }

        // Admin account
        if (userRepository.findByEmail("admin") == null) {
            User admin = new User();
            admin.setEmail("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ADMIN"));
            roles.add(roleRepository.findByName("MEMBER"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // Member account
        if (userRepository.findByEmail("member") == null) {
            User user = new User();
            user.setEmail("member");
            user.setPassword(passwordEncoder.encode("123456"));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("MEMBER"));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
