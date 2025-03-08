package org.example.finalspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.User;
import org.example.finalspring.exception.EntityNotFoundException;
import org.example.finalspring.repository.UserRepository;
import org.example.finalspring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с id " + id + " не найден"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с username '" + username + "' не найден"));
    }

    @Override
    public User register(User user) {
        boolean existsByUsername = userRepository.existsByUsername(user.getUsername());
        boolean existsByEmail = userRepository.existsByEmail(user.getEmail());

        if (existsByUsername) {
            throw new EntityNotFoundException("Пользователь с данным username уже существует");
        } if (existsByEmail) {
            throw new EntityNotFoundException("Пользователь с данным email уже существует");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User userToUpdate = findById(id);

        if (!user.getUsername().equals(userToUpdate.getUsername())) {
            boolean existsByUsername = userRepository.existsByUsername(user.getUsername());
            if (existsByUsername) {
                throw new EntityNotFoundException("Пользователь с данным username уже существует");
            }
        }
        if (!user.getEmail().equals(userToUpdate.getEmail())) {
            boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
            if (existsByEmail) {
                throw new EntityNotFoundException("Пользователь с данным email уже существует");
            }
        }

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setRoles(user.getRoles());

        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
