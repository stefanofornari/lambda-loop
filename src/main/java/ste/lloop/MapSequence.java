/*
 * Copyright 2025 the original author or authors from the λLoop project (https://lambda-loop.github.io/)..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ste.lloop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import static ste.lloop.Loop._break_;

/**
 * A sequence that loops over a map.
 * @param <K> the type of the keys in the map
 * @param <V> the type of the values in the map
 */
public class MapSequence<K, V> extends AbstractSequence<MapSequence<K, V>, Map.Entry<K, V>> {
    private final Map<K, V> map;

    /**
     * Creates a new sequence for the given map.
     * @param map the map to loop over
     */
    public MapSequence(Map<K, V> map) {
        super();
        this.map = map;
    }

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the map provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param <R> the type of the return value
     * @param consumer the consumer to execute for each element
     * @return the value passed to {@link Loop#brk(Object)}, or {@code null} if the loop completes
     *         without a {@code brk}
     */
    public <R> R loop(MapLoopConsumer<K, V> consumer) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        final List<Map.Entry<K, V>> entries = new ArrayList<>(map.entrySet());
        final int size = entries.size();

        // If 'to' is not set, default it to the last index.
        if (indexes.to == null) {
            indexes.to(size - 1);
        }

        // Eagerly cap the 'to' value to the last valid index.
        if (indexes.to >= size) {
            indexes.to(size - 1);
        }

        // Now we can delegate to NumericSequence, confident that it will not
        // generate an index that is out of the list's upper bounds.
        return indexes.loop(index -> {
            Map.Entry<K, V> entry = entries.get(index);
            consumer.accept(index, entry.getKey(), entry.getValue());
        });
    }

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the map provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param <R> the type of the return value
     * @param consumer the consumer to execute for each element
     * @return the value passed to {@link Loop#brk(Object)}, or {@code null} if the loop completes
     *         without a {@code brk}
     */
    public <R> R loop(BiConsumer<K, V> consumer) {
        return loop((index, key, value) -> consumer.accept(key, value));
    }

}

