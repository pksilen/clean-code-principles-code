package com.example.cycle19;


public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(final BusStop currentBusStop);
}
