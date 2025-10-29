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
     * Creates a new loop over the given array.
     *
     * @param array the array to loop over
     * @param <T> the type of the elements in the array
     * @return a new {@link ArraySeries} instance
     */
    public static <T> ArraySeries<T> on(T[] array) {
        return new ArraySeries<>(array);
    }
}
