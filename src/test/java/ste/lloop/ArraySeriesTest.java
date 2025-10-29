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

public class ArraySeriesTest {

    @Test
    public void on_loops_through_the_elements_of_an_array() {
        final String[] array = {"a", "b", "c"};
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new ArraySeries<>(array).loop((index, element) -> {
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

        new ArraySeries<>(array).from(2).loop((index, element) -> {
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

        new ArraySeries<>(array).from(1).to(3).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("1:b,2:c,3:d,");
    }

    @Test
    public void on_can_loop_backwards() {
        final String[] array = {"a", "b", "c", "d", "e"};
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new ArraySeries<>(array).from(3).to(1).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("3:d,2:c,1:b,");
    }

    @Test
    public void from_throws_exception_if_less_than_zero() {
        thenThrownBy(() -> {
            new ArraySeries<>(new String[]{}).from(-1);
        }).isInstanceOf(IndexOutOfBoundsException.class)
          .hasMessage("The 'from' value cannot be less than zero.");
    }

    @Test
    public void to_caps_at_array_size_minus_one() {
        final String[] array = {"a", "b", "c"}; // length 3, max index 2
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new ArraySeries<>(array).from(0).to(100).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3); // Should loop 0, 1, 2
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,");
    }

    @Test
    public void on_null_array_does_not_loop() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on((String[]) null).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(0);
        then(sb.toString()).isEmpty();
    }

    @Test
    public void on_empty_array_does_not_loop() {
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        Loop.on(new String[]{}).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(0);
        then(sb.toString()).isEmpty();
    }
}
