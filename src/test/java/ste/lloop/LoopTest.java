package ste.lloop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicInteger;

public class LoopTest {

    @Test
    public void from_returns_not_null() {
        then(Loop.from(0)).isNotNull();
    }

    @Test
    public void to_returns_not_null() {
        then(Loop.from(0).to(10)).isNotNull();
    }

    @Test
    public void loop_can_be_called() {
        Loop.from(0).to(10).loop(i -> {});
    }

    @Test
    public void loop_executes_the_correct_number_of_times() {
        final AtomicInteger counter = new AtomicInteger(0);
        Loop.from(0).to(10).loop(i -> counter.incrementAndGet());
        then(counter.get()).isEqualTo(11);
    }
}
