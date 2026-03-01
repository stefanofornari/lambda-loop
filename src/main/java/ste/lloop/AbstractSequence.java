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
 * A base class for sequences that can be iterated over with indexes.
 *
 * @param <S> the type of the sequence
 * @param <T> the type of the elements in the sequence
 */
public abstract class AbstractSequence<S extends AbstractSequence<S, T>, T> {
    /**
     * The numeric series that controls the looping.
     */
    protected final NumericSequence indexes;

    /**
     * Creates a new collection sequence.
     */
    public AbstractSequence() {
        this.indexes = new NumericSequence();
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
     * @return this instance
     */
    public S step(final int step) {
        indexes.step(step);
        return (S) this;
    }

    /**
     * Sets the starting index of the loop (inclusive).
     *
     * @param from the starting index
     * @return this instance
     * @throws IllegalArgumentException if 'from' is less than zero
     */
    public S from(final int from) {
        if (from < 0) {
            throw new IllegalArgumentException("from can not be less than zero");
        }
        indexes.from(from);
        return (S) this;
    }

    /**
     * Sets the ending index of the loop (inclusive).
     *
     * @param to the ending index
     * @return this instance
     * @throws IllegalArgumentException if 'to' is less than zero
     */
    public S to(final int to) {
        if (to < 0) {
            throw new IllegalArgumentException("to can not be less than zero");
        }
        indexes.to(to);
        return (S) this;
    }

    protected void adjustIndexes(final int size) {
        //
        // If to is not provoded, we set it taking into account we can not set
        // to and a negative step (to and positive step is valid instead).
        // If to is not null, to/from/step are already properly set.
        //
        if (indexes.to == null) {
            if (indexes.step >= 0) {
                indexes.to(size - 1);
            } else {
                indexes.step(-indexes.step);
                indexes.from(size - 1);
                indexes.to(0);
            }
        } else if (indexes.to > size-1) {
            indexes.to(size - 1);
        }
    }
}