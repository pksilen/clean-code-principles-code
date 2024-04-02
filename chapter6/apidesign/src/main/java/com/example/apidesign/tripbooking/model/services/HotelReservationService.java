package com.example.apidesign.tripbooking.model.services;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface HotelReservationService extends ReservationService {
  class ReserveHotelError extends TripBookingServiceError {
    public ReserveHotelError(Exception exception) {
      super(exception);
    }
    // ...
  }

  String reserveHotel(...);
}
