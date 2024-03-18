package com.example.cycle7;


public interface BusRoute {
  BusStop getNextBusStop(final BusStop currentBusStop);
}
