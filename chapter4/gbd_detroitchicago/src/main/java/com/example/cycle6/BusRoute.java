package com.example.cycle6;


public interface BusRoute {
  BusStop getNextBusStop(final BusStop currentBusStop);
}
