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

class ForwardOnlySequence<T> extends CollectionSequence<ForwardOnlySequence<T>> {
    private final Iterable<T> iterable;

    ForwardOnlySequence(Iterable<T> iterable) {
        super();
        this.iterable = iterable;
    }

    @Override
    public ForwardOnlySequence<T> step(int step) {
        if (step < 0) {
            throw new IllegalArgumentException("step can not be negative for forward-only collections");
        }
        return super.step(step);
    }

    @Override
    public ForwardOnlySequence<T> from(int from) {
        if (indexes.to != null && from > indexes.to) {
            throw new IllegalArgumentException("from can not be greater than to in a forward-only sequence");
        }
        return super.from(from);
    }

    @Override
    public ForwardOnlySequence<T> to(int to) {
        super.to(to); // This will throw if to < 0
        if (to < indexes.from) {
            throw new IllegalArgumentException("from can not be greater than to in a forward-only sequence");
        }
        return this;
    }

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