package ste.lloop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import java.util.concurrent.atomic.AtomicInteger;

public class NumericSeriesTest {

    @Test
    public void from_returns_not_null() {
        then(new NumericSeries().from(0)).isNotNull();
    }

    @Test
    public void to_returns_not_null() {
        then(new NumericSeries().from(0).to(10)).isNotNull();
    }

    @Test
    public void loop_can_be_called() {
        new NumericSeries().from(0).to(10).loop(i -> {});
    }

    @Test
    public void loop_executes_the_correct_number_of_times() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();
        new NumericSeries().from(0).to(10).loop(i -> {
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
        new NumericSeries().from(10).to(0).loop(i -> {
            counter.incrementAndGet();
            sb.append(i);
        });
        then(counter.get()).isEqualTo(11);
        then(sb.toString()).isEqualTo("109876543210");
    }

    @Test
    public void loop_throws_exception_if_to_is_not_set() {
        thenThrownBy(() -> {
            new NumericSeries().from(0).loop(i -> {});
        }).isInstanceOf(IllegalStateException.class)
          .hasMessage("'to' has not been set");
    }
}
