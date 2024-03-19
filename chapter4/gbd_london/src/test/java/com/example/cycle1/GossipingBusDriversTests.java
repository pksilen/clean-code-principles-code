package com.example.cycle1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

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

  @Test
  void testDriveUntilAllRumorsShared_afterOneStop() {
    // GIVEN
    when(busDriverMock1.driveToNextBusStop()).thenReturn(busStopMock1);
    when(busDriverMock2.driveToNextBusStop()).thenReturn(busStopMock1);
    when(busDriverMock1.getRumors()).thenReturn(allRumors);
    when(busDriverMock2.getRumors()).thenReturn(allRumors);

    final var gossipingBusDrivers = new GossipingBusDrivers(List.of(busDriverMock1, busDriverMock2));

    // WHEN
    final var allRumorsWereShared = gossipingBusDrivers.driveUntilAllRumorsShared();

    // THEN
    assertTrue(allRumorsWereShared);
    verify(busStopMock1, times(2)).shareRumorsWithDrivers();
  }
}

