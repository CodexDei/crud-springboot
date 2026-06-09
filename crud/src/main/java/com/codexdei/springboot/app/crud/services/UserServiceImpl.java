package com.codexdei.springboot.app.crud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codexdei.springboot.app.crud.entities.Role;
import com.codexdei.springboot.app.crud.entities.User;
import com.codexdei.springboot.app.crud.repositories.RoleRepository;
import com.codexdei.springboot.app.crud.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {

        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {

        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");

        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresentOrElse(roles::add, () -> System.out.println("Role not found"));

        if (user.isAdmin()) {
            
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresentOrElse(roles::add, () -> System.out.println("Role not found"));
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));        

        return userRepository.save(user);
    }

    @Override
    public boolean existByUsername(String username) {

        return userRepository.existsByUsername(username);
    }

}
