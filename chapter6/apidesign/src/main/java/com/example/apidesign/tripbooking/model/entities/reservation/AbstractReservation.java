package com.example.apidesign.tripbooking.model.entities.reservation;

import com.example.apidesign.tripbooking.model.services.ReservationService;

import java.util.Optional;

public abstract class AbstractReservation implements Reservation {
  private Optional<String> maybeId;

  public AbstractReservation(final Optional<String> maybeId) {
    this.maybeId = maybeId;
  }

  public void setId(final String id) {
    this.id = Optional.of(id);
  }

  protected void assertIsNotReserved() {
    if (maybeId.isPresent()) {
      throw new AlreadyReservedError("Reservation already exists");
    }
  }

  protected void cancelUsing(final ReservationService reservationService) {
    maybeId.ifPresent((id) -> {
      try {
        reservationService.cancelReservation(id);
        id = null;
      } catch (final ReservationService.CancelReservationError error) {
        throw new CancelError(error);
      }
    });
  }
}
