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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class IteratorSequenceTest {
    @Test
    public void on_loops_through_the_elements_of_an_iterator() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new IteratorSequence<>(list.iterator()).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,");
    }

    @Test
    public void on_loops_through_the_elements_of_an_iterator_without_index() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new IteratorSequence<>(list.iterator()).loop((element) -> {
            counter.incrementAndGet();
            sb.append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("a,b,c,");
    }

    @Test
    public void on_can_loop_from_a_given_index() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new IteratorSequence<>(list.iterator()).from(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("2:c,3:d,4:e,");
    }

    @Test
    public void on_can_loop_to_a_given_index() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new IteratorSequence<>(list.iterator()).to(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,");
    }

    @Test
    public void loop_returns_value_on_break() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        String result = new IteratorSequence<>(list.iterator()).<String>loop((index, element) -> {
            if (index == 2) {
                Loop.brk(element);
            }
        });
        then(result).isEqualTo("c");
    }

    @Test
    public void to_throws_exception_if_negative_or_smaller_then_from() {
        thenThrownBy(() -> {
            new IteratorSequence<>(Arrays.asList("a", "b", "c", "d").iterator()).from(3).to(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("to can not be negative or smaller than from for forward-only sequences");

        thenThrownBy(() -> {
            new IteratorSequence<>(Arrays.asList("a", "b", "c", "d").iterator()).from(3).to(1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("to can not be negative or smaller than from for forward-only sequences");
    }

    @Test
    public void from_throws_exception_if_negative_or_bigger_than_to() {
        thenThrownBy(() -> {
            new IteratorSequence<>(Arrays.asList("a", "b", "c", "d").iterator()).to(1).from(3);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("from can not be negative or greater than to for forward-only sequences");

        thenThrownBy(() -> {
            new IteratorSequence<>(Arrays.asList("a", "b", "c", "d").iterator()).to(1).from(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("from can not be negative or greater than to for forward-only sequences");
    }

    @Test
    public void step_throws_exception_if_negative() {
        thenThrownBy(() -> {
            new IteratorSequence<>(Arrays.asList("a", "b", "c", "d").iterator()).step(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("step can never be negative for forward-only sequences");
    }

    @Test
    public void can_loop_with_a_given_step() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new IteratorSequence<>(list.iterator()).step(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,2:c,4:e,");
    }
}
