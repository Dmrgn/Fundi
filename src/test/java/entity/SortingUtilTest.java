
package entity;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SortingUtilTest {

    @Test
    void testGetTopNByValueDescending() {
        Map<String, Double> map = Map.of("A", 1.0, "B", 3.0, "C", 2.0);
        Map<String, Double> result = SortingUtil.getTopnByValue(map, 2, true);

        Iterator<Map.Entry<String, Double>> iterator = result.entrySet().iterator();
        assertEquals("B", iterator.next().getKey());
        assertEquals("C", iterator.next().getKey());
    }

    @Test
    void testGetTopNByValueAscending() {
        Map<String, Double> map = Map.of("A", 1.0, "B", 3.0, "C", 2.0);
        Map<String, Double> result = SortingUtil.getTopnByValue(map, 2, false);

        Iterator<Map.Entry<String, Double>> iterator = result.entrySet().iterator();
        assertEquals("A", iterator.next().getKey());
        assertEquals("C", iterator.next().getKey());
    }

    @Test
    void testGetTopNByValueWithTies() {
        Map<String, Double> map = Map.of("A", 2.0, "B", 2.0, "C", 1.0);
        Map<String, Double> result = SortingUtil.getTopnByValue(map, 2, true);

        assertEquals(2, result.size());
        assertTrue(result.containsKey("A") || result.containsKey("B"));
    }

    @Test
    void testGetTopNByValueEmptyMap() {
        Map<String, Double> map = new HashMap<>();
        Map<String, Double> result = SortingUtil.getTopnByValue(map, 3, true);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTopNByValueWithExclusions() {
        Map<String, Double> sortMap = Map.of("A", 5.0, "B", 3.0, "C", 4.0);
        Map<String, Double> valueMap = Map.of("A", 50.0, "B", 30.0, "C", 40.0);
        Set<String> exclude = Set.of("A");

        Map<String, Double> result = SortingUtil.getTopnByValue(sortMap, 2, true, exclude, valueMap);

        assertFalse(result.containsKey("A"));
        assertEquals(2, result.size());
        assertEquals(40.0, result.get("C"));
    }

    @Test
    void testGetTopNByValueWithInsufficientItems() {
        Map<String, Double> sortMap = Map.of("A", 5.0);
        Map<String, Double> valueMap = Map.of("A", 50.0);
        Set<String> exclude = Set.of("B"); // exclude key not present

        Map<String, Double> result = SortingUtil.getTopnByValue(sortMap, 3, true, exclude, valueMap);

        assertEquals(1, result.size());
        assertTrue(result.containsKey("A"));
    }
}