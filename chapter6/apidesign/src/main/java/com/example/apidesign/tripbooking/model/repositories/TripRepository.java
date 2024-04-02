package com.example.apidesign.tripbooking.model.repositories;

import com.example.apidesign.tripbooking.model.entities.Trip;
import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface TripRepository {
  public static class Error extends TripBookingServiceError {
    // ...
  }

  void save(final Trip trip);
}
