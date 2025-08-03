package entity;

import java.util.*;
import java.util.stream.Collectors;

public class SortingUtil {
    private SortingUtil() {

    }

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
