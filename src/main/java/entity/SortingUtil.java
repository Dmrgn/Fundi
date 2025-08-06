package entity;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class containing functions for sorting maps with key based order.
 */
public final class SortingUtil {
    private SortingUtil() {

    }

    /**
     * Return a Linked HashMap of the top n elements of the given map sorted by value in the specified order.
     * @param map The map to sort
     * @param num The number of entries to keep
     * @param descending The sort order
     * @param <K> The key Type
     * @param <V> The value Type, must be numeric
     * @return The Linked HashMap containing the top n elements
     */
    public static <K, V extends Number> Map<K, V> getTopnByValue(Map<K, V> map, int num, boolean descending) {
        Comparator<Map.Entry<K, V>> comparator = Comparator.comparingDouble(entry -> entry.getValue().doubleValue());
        if (descending) {
            comparator = comparator.reversed();
        }

        return map.entrySet().stream()
                .sorted(comparator)
                .limit(num)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (entry1, entry2) -> entry1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Return a Linked HashMap of the top n elements of the given map sorted by a given key in the specified order
     * excluding a set of keys.
     * @param sortMap The map to sort
     * @param num The number of entries to keep
     * @param descending The sort order
     * @param excludeKeys The keys to exclude from the sort
     * @param valueMap The sorting key
     * @param <K> The key Type
     * @param <V> The value Type, must be numeric
     * @return The Linked HashMap containing the top n elements
     */
    public static <K, V extends Number> Map<K, V> getTopnByValue(Map<K, V> sortMap, int num, boolean descending,
                                                                 Collection<K> excludeKeys, Map<K, V> valueMap) {
        Comparator<Map.Entry<K, V>> comparator = Comparator.comparingDouble(entry -> entry.getValue().doubleValue());
        if (descending) {
            comparator = comparator.reversed();
        }

        return sortMap.entrySet().stream()
                .filter(entry -> !excludeKeys.contains(entry.getKey()))
                .sorted(comparator)
                .limit(num)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> valueMap.get(entry.getKey()),
                        (entry1, entry2) -> entry1,
                        LinkedHashMap::new
                ));
    }
}
