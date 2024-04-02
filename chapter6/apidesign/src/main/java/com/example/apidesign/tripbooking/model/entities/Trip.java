package com.example.apidesign.tripbooking.model.entities;

import com.example.apidesign.tripbooking.model.dtos.input.InputTrip;
import com.example.apidesign.tripbooking.model.entities.reservation.FlightReservation;
import com.example.apidesign.tripbooking.model.entities.reservation.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Trip {
  private final List<Reservation> reservations;

  public Trip(final List<Reservation> reservations, ...) {
    this.reservations = reservations;
  }

  public static Trip from(final InputTrip inputTrip) {
    final List<Reservation> reservations = new ArrayList<>();

    for (final var flightReservation : inputTrip.getFlightReservations()) {
      reservations.add(new FlightReservation(...));
    }

    // Similar loop as above for hotel reservations and
    // rental car reservations

    return new Trip(reservations, /* ... other fields */);
  }

  public void makeReservations() {
    for (final var reservation : reservations) {
      try {
        reservation.make();
      } catch (final Reservation.MakeError error) {
        this.cancelReservations();
        throw error;
      }
    }
  }

  public void cancelReservations() {
    // In production code, this loop cannot be forever but should be
    // replaced with more robust and complex error handling as described
    // earlier in first chapter when discussing distributed transactions

    while (!reservations.isEmpty()) {
      for (final var reservation : reservations) {
        try {
          reservation.cancel();
          reservations.remove(reservation);
          break;
        } catch (final Reservation.CancelError error) {
          // Intentionally no operation
        }
      }
    }
  }

  public void add(final Reservation reservation) {
    reservation.make();
    reservations.add(reservation);
  }

  public void remove(final Reservation reservation) {
    while (true) {
      try {
        reservation.cancel();
        break;
      } catch (final Reservation.CancelError error) {
        // Intentionally no operation
      }
    }

    reservations.remove(reservation);
  }
}
