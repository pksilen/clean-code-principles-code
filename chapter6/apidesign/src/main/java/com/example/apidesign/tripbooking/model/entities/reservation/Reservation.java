package com.example.apidesign.tripbooking.model.entities.reservation;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface Reservation {
  public static class MakeError extends TripBookingServiceError {
    // ...
  }

  class AlreadyReservedError extends MakeError {
    // ...
  }

  void make();

  public static class CancelError extends TripBookingServiceError {
    // ...
  }

  void cancel();
}
