package com.example.apidesign.tripbooking.model.services;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface FlightReservationService extends ReservationService {
  class ReserveFlightError extends TripBookingServiceError {
    public ReserveFlightError(Exception exception) {
      super(exception);
    }
    // ...
  }

  String reserveFlight(...);
}
