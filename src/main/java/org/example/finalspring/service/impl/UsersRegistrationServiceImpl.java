package org.example.finalspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.mongo.UsersRegistration;
import org.example.finalspring.repository.mongoRep.UsersRegistrationRepository;
import org.example.finalspring.service.UsersRegistrationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersRegistrationServiceImpl implements UsersRegistrationService {

    private final UsersRegistrationRepository repository;

    @Override
    public UsersRegistration save(UsersRegistration usersRegistration) {
        usersRegistration.setId(UUID.randomUUID().toString());
        usersRegistration.setRegistrationDate(LocalDateTime.now());
        return repository.save(usersRegistration);
    }

    @Override
    public List<UsersRegistration> findAll() {
        return repository.findAll();
    }
}
