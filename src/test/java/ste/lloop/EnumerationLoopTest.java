package ste.lloop;

import java.util.StringTokenizer;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class EnumerationLoopTest {

    @Test
    public void loop_through_the_elements_of_an_enumeration() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(new StringTokenizer("Hello world!")).loop((element) -> {
            counter.incrementAndGet();
            sb.append(element).append(",");
        });

        then(counter.get()).isEqualTo(2);
        then(sb.toString()).isEqualTo("Hello,world!,");
    }

    @Test
    public void loop_through_the_elements_of_an_enumeration_with_index() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(new StringTokenizer("Hello world!")).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(2);
        then(sb.toString()).isEqualTo("0:Hello,1:world!,");
    }

    @Test
    public void loop_from_a_given_index() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(new StringTokenizer("a b c d e")).from(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("2:c,3:d,4:e,");
    }

    @Test
    public void on_can_loop_from_a_given_index_to_a_given_index() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(new StringTokenizer("a b c d e")).from(1).to(3).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("1:b,2:c,3:d,");
    }

    @Test
    public void on_can_not_loop_backwards() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        thenThrownBy(() -> {
            Loop.on(new StringTokenizer("a b c d e")).from(3).to(1).loop((index, element) -> {
                counter.incrementAndGet();
                sb.append(index).append(":").append(element).append(",");
            });
        }).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("to can not be negative or smaller than from for forward-only sequences");

        then(counter.get()).isEqualTo(0);
        then(sb.toString()).isEmpty();
    }

    @Test
    public void from_throws_exception_if_less_than_zero() {
        thenThrownBy(() -> {
            Loop.on(new StringTokenizer("a b c d e")).from(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("from can not be negative or greater than to for forward-only sequences");
    }

    @Test
    public void to_cap_at_list_size_minus_one() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(new StringTokenizer("a b c d e"))
            .from(0).to(100).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(5); // Should loop 0, 1, 2, 3, 4
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,3:d,4:e,");
    }

    @Test
    public void null_enumertion_does_not_loop() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on((java.util.Enumeration) null).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(0);
        then(sb.toString()).isEmpty();
    }

    @Test
    public void on_empty_enumeration_does_not_loop() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(new StringTokenizer("")).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(0);
        then(sb.toString()).isEmpty();
    }

    @Test
    public void loop_with_step() {
        // from(0).to(10).step(2) -> 0,2,..,10
        final StringBuilder sb1 = new StringBuilder();
        Loop.on(
            new StringTokenizer("a b c d e f g h i j k")
        ).from(0).to(10).step(2).loop((index, element) -> sb1.append(index).append(element));
        then(sb1.toString()).isEqualTo("0a2c4e6g8i10k");

        // step is zero, no loop
        final StringBuilder sb5 = new StringBuilder();
        Loop.on(
            new StringTokenizer("a b c d e f g h i j k")
        ).from(0).to(10).step(0).loop((index, element) -> sb5.append(index).append(element));
        then(sb5.toString()).isEmpty();
    }

    @Test
    public void loop_returns_value_on_break() {
        String result = Loop.on(new StringTokenizer("a b c d e")).<String>loop((index, element) -> {
            if (index == 2) {
                Loop.brk(element);
            }
        });
        then(result).isEqualTo("c");
    }
}