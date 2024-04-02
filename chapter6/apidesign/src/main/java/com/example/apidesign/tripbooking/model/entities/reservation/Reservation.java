package com.example.apidesign.tripbooking.model.entities.reservation;

import com.example.apidesign.tripbooking.model.errors.TripBookingServiceError;

public interface Reservation {
  class MakeError extends TripBookingServiceError {
    public MakeError(final Exception exception) {
      super(exception);
    }
    // ...
  }

  class AlreadyReservedError extends MakeError {
    public AlreadyReservedError(final Exception exception) {
      super(exception);
    }
    // ...
  }

  void make();

  public static class CancelError extends TripBookingServiceError {
    public CancelError(final Exception exception) {
      super(exception);
    }
    // ...
  }

  void cancel();
}
