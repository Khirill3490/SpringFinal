package org.example.finalspring.service;

import org.example.finalspring.entity.mongo.UsersRegistration;

import java.util.List;

public interface UsersRegistrationService {

    UsersRegistration save(UsersRegistration usersRegistration);

    List<UsersRegistration> findAll();
}
