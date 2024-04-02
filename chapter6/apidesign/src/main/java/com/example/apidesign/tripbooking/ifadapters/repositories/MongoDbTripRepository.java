package com.example.apidesign.tripbooking.ifadapters.repositories;

import com.example.apidesign.tripbooking.model.entities.Trip;
import com.example.apidesign.tripbooking.model.repositories.TripRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDbTripRepository
    extends MongoRepository<Trip, String>, TripRepository {
  // ...
}
