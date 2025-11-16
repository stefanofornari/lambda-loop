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

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import static ste.lloop.Loop._break_;

/**
 * A sequence that loops over an iterable and can only go forward.
 * @param <T> the type of the elements in the iterable
 */
public class ForwardOnlySequence<T> extends AbstractSequence<ForwardOnlySequence<T>> {
    private final Iterable<T> iterable;

    /**
     * Creates a new sequence for the given iterable.
     * @param iterable the iterable to loop over
     */
    public ForwardOnlySequence(Iterable<T> iterable) {
        super();
        this.iterable = iterable;
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
     * <p>If the iterable provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param <R> the type of the return value
     * @param consumer the consumer to execute for each element
     * @return the value passed to {@link Loop#brk(Object)}, or {@code null} if the loop completes
     *         without a {@code brk}
     */
    public <R> R loop(BiConsumer<Integer, T> consumer) {
        if (iterable == null) {
            return null;
        }

        if (indexes.step == 0) {
            return null;
        }

        final Iterator<T> iterator = iterable.iterator();
        final AtomicInteger currentIndex = new AtomicInteger(0); // To track iterator's current position

        // Use indexes.loop() to generate the sequence of target indices
        return indexes.loop(targetIndex -> {
            T element = null;
            // Advance the iterator to the position of the targetIndex
            while (currentIndex.get() <= targetIndex) {
                if (!iterator.hasNext()) {
                    // We ran out of elements before reaching the targetIndex
                    // This can happen if 'to' is set beyond the actual size
                    // or if 'from' is beyond the actual size.
                    // return; // Stop the loop
                    _break_(targetIndex);
                }
                element = iterator.next();
                currentIndex.incrementAndGet();
            }
            // If we reached here, element should be the one at targetIndex
            consumer.accept(targetIndex, element);
        });
    }
}