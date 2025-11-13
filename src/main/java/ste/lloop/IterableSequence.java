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
import java.util.Iterator;

class IterableSequence<T> extends Sequence<T> {
    private final Iterable<T> iterable;

    IterableSequence(Iterable<T> iterable) {
        super();
        this.iterable = iterable;
    }

    @Override
    public Sequence<T> to(int to) {
        if ((to < 0) || (to < indexes.from)) {
            throw new IllegalArgumentException("to can not be less than zero or from");
        }
        return super.to(to);
    }

    @Override
    public <R> R loop(BiConsumer<Integer, T> consumer) {
        if (iterable == null) {
            return null; // Do nothing for null iterable
        }

        //
        // TODO: create an optimized version for List
        //
        //
        // Skip the first from elements
        //
        Iterator<T> iterator = iterable.iterator();
        for (int i = 0; i < indexes.from; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            } else {
                return null; // from is out of bounds
            }
        }

        //
        // Loop until to or there are no more elements
        //
        int i = indexes.from;
        Integer to = indexes.to; // Unbox indexes.to once
        try {
            while (iterator.hasNext()) {
                if (to != null && i > to) { // Compare with unboxed 'to'
                    break;
                }
                T element = iterator.next();
                consumer.accept(i, element);
                i++;
            }
        } catch (ReturnValue e) {
            return e.value();
        }
        return null;
    }
}