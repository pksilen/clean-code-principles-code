package com.example.apidesign.tripbooking.model.entities.reservation;

import com.example.apidesign.tripbooking.model.dtos.input.InputRentalCarReservation;
import com.example.apidesign.tripbooking.model.services.RentalCarReservationService;

import java.util.Optional;

public class RentalCarReservation extends AbstractReservation {
  private final RentalCarReservationService rentalCarReservationService;

  public RentalCarReservation(...) {
    super(Optional.empty());
  }

  public static RentalCarReservation from(final InputRentalCarReservation inputRentalCarReservation) {
    // ...
  }

  @Override
  public void make() {
    assertIsNotReserved();

    try {
      this.setId(rentalCarReservationService.reserveCar(...));
    } catch (final RentalCarReservationService.ReserveCarError error) {
      throw new MakeError(error);
    }
  }

  @Override
  public void cancel() {
    cancelUsing(rentalCarReservationService);
  }
}
