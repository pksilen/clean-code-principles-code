package com.example.cycle13;

public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(BusStop currentBusStop);
}

