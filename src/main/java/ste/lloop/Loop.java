package ste.lloop;

import java.util.function.Consumer;

/**
 * Provides a fluent API for creating loops.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Loop.from(0).to(10).loop(i -> {
 *     // do something with i
 * });
 * }</pre>
 */
public class Loop {
    private final int from;
    private int to;

    private Loop(int from) {
        this.from = from;
    }

    /**
     * Creates a new loop starting from the given value (inclusive).
     *
     * @param from the starting value of the loop (inclusive)
     * @return a new {@link Loop} instance
     */
    public static Loop from(int from) {
        return new Loop(from);
    }

    /**
     * Sets the ending value of the loop.
     *
     * @param to the ending value of the loop (inclusive)
     * @return this {@link Loop} instance
     */
    public Loop to(int to) {
        this.to = to;
        return this;
    }

    /**
     * Executes the given consumer for each value in the loop.
     *
     * @param consumer the consumer to execute for each value in the loop
     */
    public void loop(Consumer<Integer> consumer) {
        int i = from;
        int step = (from > to) ? -1 : 1;

        while ((step == 1 && i <= to) || (step == -1 && i >= to)) {
            consumer.accept(i);
            i += step;
        }
    }
}
