package com.example.apidesign.tripbooking.model.entities.reservation;

import com.example.apidesign.tripbooking.model.services.HotelReservationService;

import java.util.Optional;

public class HotelReservation extends AbstractReservation {
  private final HotelReservationService hotelReservationService;

  public HotelReservation(...) {
    super(Optional.empty());
  }

  @Override
  public void make() {
    assertIsNotReserved();

    try {
      this.setId(hotelReservationService.reserveHotel(...));
    } catch (final HotelReservationService.ReserveHotelError error) {
      throw new MakeError( error);
    }
  }

  @Override
  public void cancel() {
    cancelUsing(hotelReservationService);
  }
}
