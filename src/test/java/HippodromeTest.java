import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

    @Test
    void Hippodrome_withBadArgs_returnException() {
        Exception nullHorsesException = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        Exception emptyHorsesException = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<Horse>()));

        assertAll("Grouped Assertions of Hippodrome constructor",
                () -> assertEquals("Horses cannot be null.", nullHorsesException.getMessage()),
                () -> assertEquals("Horses cannot be empty.", emptyHorsesException.getMessage()));
    }

    @Test
    void getHorses_initWithHorsesList_thenVerifyReturnedList() {
        List<Horse> horses = getHorsesList(30);
        Hippodrome hippodrome = new Hippodrome(horses);

        assertIterableEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move_initWithHorsesList_thenVerifyInvokeMoveForEach() {
        List<Horse> horses = getMockedHorsesList();
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner_initWithHorsesList_thenReturnHorseWithMaxDistance() {
        List<Horse> horses = getHorsesList(50);
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse fastestHorse = horses.stream()
                .max(Comparator.comparingDouble(Horse::getDistance))
                .get();

        assertEquals(fastestHorse, hippodrome.getWinner());
    }

    List<Horse> getHorsesList(int size) {
        return IntStream.rangeClosed(1, size)
                .mapToObj(String::valueOf)
                .map(s -> new Horse(s, Math.random() * 10, Math.random() * 10))
                .collect(Collectors.toList());
    }

    List<Horse> getMockedHorsesList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        return horses;
    }

}
