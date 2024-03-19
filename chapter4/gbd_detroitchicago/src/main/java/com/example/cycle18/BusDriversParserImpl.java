package com.example.cycle18;

import java.util.*;

public class BusDriversParserImpl implements BusDriversParser {
  private final Map<String, BusStop> nameToBusStop = new HashMap<>();
  private final Map<String, Rumor> nameToRumor = new HashMap<>();

  @Override
  public List<BusDriver> parse(List<String> busDriverSpecs) {
    return busDriverSpecs.stream().map(this::getBusDriver).toList();
  }

  private BusDriver getBusDriver(String busDriverSpec) {
    final var parts = busDriverSpec.split(";");
    final var busRouteSpec = parts[0];
    final var rumorName = parts[1];

    final var busStops = Arrays.stream(busRouteSpec.split(","))
      .map(busStopName -> {
        nameToBusStop.computeIfAbsent(busStopName, key -> new BusStopImpl());
        return nameToBusStop.get(busStopName);
      })
      .toList();

    nameToRumor.computeIfAbsent(rumorName, key -> new Rumor());

    return new BusDriverImpl(
      new CircularBusRoute(busStops),
      Set.of(nameToRumor.get(rumorName))
    );
  }
}




