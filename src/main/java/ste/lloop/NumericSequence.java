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

import java.util.function.Consumer;

/**
 * A numeric loop that can be configured with a starting and ending value.
 *
 * <p>This class is not meant to be instantiated directly. Use {@link Loop#on()} to start building a
 * numeric loop.
 */
public class NumericSequence {
    /**
     * The starting value of the loop (inclusive).
     */
    protected int from;

    /**
     * The ending value of the loop (inclusive).
     */
    protected Integer to;

    /**
     * The step of the loop.
     */
    protected int step = 1;

    /**
     * Constructs a new {@link NumericSequence} instance.
     */
    public NumericSequence() {}

    /**
     * Sets the starting value of the loop (inclusive).
     *
     * @param from the starting value
     * @return this {@link NumericSequence} instance
     */
    public NumericSequence from(int from) {
        this.from = from;
        return this;
    }

    /**
     * Sets the ending value of the loop (inclusive).
     *
     * @param to the ending value
     * @return this {@link NumericSequence} instance
     * @throws IllegalArgumentException if a negative step is set
     */
    public NumericSequence to(int to) {
        if (step < 0) {
            throw new IllegalArgumentException("a negative step is not allowed when to is set");
        }
        this.to = to;
        return this;
    }

    /**
     * Sets the step of the loop.
     * <p>
     * A negative step is allowed only if {@code to} is not set, in which case the loop will go
     * backwards.
     * <p>
     * The absolute value of the step is used as the increment. If the step is zero, the loop will
     * not execute.
     *
     * @param step the step value
     * @return this {@link NumericSequence} instance
     * @throws IllegalArgumentException if a negative step is set when {@code to} is also set
     */
    public NumericSequence step(int step) {
        if (this.to != null && step < 0) {
            throw new IllegalArgumentException("a negative step is not allowed when to is set");
        }
        this.step = step;
        return this;
    }

    /**
     * Executes the given consumer for each value in the loop.
     * <p>
     * If {@code to} is set (finite loop) and {@code from} and {@code to} are equal, the loop will
     * not execute.
     * <p>
     * If {@code to} is not set (infinite loop), the loop will continue indefinitely until
     * {@link Loop#brk(Object)} is called.
     *
     * @param <R> the type of the return value
     * @param consumer the consumer to execute for each value
     * @return the value passed to {@link Loop#brk(Object)}, or {@code null} if the loop completes
     *         without a {@code brk}
     */
    public <R> R loop(Consumer<Integer> consumer) {
        if (step == 0) {
            return null;
        }

        Integer actualTo = to, actualStep = step;

        if (to == null) {
            //
            // endless loop
            //
            actualTo = (step > 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            actualStep = Math.abs(step);
        }

        try {
            final boolean forward = from < actualTo;
            final int increment = (forward) ? actualStep : -actualStep;

            int i = from;
            while ((forward && i <= actualTo) || (!forward && i >= actualTo)) {
                consumer.accept(i); i += increment;
            }
        } catch (ReturnValue e) {
            return e.value();
        }

        return null;
    }
}