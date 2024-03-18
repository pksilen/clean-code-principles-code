package com.example.cycle9;


public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(final BusStop currentBusStop);
}
