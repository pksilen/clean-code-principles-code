package com.example.cycle5;

import java.util.Set;

public class BusDriverImpl implements BusDriver {
  private Set<Rumor> rumors;

  public BusDriverImpl(final Set<Rumor> rumors) {
    this.rumors = Set.copyOf(rumors);
  }

  @Override
  public BusStop driveToNextBusStop() {
    return null;
  }

  @Override
  public Set<Rumor> getRumors() {
    return Set.copyOf(rumors);
  }

  public void setRumors(final Set<Rumor> rumors) {
    this.rumors = Set.copyOf(rumors);
  }
}


