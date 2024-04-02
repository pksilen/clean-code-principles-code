package com.example.apidesign.tripbooking.model.services;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface FlightReservationService extends ReservationService {
  public static class ReserveFlightError extends TripBookingServiceError {
    // ...
  }

  String reserveFlight(...);
}
