package com.example.cycle13;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusStopImplTests {
  final Rumor rumor1 = new Rumor();
  final Rumor rumor2 = new Rumor();
  final Rumor rumor3 = new Rumor();
  final Set<Rumor> allRumors = Set.of(rumor1, rumor2, rumor3);

  @Mock
  BusDriver busDriverMock1;

  @Mock
  BusDriver busDriverMock2;

  @Mock
  BusDriver busDriverMock3;

  @Test
  void testShareRumorsWithDrivers() {
    // GIVEN
    final var busDrivers = List.of(busDriverMock1, busDriverMock2, busDriverMock3);
    when(busDriverMock1.getRumors()).thenReturn(Set.of(rumor1, rumor2));
    when(busDriverMock2.getRumors()).thenReturn(Set.of(rumor2));
    when(busDriverMock3.getRumors()).thenReturn(Set.of(rumor2, rumor3));
    final var busStop = new BusStopImpl();
    busDrivers.forEach(busStop::add);

    // WHEN
    busStop.shareRumorsWithDrivers();

    // THEN
    assertAllRumorsAreSet(busDrivers);
  }

  @Test
  void testShareRumorsWithDrivers_whenDriverIsRemoved() {
    // GIVEN
    final var busStop = new BusStopImpl();
    busStop.add(busDriverMock1);

    // WHEN
    busStop.remove(busDriverMock1);
    busStop.shareRumorsWithDrivers();

    // THEN
    verify(busDriverMock1, never()).setRumors(any());
  }

  private void assertAllRumorsAreSet(final List<BusDriver> busDrivers) {
    for (BusDriver busDriverMock : busDrivers) {
      verify(busDriverMock).setRumors(allRumors);
    }
  }
}

