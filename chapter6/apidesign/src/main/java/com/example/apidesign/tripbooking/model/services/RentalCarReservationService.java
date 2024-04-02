package com.example.apidesign.tripbooking.model.services;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface RentalCarReservationService extends ReservationService {
  public static class ReserveCarError extends TripBookingServiceError {
    // ...
  }

  String reserveCar(...);
}
