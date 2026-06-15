package mfit.java.hw1;
import mfti.java.hw1.Flow;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlowTest {
    @Test
    void testCreationFlowFromList() {
        List<Integer> expectedList = List.of(-2, 4, 6);
        Flow<Integer> flow1 = new Flow<Integer>(expectedList);
        List<Integer> resList = flow1.collect(() -> new ArrayList<Integer>(),
                (list, elem) -> list.add(elem));
        assertEquals(expectedList, resList);

    }

    @Test
    void testCreationFlowFromVarargs() {
        Flow<Integer> flow1 = new Flow<Integer>(-2,4,6);
        List<Integer> expectedList = List.of(-2, 4, 6);
        List<Integer> resList = flow1.collect(() -> new ArrayList<Integer>(),
                (list, elem) -> list.add(elem));
        assertEquals(expectedList, resList);

    }

    @Test
    void testCreationFlowFromBorderValsAndStepFunction() {
        Flow<Integer> flow1 = new Flow<Integer>(2, (el) -> el + 2, 8);
        List<Integer> expectedList = List.of(2, 4, 6);
        List<Integer> resList = flow1.collect(() -> new ArrayList<Integer>(),
                (list, elem) -> list.add(elem));
        assertEquals(expectedList, resList);

    }

    @Test
    void testMapFilterReduce() {
        Flow<Integer> flow1 = new Flow<Integer>(-2,4,6);
        Integer res1 = flow1
                .map(elem -> elem * 2)
                .filter(elem -> elem >= 0)
                .reduce(Integer::sum);
        Integer expectedVal = 20;
        assertEquals(expectedVal, res1);

    }
}
