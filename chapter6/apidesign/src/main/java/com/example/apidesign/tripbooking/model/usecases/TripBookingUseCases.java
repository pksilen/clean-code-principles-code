package com.example.apidesign.tripbooking.model.usecases;

import com.example.apidesign.tripbooking.model.dtos.input.InputRentalCarReservation;
import com.example.apidesign.tripbooking.model.dtos.input.InputTrip;
import com.example.apidesign.tripbooking.model.dtos.output.OutputRentalCarReservation;
import com.example.apidesign.tripbooking.model.dtos.output.OutputTrip;

public interface TripBookingUseCases {
  OutputTrip bookTrip(final InputTrip inputTrip);

  OutputRentalCarReservation addRentalCarReservation(
      final String tripId,
      final InputRentalCarReservation inputRentalCarReservation
  );
}
