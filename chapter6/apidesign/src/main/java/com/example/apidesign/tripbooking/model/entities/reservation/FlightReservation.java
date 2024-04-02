package com.example.apidesign.tripbooking.model.entities.reservation;

import com.example.apidesign.tripbooking.model.services.FlightReservationService;

import java.util.Optional;

public class FlightReservation extends AbstractReservation {
  private final FlightReservationService flightReservationService;

  public FlightReservation(
      final FlightReservationService flightReservationService, ...
  ) {
    super(Optional.empty());
  }

  @Override
  public void make() {
    assertIsNotReserved();

    try {
      this.setId(flightReservationService.reserveFlight(...));
    } catch (final FlightReservationService.ReserveFlightError error) {
      throw new MakeError(error);
    }
  }

  @Override
  public void cancel() {
    cancelUsing(flightReservationService);
  }
}
