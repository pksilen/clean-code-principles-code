package com.example.cycle5;


public interface BusRoute {
  BusStop getNextBusStop(final BusStop currentBusStop);
}
