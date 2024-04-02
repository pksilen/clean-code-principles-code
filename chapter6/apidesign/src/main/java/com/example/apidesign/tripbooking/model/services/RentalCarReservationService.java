package com.example.apidesign.tripbooking.model.services;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface RentalCarReservationService extends ReservationService {
  class ReserveCarError extends TripBookingServiceError {
    public ReserveCarError(Exception exception) {
      super(exception);
    }
    // ...
  }

  String reserveCar(...);
}
