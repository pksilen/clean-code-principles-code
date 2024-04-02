package com.example.apidesign.tripbooking.model.usecases;

import com.example.apidesign.tripbooking.model.dtos.input.InputRentalCarReservation;
import com.example.apidesign.tripbooking.model.dtos.input.InputTrip;
import com.example.apidesign.tripbooking.model.dtos.output.OutputRentalCarReservation;
import com.example.apidesign.tripbooking.model.dtos.output.OutputTrip;
import com.example.apidesign.tripbooking.model.entities.Trip;
import com.example.apidesign.tripbooking.model.entities.reservation.RentalCarReservation;
import com.example.apidesign.tripbooking.model.errors.EntityNotFoundError;
import com.example.apidesign.tripbooking.model.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripBookingUseCasesImpl implements TripBookingUseCases {
  @Autowired
  private TripRepository tripRepository;

  @Override
  @Transactional
  public OutputTrip bookTrip(InputTrip inputTrip) {
    final Trip trip = Trip.from(inputTrip);
    trip.makeReservations();

    try {
      tripRepository.save(trip);
    } catch (final TripRepository.Error error) {
      trip.cancelReservations();
      throw error;
    }

    return OutputTrip.from(trip);
  }

  @Override
  @Transactional
  public OutputRentalCarReservation addRentalCarReservation(
      final String tripId,
      final InputRentalCarReservation inputRentalCarReservation
  ) {
    // Retrieve the Trip entity
    final var trip = tripRepository.findById(tripId)
        .orElseThrow(() -> new EntityNotFoundError("Trip", tripId));

    // Create RentalCarReservation domain object
    final var rentalCarReservation =
        RentalCarReservation.from(inputRentalCarReservation);

    trip.add(rentalCarReservation);

    try {
      tripRepository.save(trip);
    } catch (final TripRepository.Error error) {
      trip.remove(rentalCarReservation);
      throw error;
    }

    return OutputRentalCarReservation.from(rentalCarReservation);
  }
}
