package com.example.demo.adduser;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AddUser implements ApplicationListener<ContextRefreshedEvent> {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public AddUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        User user = new User();
        user.setActive(false);
        user.setEmail("test@wp.pl");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        userRepository.save(user);
    }


}
