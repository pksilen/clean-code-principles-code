package com.example.cycle10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusDriverImplTests {
  final Rumor rumor1 = new Rumor();
  final Rumor rumor2 = new Rumor();
  final Rumor rumor3 = new Rumor();
  final Rumor rumor4 = new Rumor();

  @Mock
  BusRoute busRouteMock;

  @Mock
  BusStop busStopMock1;

  @Mock
  BusStop busStopMock2;

  @BeforeEach
  void setUp() {
    when(busRouteMock.getFirstBusStop()).thenReturn(busStopMock1);
  }

  @Test
  void testDriveToNextBusStop() {
    // GIVEN
    when(busRouteMock.getNextBusStop(busStopMock1)).thenReturn(busStopMock2);
    final var busDriver = new BusDriverImpl(busRouteMock, new HashSet<>());

    // WHEN
    busDriver.driveToNextBusStop();

    // THEN
    verify(busStopMock1).remove(busDriver);
    verify(busStopMock2).add(busDriver);
    assertSame(busDriver.getCurrentBusStop(), busStopMock2);
  }

  @Test
  void testGetRumors() {
    // GIVEN
    final var initialRumors = Set.of(rumor1, rumor2);
    final var busDriver = new BusDriverImpl(busRouteMock, initialRumors);

    // WHEN
    final var rumors = busDriver.getRumors();

    // THEN
    assertEquals(rumors, initialRumors);
  }

  @Test
  void testSetRumors() {
    // GIVEN
    final var initialRumors = Set.of(rumor1, rumor2);
    final var newRumors = Set.of(rumor3, rumor4);
    final var busDriver = new BusDriverImpl(busRouteMock, initialRumors);

    // WHEN
    busDriver.setRumors(newRumors);

    // THEN
    assertEquals(busDriver.getRumors(), newRumors);
  }
}
