package org.example.finalspring.repository.mongoRep;

import org.example.finalspring.entity.mongo.UsersRegistration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRegistrationRepository extends MongoRepository<UsersRegistration, String> {
}
