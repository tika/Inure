package one.tika.inure.util;

import java.util.*;
import java.util.stream.Stream;

public class CollectionUtil {
    public static <K, V> TreeMap<K, V> treeMap(K key1, V value1, Object... objects) {
        TreeMap<K, V> ret = new TreeMap<>();

        ret.put(key1, value1);

        Iterator<Object> iter = Arrays.asList(objects).iterator();
        while (iter.hasNext()) {
            K key = (K) iter.next();
            V value = (V) iter.next();
            ret.put(key, value);
        }

        return ret;
    }


    public static <K, V> Map<K, V> map(K key1, V value1, Object... objects) {
        Map<K, V> ret = new HashMap<>();

        ret.put(key1, value1);

        Iterator<Object> iter = Arrays.asList(objects).iterator();
        while (iter.hasNext()) {
            K key = (K) iter.next();
            V value = (V) iter.next();
            ret.put(key, value);
        }

        return ret;
    }

    // Requires Java 8
    public static <E> E getWeightedRandom(Stream<Map.Entry<E, Double>> weights, Random random) {
        return weights
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), -Math.log(random.nextDouble()) / e.getValue()))
                .min(Map.Entry.comparingByValue())
                .orElseThrow(IllegalArgumentException::new).getKey();
    }

}
