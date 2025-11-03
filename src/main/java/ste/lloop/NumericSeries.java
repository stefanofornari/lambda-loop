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
public class NumericSeries {
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
     * Constructs a new {@link NumericSeries} instance.
     */
    public NumericSeries() {}

    /**
     * Sets the starting value of the loop (inclusive).
     *
     * @param from the starting value
     * @return this {@link NumericSeries} instance
     */
    public NumericSeries from(int from) {
        this.from = from;
        return this;
    }

    /**
     * Sets the ending value of the loop (inclusive).
     *
     * @param to the ending value
     * @return this {@link NumericSeries} instance
     */
    public NumericSeries to(int to) {
        this.to = to;
        return this;
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
     * @return this {@link NumericSeries} instance
     */
    public NumericSeries step(int step) {
        this.step = step;
        return this;
    }

    /**
     * Executes the given consumer for each value in the loop.
     * If {@code from} and {@code to} are equal, the loop will not execute.
     *
     * @param consumer the consumer to execute for each value
     */
    public void loop(Consumer<Integer> consumer) {
        if (to == null) {
            throw new IllegalStateException("'to' has not been set");
        }

        if (from == to) {
            return;
        }

        if (step == 0) {
            return;
        }

        int start = from;
        int end = to;

        if (step < 0) {
            start = to;
            end = from;
        }

        final boolean isForward = end >= start;
        final int increment = isForward ? Math.abs(step) : -Math.abs(step);

        int i = start;

        while ((isForward && i <= end) || (!isForward && i >= end)) {
            consumer.accept(i);
            i += increment;
        }
    }
}