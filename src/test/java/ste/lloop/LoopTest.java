package ste.lloop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;

public class LoopTest {

    @Test
    public void on_returns_NumericSeries() {
        then(Loop.on()).isInstanceOf(NumericSeries.class);
    }

    @Test
    public void on_array_returns_ArraySeries() {
        then(Loop.on(new String[]{})).isInstanceOf(ArraySeries.class);
    }
}
