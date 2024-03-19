package com.example.cycle19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class BusDriversParserImpl implements BusDriversParser {
  private final Map<String, BusStop> nameToBusStop = new HashMap<>();
  private final Map<String, Rumor> nameToRumor = new HashMap<>();

  @Override
  public List<BusDriver> parse(final List<String> busDriverSpecs) {
    return busDriverSpecs.stream().map(this::getBusDriver).toList();
  }

  private BusDriver getBusDriver(final String busDriverSpec) {
    final var parts = busDriverSpec.split(";");
    final var busRouteSpec = parts[0];
    final var rumorsSpec = parts[1];
    final var busStops = getBusStops(List.of(busRouteSpec.split(",")));
    final var rumors = getRumors(List.of(rumorsSpec.split(",")));

    return new BusDriverImpl(
      new CircularBusRoute(busStops),
      rumors
    );
  }

  private List<BusStop> getBusStops(final List<String> busStopNames) {
    return busStopNames.stream()
      .map(busStopName -> {
        nameToBusStop.computeIfAbsent(busStopName, key -> new BusStopImpl());
        return nameToBusStop.get(busStopName);
      })
      .toList();
  }

  private Set<Rumor> getRumors(final List<String> rumorNames) {
    return rumorNames.stream()
      .map(rumorName -> {
        nameToRumor.computeIfAbsent(rumorName, key -> new Rumor());
        return nameToRumor.get(rumorName);
      })
      .collect(Collectors.toSet());
  }
}




