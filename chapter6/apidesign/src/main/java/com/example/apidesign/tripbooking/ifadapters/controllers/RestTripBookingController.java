package com.example.apidesign.tripbooking.ifadapters.controllers;

import com.example.apidesign.tripbooking.model.dtos.input.InputTrip;
import com.example.apidesign.tripbooking.model.dtos.output.OutputTrip;
import com.example.apidesign.tripbooking.model.usecases.TripBookingUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trips")
public class RestTripBookingController {
  @Autowired
  private TripBookingUseCases tripBookingUseCases;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OutputTrip bookTrip(@RequestBody InputTrip inputTrip) {
    return tripBookingUseCases.bookTrip(inputTrip);
  }
}
