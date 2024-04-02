package com.example.apidesign.tripbooking.model.services;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface ReservationService {
  class CancelReservationError extends TripBookingServiceError {
    // ...
  }

  void cancelReservation(final String id);
}
