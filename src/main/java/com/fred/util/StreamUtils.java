package com.fred.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamUtils {

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object>  keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }



    public static <T> Predicate<T> distinctByKeys(Function<? super T, Object>...  keyExtractors) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> {
            String key = "";
            for (Function<? super T, Object> keyExtractor : keyExtractors) {
                key += keyExtractor.apply(t).toString();
            }
            return seen.putIfAbsent(key, Boolean.TRUE) == null;
        };
    }
}
