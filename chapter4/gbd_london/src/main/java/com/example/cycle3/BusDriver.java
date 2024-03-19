package com.example.cycle3;

import java.util.Set;

public interface BusDriver {
  BusStop driveToNextBusStop();

  Set<Rumor> getRumors();
}

