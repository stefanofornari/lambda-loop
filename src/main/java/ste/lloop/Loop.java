package ste.lloop;

/**
 * Provides a fluent API for creating loops.
 *
 * <p>This class is the main entry point for creating loops. Use one of the static {@code on} methods
 * to start building a loop.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Numeric loop from 0 to 10 (inclusive)
 * Loop.on().from(0).to(10).loop(i -> {
 *     // do something with i
 * });
 *
 * // Loop over an array of strings, from index 1 up to (and including) index 3
 * Loop.on(new String[]{"a", "b", "c", "d"}).from(1).to(3).loop((index, element) -> {
 *    // do something with index and element
 * });
 *
 * // Numeric loop from 0 to 10, with a step of 2
 * Loop.on().from(0).to(10).step(2).loop(i -> {
 *     // i will be 0, 2, 4, 6, 8, 10
 * });
 *
 * // Numeric loop from 0 to 10, with a negative step of -2 (inverts direction)
 * Loop.on().from(0).to(10).step(-2).loop(i -> {
 *     // i will be 10, 8, 6, 4, 2, 0
 * });
 * }</pre>
 */
public final class Loop {

    private Loop() {}

    /**
     * Creates a new numeric loop.
     *
     * @return a new {@link NumericSeries} instance
     */
    public static NumericSeries on() {
        return new NumericSeries();
    }

    /**
     * Creates a new loop over the given items. This method supports both varargs and passing an array directly.
     *
     * @param items the items to loop over (can be varargs or an array)
     * @param <T> the type of the items
     * @return a new {@link ArraySeries} instance
     */
    @SafeVarargs
    public static <T> ArraySeries<T> on(T... items) {
        return new ArraySeries<>(items);
    }
}
