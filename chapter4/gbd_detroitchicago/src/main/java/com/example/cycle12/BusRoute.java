package com.example.cycle12;


public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(final BusStop currentBusStop);
}
