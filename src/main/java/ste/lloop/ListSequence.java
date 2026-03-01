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

import java.util.List;
import java.util.function.BiConsumer;

/**
 * A sequence that loops over a list.
 * @param <T> the type of the elements in the list
 */
public class ListSequence<T> extends IndexedSequence<T> {
    private final List<T> list;

    /**
     * Creates a new sequence for the given list.
     * @param list the list to loop over
     */
    public ListSequence(List<T> list) {
        super();
        this.list = list;
    }

    @Override
    public <R> R loop(BiConsumer<Integer, T> consumer) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        adjustIndexes(list.size());

        return indexes.loop(index -> {
            consumer.accept(index, list.get(index));
        });
    }
}
