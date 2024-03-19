package com.example.cycle5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GossipingBusDriversTests {
  final Rumor rumor1 = new Rumor();
  final Rumor rumor2 = new Rumor();
  final Set<Rumor> allRumors = Set.of(rumor1, rumor2);

  @Mock
  BusDriver busDriverMock1;

  @Mock
  BusDriver busDriverMock2;

  @Mock
  BusStop busStopMock1;

  @Mock
  BusStop busStopMock2;

  @Mock
  BusStop busStopMock3;

  @Test
  void testDriveUntilAllRumorsShared_afterOneStop() {
    // GIVEN
    when(busDriverMock1.driveToNextBusStop()).thenReturn(busStopMock1);
    when(busDriverMock2.driveToNextBusStop()).thenReturn(busStopMock1);
    when(busDriverMock1.getRumors()).thenReturn(allRumors);
    when(busDriverMock2.getRumors()).thenReturn(allRumors);

    final var gossipingBusDrivers = new GossipingBusDrivers(Arrays.asList(busDriverMock1, busDriverMock2));

    // WHEN
    final var allRumorsWereShared = gossipingBusDrivers.driveUntilAllRumorsShared(100);

    // THEN
    assertTrue(allRumorsWereShared);
    verify(busStopMock1, times(1)).shareRumorsWithDrivers();
  }

  @Test
  void testDriveUntilAllRumorsShared_afterTwoStops() {
    // GIVEN
    final var busStopMocks = List.of(busStopMock1, busStopMock2, busStopMock3);

    when(busDriverMock1.driveToNextBusStop())
      .thenReturn(busStopMock1)
      .thenReturn(busStopMock3);

    when(busDriverMock2.driveToNextBusStop())
      .thenReturn(busStopMock2)
      .thenReturn(busStopMock3);

    when(busDriverMock1.getRumors())
      .thenReturn(Set.of(rumor1))
      .thenReturn(Set.of(rumor1))
      .thenReturn(allRumors);

    when(busDriverMock2.getRumors())
      .thenReturn(Set.of(rumor2))
      .thenReturn(allRumors);

    final var gossipingBusDrivers = new GossipingBusDrivers(List.of(busDriverMock1, busDriverMock2));

    // WHEN
    final var allRumorsWereShared = gossipingBusDrivers.driveUntilAllRumorsShared(100);

    // THEN
    assertTrue(allRumorsWereShared);

    for (final var busStopMock : busStopMocks) {
      verify(busStopMock, times(1)).shareRumorsWithDrivers();
    }
  }

  @Test
  void testDriveUntilAllRumorsShared_whenRumorsAreNotShared() {
    // GIVEN
    final var busStopMocks = List.of(busStopMock1, busStopMock2);
    when(busDriverMock1.driveToNextBusStop()).thenReturn(busStopMock1);
    when(busDriverMock2.driveToNextBusStop()).thenReturn(busStopMock2);
    when(busDriverMock1.getRumors()).thenReturn(Set.of(rumor1));
    when(busDriverMock2.getRumors()).thenReturn(Set.of(rumor2));

    final var gossipingBusDrivers = new GossipingBusDrivers(Arrays.asList(busDriverMock1, busDriverMock2));
    final int maxDrivenStopCount = 2;

    // WHEN
    final var allRumorsWereShared = gossipingBusDrivers.driveUntilAllRumorsShared(maxDrivenStopCount);

    // THEN
    assertFalse(allRumorsWereShared);

    for (final var busStopMock : busStopMocks) {
      verify(busStopMock, times(2)).shareRumorsWithDrivers();
    }
  }
}


