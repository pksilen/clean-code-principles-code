package com.example.cycle14;


public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(final BusStop currentBusStop);
}
