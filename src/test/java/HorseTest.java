import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

    @Test
    void Horse_withBadArgs_returnExceptions() {
        Exception nullNameException = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1, 1));
        Exception blankNameException = assertThrows(IllegalArgumentException.class,
                () -> new Horse(" \t", 1, 1));
        Exception negativeSpeedException = assertThrows(IllegalArgumentException.class,
                () -> new Horse("a", -1, 1));
        Exception negativeDistanceException = assertThrows(IllegalArgumentException.class,
                () -> new Horse("a", 1, -1));

        assertAll("Grouped Assertions of Horse constructor",
                () -> assertEquals("Name cannot be null.", nullNameException.getMessage()),
                () -> assertEquals("Name cannot be blank.", blankNameException.getMessage()),
                () -> assertEquals("Speed cannot be negative.", negativeSpeedException.getMessage()),
                () -> assertEquals("Distance cannot be negative.", negativeDistanceException.getMessage()));
    }

    @Test
    void getName_returnNameFromConstructorArg() {
        String name = "test";
        Horse horse = new Horse(name, 1, 1);

        assertEquals(name, horse.getName());
    }

    @Test
    void getSpeed_returnSpeedFromConstructorArg() {
        int speed = 100;
        Horse horse = new Horse("a", speed, 1);

        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistance_returnDistanceFromConstructorArg() {
        int distance = 100;
        Horse horse1 = new Horse("a", 1, distance);
        Horse horse2 = new Horse("b", 1);

        assertAll("Grouped Assertions of Horse instance method getDistance()",
                () -> assertEquals(distance, horse1.getDistance()),
                () -> assertEquals(0, horse2.getDistance()));
    }

    @Test
    void move_whenMocked_thenVerifyCalculatingDistance() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            int speed = 10;
            int distance = 5;
            double random = 0.41;
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            Horse horse = new Horse("a", speed, distance);
            horse.move();

            assertAll("Grouped Assertions of Horse instance method move()",
                    () -> horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9)),
                    () -> assertEquals(distance + speed * random, horse.getDistance()));
        }
    }

}
