package ste.lloop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;

public class LoopTest {

    @Test
    public void on_returns_NumericSeries() {
        then(Loop.on()).isInstanceOf(NumericSeries.class);
    }

    @Test
    public void on_with_attay_returns_ArraySeries() {
        then(Loop.on()).isInstanceOf(NumericSeries.class);
        then(Loop.on("one", "two", "three")).isInstanceOf(ArraySeries.class);
        then(Loop.on(new String[] { "one", "two", "three" })).isInstanceOf(ArraySeries.class);
    }
}
