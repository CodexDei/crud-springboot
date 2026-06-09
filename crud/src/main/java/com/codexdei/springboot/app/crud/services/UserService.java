package com.codexdei.springboot.app.crud.services;

import java.util.List;

import com.codexdei.springboot.app.crud.entities.User;

public interface UserService {

    List<User> findAll();
    User save(User user);
    boolean existByUsername(String username);

}
