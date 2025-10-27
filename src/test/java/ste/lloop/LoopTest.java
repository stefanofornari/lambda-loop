package ste.lloop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;
import java.util.concurrent.atomic.AtomicInteger;

public class LoopTest {

    @Test
    public void on_from_returns_not_null() {
        then(Loop.on().from(0)).isNotNull();
    }

    @Test
    public void on_from_to_returns_not_null() {
        then(Loop.on().from(0).to(10)).isNotNull();
    }

    @Test
    public void loop_can_be_called() {
        Loop.on().from(0).to(10).loop(i -> {});
    }

    @Test
    public void loop_executes_the_correct_number_of_times() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();
        Loop.on().from(0).to(10).loop(i -> {
            counter.incrementAndGet();
            sb.append(i);
        });
        then(counter.get()).isEqualTo(11);
        then(sb.toString()).isEqualTo("012345678910");
    }

    @Test
    public void loop_executes_backwards_when_from_is_greater_than_to() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();
        Loop.on().from(10).to(0).loop(i -> {
            counter.incrementAndGet();
            sb.append(i);
        });
        then(counter.get()).isEqualTo(11);
        then(sb.toString()).isEqualTo("109876543210");
    }

    @Test
    public void on_loops_through_the_elements_of_an_array() {
        final String[] array = {"a", "b", "c"};
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(array).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,");
    }

    @Test
    public void on_can_loop_from_a_given_index() {
        final String[] array = {"a", "b", "c", "d", "e"};
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(array).from(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("2:c,3:d,4:e,");
    }

    @Test
    public void on_can_loop_from_a_given_index_to_a_given_index() {
        final String[] array = {"a", "b", "c", "d", "e"};
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(array).from(1).to(4).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("1:b,2:c,3:d,");
    }
}
