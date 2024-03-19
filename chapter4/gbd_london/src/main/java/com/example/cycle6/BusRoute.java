package com.example.cycle6;

public interface BusRoute {
  BusStop getFirstBusStop();

  BusStop getNextBusStop(BusStop currentBusStop);
}

