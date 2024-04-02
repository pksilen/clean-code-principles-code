package com.example.apidesign.tripbooking.model.services;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface ReservationService {
  class CancelReservationError extends TripBookingServiceError {
    public CancelReservationError(Exception exception) {
      super(exception);
    }
    // ...
  }

  void cancelReservation(final String id);
}
