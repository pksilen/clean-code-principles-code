package com.example.cycle18;


public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(final BusStop currentBusStop);
}
