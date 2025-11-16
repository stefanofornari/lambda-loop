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
public abstract class IndexedSequence<T> extends AbstractSequence<IndexedSequence<T>> {

    /**
     * Constructs a new {@link IndexedSequence} instance.
     */
    protected IndexedSequence() {
    }

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the array provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param <R> the type of the return value
     * @param consumer the consumer to execute for each element
     * @return the value passed to {@link Loop#brk(Object)}, or {@code null} if the loop completes
     *         without a {@code brk}
     */
    public abstract <R> R loop(final BiConsumer<Integer, T> consumer);

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the array provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param <R> the type of the return value
     * @param consumer the consumer to execute for each element
     * @return the value passed to {@link Loop#brk(Object)}, or {@code null} if the loop completes
     *         without a {@code brk}
     */
    public <R> R loop(final Consumer<T> consumer) {
        return loop((index, element) -> consumer.accept(element));
    }
}