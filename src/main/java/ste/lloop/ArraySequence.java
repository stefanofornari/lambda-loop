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

import java.util.function.BiConsumer;

/**
 * A sequence that loops over an array.
 * @param <T> the type of the elements in the array
 */
public class ArraySequence<T> extends IndexedSequence<T> {
    private final T[] array;

    /**
     * Creates a new sequence for the given array.
     * @param array the array to loop over
     */
    public ArraySequence(T[] array) {
        super();
        this.array = array;
    }

    @Override
    public <R> R loop(final BiConsumer<Integer, T> consumer) {
        if (array == null || array.length == 0) {
            return null; // Do nothing for null or empty array
        }

        adjustIndexes(array.length);

        return indexes.loop((i) -> consumer.accept(i, array[i]));
    }
}