package com.example.cycle5;

import java.util.Set;

public interface BusDriver {
  BusStop driveToNextBusStop();

  Set<Rumor> getRumors();
}

