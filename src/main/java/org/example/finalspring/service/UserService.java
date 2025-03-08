package org.example.finalspring.service;

import org.example.finalspring.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User findById(Long id);

    User findByUsername(String name);

    User register(User user);

    User update(Long id, User user);

    void delete(Long id);
}
