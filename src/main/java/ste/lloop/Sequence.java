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
import java.util.function.Consumer;

/**
 * A loop over an array that can be configured with a starting and ending index.
 *
 * <p>This class is not meant to be instantiated directly. Use {@link Loop#on(Object[])} to start
 * building an array loop.
 *
 * <p>The loop can be configured with a {@code from} index, a {@code to} index, and a {@code step} value.
 * If {@code from} and {@code to} are equal, the loop will not execute.
 * The loop can be executed by calling one of the two {@code loop} methods: one that provides both the index and the element, and one that provides only the element.
 * </p>
 *
 * @param <T> the type of the elements in the array
 */
public class Sequence<T> {
    private final T[] array;
    private final NumericSeries indexes;

    /**
     * Constructs an {@code ArraySeries} instance.
     *
     * @param array the array to loop over
     */
    Sequence(T[] array) {
        this.array = array;
        this.indexes = new NumericSeries();
    }

    /**
     * Sets the step of the loop. The sign of the step determines the direction of the loop.
     * <p>
     * If the step is positive, the loop will go from {@code from} to {@code to}.
     * If the step is negative, the loop will go from {@code to} to {@code from}.
     * <p>
     * The absolute value of the step is used as the increment.
     * If the step is zero, the loop will not execute.
     *
     * @param step the step value
     * @return this {@link Sequence} instance
     */
    public Sequence<T> step(final int step) {
        indexes.step(step); return this;
    }

    /**
     * Sets the starting index of the loop (inclusive).
     *
     * @param from the starting index
     * @return this {@link Sequence} instance
     * @throws IndexOutOfBoundsException if the 'from' value is less than zero
     */
    public Sequence<T> from(final int from) {
        if (from < 0) {
            throw new IndexOutOfBoundsException("The 'from' value cannot be less than zero.");
        }
        indexes.from(from); return this;
    }

    /**
     * Sets the ending index of the loop (inclusive).
     *
     * @param to the ending index
     * @return this {@link Sequence} instance
     */
    public Sequence<T> to(final int to) {
        indexes.to(to); return this;
    }

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the array provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param consumer the consumer to execute for each element
     */
    public void loop(final BiConsumer<Integer, T> consumer) {
        if (array == null || array.length == 0) {
            return; // Do nothing for null or empty array
        }

        if (indexes.to == null) {
            indexes.to = array.length-1;
        } else {
           if (indexes.to > array.length-1) {
               indexes.to = array.length-1;
           }
        }
        indexes.loop((i) -> consumer.accept(i, array[i]));
    }

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the array provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param consumer the consumer to execute for each element
     */
    public void loop(final Consumer<T> consumer) {
        loop((index, element) -> consumer.accept(element));
    }
}