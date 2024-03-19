package com.example.cycle19;

import java.util.List;


public class Main {
  public static void main(final String[] args) {
    if (args.length < 3) {
      System.err.println("Usage: Main <maxDrivenStopCount> <busDriverSpec1> <busDriverSpec2> ..."); //NOSONAR
      System.exit(1);
    }

    try {
      final var maxDrivenStopCount = new MaxDrivenStopCountParserImpl().parse(args[0]);
      final var busDriverSpecs = List.of(args).subList(1, args.length);
      final var busDrivers = new BusDriversParserImpl().parse(busDriverSpecs);

      boolean allRumorsWereShared = new GossipingBusDrivers(busDrivers)
        .driveUntilAllRumorsShared(maxDrivenStopCount);

      System.exit(allRumorsWereShared ? 0 : 1);

    } catch (final NumberFormatException exception) {
      System.err.println("Invalid max driven stop count: " + args[0]); //NOSONAR
      System.exit(1);
    } catch (final Exception exception) {
      System.err.println("Exception: " + exception.getMessage()); //NOSONAR
      System.exit(1);
    }
  }
}
