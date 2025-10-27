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
public class ArrayLoop<T> {
    private final T[] array;
    private int from = 0;
    private int to;

    ArrayLoop(T[] array) {
        this.array = array;
        this.to = array.length;
    }

    /**
     * Sets the starting index of the loop (inclusive).
     *
     * @param fromIndex the starting index
     * @return this {@link ArrayLoop} instance
     */
    public ArrayLoop<T> from(int fromIndex) {
        this.from = fromIndex;
        return this;
    }

    /**
     * Sets the ending index of the loop (exclusive).
     *
     * @param toIndex the ending index
     * @return this {@link ArrayLoop} instance
     */
    public ArrayLoop<T> to(int toIndex) {
        this.to = toIndex;
        return this;
    }

    /**
     * Executes the given consumer for each element in the loop.
     *
     * @param consumer the consumer to execute for each element
     */
    public void loop(BiConsumer<Integer, T> consumer) {
        for (int i = from; i < to; i++) {
            consumer.accept(i, array[i]);
        }
    }
}