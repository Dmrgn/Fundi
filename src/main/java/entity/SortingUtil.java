package entity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class containing functions for sorting maps with key based order
 */
public class SortingUtil {
    private SortingUtil() {

    }

    /**
     * Return a Linked HashMap of the top n elements of the given map sorted by value in the specified order
     * @param map The map to sort
     * @param n The number of entries to keep
     * @param descending The sort order
     * @return The Linked HashMap containing the top n elements
     * @param <K> The key Type
     * @param <V> The value Type, must be numeric
     */
    public static <K, V extends Number> Map<K, V> getTopNByValue(Map<K, V> map, int n, boolean descending) {
        Comparator<Map.Entry<K, V>> comparator = Comparator.comparingDouble(e -> e.getValue().doubleValue());
        if (descending) {
            comparator = comparator.reversed();
        }

        return map.entrySet().stream()
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Return a Linked HashMap of the top n elements of the given map sorted by a given key in the specified order
     * excluding a set of keys
     * @param sortMap The map to sort
     * @param n The number of entries to keep
     * @param descending The sort order
     * @param excludeKeys The keys to exclude from the sort
     * @param valueMap The sorting key
     * @return The Linked HashMap containing the top n elements
     * @param <K> The key Type
     * @param <V> The value Type, must be numeric
     */
    public static <K, V extends Number> Map<K, V> getTopNByValue(Map<K, V> sortMap, int n, boolean descending, Collection<K> excludeKeys, Map<K, V> valueMap) {
        Comparator<Map.Entry<K, V>> comparator = Comparator.comparingDouble(e -> e.getValue().doubleValue());
        if (descending) {
            comparator = comparator.reversed();
        }

        return sortMap.entrySet().stream()
                .filter(entry -> !excludeKeys.contains(entry.getKey()))
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> valueMap.get(e.getKey()),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
