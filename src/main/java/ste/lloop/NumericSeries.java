package ste.lloop;

import java.util.function.Consumer;

/**
 * A numeric loop that can be configured with a starting and ending value.
 *
 * <p>This class is not meant to be instantiated directly. Use {@link Loop#on()} to start building a
 * numeric loop.
 */
public class NumericSeries {
    protected int from;
    protected Integer to;

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
     * Executes the given consumer for each value in the loop.
     *
     * @param consumer the consumer to execute for each value
     */
    public void loop(Consumer<Integer> consumer) {
        if (to == null) {
            throw new IllegalStateException("'to' has not been set");
        }

        int i = from;
        int step = (from > to) ? -1 : 1;

        while ((step == 1 && i <= to) || (step == -1 && i >= to)) {
            consumer.accept(i);
            i += step;
        }
    }
}