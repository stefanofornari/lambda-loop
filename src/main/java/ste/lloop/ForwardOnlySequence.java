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


/**
 * A sequence that loops only forwards
 * @param <T> the type of the elements in the sequence
 */
public abstract class ForwardOnlySequence<T> extends AbstractSequence<ForwardOnlySequence<T>, T> {
    /**
     * Constructs a new {@link ForwardOnlySequence} instance.
     */
    protected ForwardOnlySequence() {
    }
    @Override
    public ForwardOnlySequence<T> to(final int to) {
        if (to < indexes.from) {
            throw new IllegalArgumentException("to can not be negative or smaller than from for forward-only sequences");
        }
        return super.to(to);
    }

    @Override
    public ForwardOnlySequence<T> from(final int from) {
        if ((from < 0) || ((indexes.to != null) && (from > indexes.to))) {
            throw new IllegalArgumentException("from can not be negative or greater than to for forward-only sequences");
        }
        return super.from(from);
    }

    @Override
    public ForwardOnlySequence<T> step(final int step) {
        if (step < 0) {
            throw new IllegalArgumentException("step can never be negative for forward-only sequences");
        }
        return super.step(step);
    }

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the array provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param <R> the type of the return value
     * @param consumer the consumer to execute for each element
     * @return the value passed to {@link ste.lloop.Loop#_break_(Object...)}, or {@code null} if the loop completes
     *         without a {@code _break_}
     */
    public abstract <R> R loop(final java.util.function.BiConsumer<Integer, T> consumer);

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the array provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param <R> the type of the return value
     * @param consumer the consumer to execute for each element
     * @return the value passed to {@link ste.lloop.Loop#_break_(Object...)}, or {@code null} if the loop completes
     *         without a {@code _break_}
     */
    public <R> R loop(final java.util.function.Consumer<T> consumer) {
        return loop((index, element) -> consumer.accept(element));
    }
}