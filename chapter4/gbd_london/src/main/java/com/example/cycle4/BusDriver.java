package com.example.cycle4;

import java.util.Set;

public interface BusDriver {
  BusStop driveToNextBusStop();

  Set<Rumor> getRumors();
}

