package com.example.cycle3;


public interface BusRoute {
  BusStop getNextBusStop(final BusStop currentBusStop);
}
