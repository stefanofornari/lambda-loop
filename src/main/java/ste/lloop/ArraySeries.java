package ste.lloop;

import java.util.function.BiConsumer;

/**
 * A loop over an array that can be configured with a starting and ending index.
 *
 * <p>This class is not meant to be instantiated directly. Use {@link Loop#on(Object[])} to start
 * building an array loop.
 *
 * @param <T> the type of the elements in the array
 */
public class ArraySeries<T> {
    private final T[] array;
    private final NumericSeries indexes;

    /**
     * Constructs an {@code ArraySeries} instance.
     *
     * @param array the array to loop over
     */
    ArraySeries(T[] array) {
        this.array = array;
        this.indexes = new NumericSeries();
    }

    /**
     * Sets the starting index of the loop (inclusive).
     *
     * @param from the starting index
     * @return this {@link ArraySeries} instance
     * @throws IndexOutOfBoundsException if the 'from' value is less than zero
     */
    public ArraySeries<T> from(final int from) {
        if (from < 0) {
            throw new IndexOutOfBoundsException("The 'from' value cannot be less than zero.");
        }
        indexes.from(from); return this;
    }

    /**
     * Sets the ending index of the loop (inclusive).
     *
     * @param to the ending index
     * @return this {@link ArraySeries} instance
     */
    public ArraySeries<T> to(final int to) {
        indexes.to(to); return this;
    }

    /**
     * Executes the given consumer for each element in the loop.
     *
     * <p>If the array provided to the constructor was {@code null}, this method will do nothing.
     *
     * @param consumer the consumer to execute for each element
     */
    public void loop(final BiConsumer<Integer, T> consumer) {
        if (array == null || array.length == 0) {
            return; // Do nothing for null or empty array
        }

        if (indexes.to == null) {
            indexes.to = array.length-1;
        } else {
           if (indexes.to > array.length-1) {
               indexes.to = array.length-1;
           }
        }
        indexes.loop((i) -> consumer.accept(i, array[i]));
    }
}