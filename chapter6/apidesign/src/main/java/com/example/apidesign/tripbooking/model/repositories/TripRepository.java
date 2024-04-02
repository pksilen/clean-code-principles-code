package com.example.apidesign.tripbooking.model.repositories;

import com.example.apidesign.tripbooking.model.entities.Trip;
import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

import java.util.Optional;

public interface TripRepository {
  class Error extends TripBookingServiceError {
    public Error(final Exception exception) {
      super(exception);
    }
    // ...
  }

  void save(final Trip trip);

  Optional<Trip> findById(final String id);

  void update(final Trip trip);
}
