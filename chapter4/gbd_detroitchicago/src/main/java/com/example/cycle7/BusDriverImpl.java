package com.example.cycle7;

import java.util.Set;

public class BusDriverImpl implements BusDriver {
  private Set<Rumor> rumors;

  public BusDriverImpl(final BusRoute busRoute, final Set<Rumor> rumors) {
    this.rumors = Set.copyOf(rumors);
  }

  @Override
  public Set<Rumor> getRumors() {
    return rumors;
  }

  @Override
  public void setRumors(final Set<Rumor> rumors) {
    this.rumors = Set.copyOf(rumors);
  }
}

