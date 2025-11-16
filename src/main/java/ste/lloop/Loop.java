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

/**
 * Provides a fluent API for creating loops.
 *
 * <p>This class is the main entry point for creating loops. Use one of the static {@code on} methods
 * to start building a loop.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Numeric loop from 0 to 10 (inclusive)
 * Loop.on().from(0).to(10).loop(i -> {
 *     // do something with i
 * });
 *
 * // Loop over an array of strings
 * Loop.on("a", "b", "c").loop(element -> {
 *    // do something with element
 * });
 *
 * // Loop over an array of strings, from index 1 up to (and including) index 3
 * Loop.on(new String[]{"a", "b", "c", "d"}).from(1).to(3).loop((index, element) -> {
 *    // do something with index and element
 * });
 *
 * // Numeric loop from 0 to 10, with a step of 2
 * Loop.on().from(0).to(10).step(2).loop(i -> {
 *     // i will be 0, 2, 4, 6, 8, 10
 * });
 *
 * // Numeric loop from 0 to 10, with a negative step of -2 (inverts direction)
 * Loop.on().from(0).to(10).step(-2).loop(i -> {
 *     // i will be 10, 8, 6, 4, 2, 0
 * });
 * }</pre>
 */
public final class Loop {

    private Loop() {}

    /**
     * Creates a new numeric loop.
     *
     * @return a new {@link NumericSequence} instance
     */
    public static NumericSequence on() {
        return new NumericSequence();
    }

    /**
     * Creates a new loop over the given items. This method supports both varargs and passing an array directly.
     *
     * @param items the items to loop over (can be varargs or an array)
     * @param <T> the type of the items
     * @return a new {@link Sequence} instance
     */
    @SafeVarargs
    public static <T> ArraySequence<T> on(T... items) {
        return new ArraySequence<>(items);
    }

    /**
     * Creates a new loop over the given list.
     *
     * @param <T> the type of the items in the list
     * @param list the list to loop over
     * @return a new {@link ListSequence} instance
     */
    public static <T> ListSequence<T> on(List<T> list) {
        return new ListSequence<>(list);
    }

    /**
     * Creates a new loop over the given iterable.
     *
     * @param <T> the type of the items in the iterable
     * @param iterable the iterable to loop over
     * @return a new {@link ForwardOnlySequence} instance
     */
    public static <T> ForwardOnlySequence<T> on(Iterable<T> iterable) {
        return new ForwardOnlySequence<>(iterable);
    }

    /**
     * Throws a {@link ReturnValue} exception with the given value.
     * This method is intended to be used inside a loop to break out of it
     * and return a value.
     *
     * @param value the value to return
     * @throws ReturnValue with the given value
     */
    public static void brk(Object value) {
        throw new ReturnValue(value);
    }

    /**
     * Same as brk() - which version do you prefer? please let me know, we will
     * remove the less voted one.
     *
     * @param value the value to return
     * @throws ReturnValue with the given value
     */
    public static void _break_(Object value) {
        throw new ReturnValue(value);
    }
}
