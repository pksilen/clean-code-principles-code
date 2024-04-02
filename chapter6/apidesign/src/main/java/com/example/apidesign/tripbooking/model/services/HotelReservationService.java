package com.example.apidesign.tripbooking.model.services;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface HotelReservationService extends ReservationService {
  public static class ReserveHotelError extends TripBookingServiceError {
    // ...
  }

  String reserveHotel(...);
}
