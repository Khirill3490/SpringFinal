package org.example.finalspring.repository.mongoRep;

import org.example.finalspring.entity.mongo.RoomsBooking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomsBookingRepository extends MongoRepository<RoomsBooking, String> {
}
