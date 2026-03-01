/*
 * Copyright 2025 the original author or authors from the λLoop project (https://lambda-loop.github.io/)..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ste.lloop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import java.util.concurrent.atomic.AtomicInteger;

public class NumericSequenceTest {

    @Test
    public void from_returns_not_null() {
        then(new NumericSequence().from(0)).isNotNull();
    }

    @Test
    public void to_returns_not_null() {
        then(new NumericSequence().from(0).to(10)).isNotNull();
    }

    @Test
    public void loop_can_be_called() {
        new NumericSequence().from(0).to(10).loop(i -> {});
    }

    @Test
    public void loop_executes_the_correct_number_of_times() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();
        new NumericSequence().from(0).to(10).loop(i -> {
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
        new NumericSequence().from(10).to(0).loop(i -> {
            counter.incrementAndGet();
            sb.append(i);
        });
        then(counter.get()).isEqualTo(11);
        then(sb.toString()).isEqualTo("109876543210");
    }

    @Test
    public void loop_with_step() {
        // from(0).to(10).step(2) -> 0,2,..,10
        final StringBuilder sb = new StringBuilder();
        new NumericSequence().from(0).to(10).step(2).loop(sb::append);
        then(sb.toString()).isEqualTo("0246810");

        // from(10).to(0).step(2) -> 10,8,..,0
        sb.delete(0, sb.length());
        new NumericSequence().from(10).to(0).step(2).loop(sb::append);
        then(sb.toString()).isEqualTo("1086420");

        // step is zero, no loop
        sb.delete(0, sb.length());
        new NumericSequence().from(0).to(10).step(0).loop(sb::append);
        then(sb.toString()).isEmpty();
    }

    @Test
    public void loop_returns_value_on_break() {
        final String expectedValue = "test value";
        String result = new NumericSequence().from(0).to(10).<String>loop(i -> {
            if (i == 5) {
                Loop.brk(expectedValue);
            }
        });
        then(result).isEqualTo(expectedValue);
    }

    @Test
    public void loop_continues_on_continue() {
        final StringBuilder sb = new StringBuilder();
        new NumericSequence().from(1).to(10).<String>loop(i -> {
            if (i == 5) {
                Loop.cntn();
            }
            sb.append(i).append(',');
        });
        then(sb.toString()).isEqualTo("1,2,3,4,6,7,8,9,10,");
    }

    @Test
    public void infinite_loop_is_broken_by_brk() {
        Integer result = new NumericSequence().from(0).<Integer>loop(i -> {
            if (i == 100) {
                Loop.brk(i);
            }
        });
        then(result).isEqualTo(100);
    }

    @Test
    public void infinite_loop_can_go_backwards() {
        Integer result = new NumericSequence().from(0).step(-1).<Integer>loop(i -> {
            if (i == -100) {
                Loop.brk(i);
            }
        });
        then(result).isEqualTo(-100);
    }

    @Test
    public void infinite_loop_with_zero_step_does_not_run() {
        AtomicInteger counter = new AtomicInteger(0);
        new NumericSequence().from(0).step(0).loop(i -> {
            counter.incrementAndGet();
        });
        then(counter.get()).isEqualTo(0);
    }

    @Test
    public void negative_step_with_to_throws_illegal_argument_exception() {
        thenThrownBy(() -> new NumericSequence().from(0).to(10).step(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("a negative step is not allowed when to is set");

        thenThrownBy(() -> new NumericSequence().from(0).step(-1).to(10))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("a negative step is not allowed when to is set");
    }
}
