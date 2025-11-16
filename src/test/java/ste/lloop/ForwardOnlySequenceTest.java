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

class ForwardOnlySequenceTest {
    @Test
    public void on_loops_through_the_elements_of_an_iterable() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new ForwardOnlySequence<>(list).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,");
    }

    @Test
    public void from_throws_exception_if_less_than_zero() {
        thenThrownBy(() -> {
            new ForwardOnlySequence<>(Arrays.asList("a", "b")).from(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("from can not be less than zero");
    }

    @Test
    public void to_throws_exception_if_less_than_zero() {
        thenThrownBy(() -> {
            new ForwardOnlySequence<>(Arrays.asList("a", "b")).to(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("to can not be less than zero");
    }

    @Test
    public void on_can_loop_from_a_given_index() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new ForwardOnlySequence<>(list).from(2).loop((index, element) -> {
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

        new ForwardOnlySequence<>(list).to(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,");
    }

    @Test
    public void loop_returns_value_on_break() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        String result = new ForwardOnlySequence<>(list).<String>loop((index, element) -> {
            if (index == 2) {
                Loop.brk(element);
            }
        });
        then(result).isEqualTo("c");
    }

    @Test
    public void to_throws_exception_if_from_is_greater() {
        thenThrownBy(() -> {
            new ForwardOnlySequence<>(Arrays.asList("a", "b", "c", "d")).from(3).to(1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("from can not be greater than to in a forward-only sequence");
    }

    @Test
    public void from_throws_exception_if_to_is_smaller() {
        thenThrownBy(() -> {
            new ForwardOnlySequence<>(Arrays.asList("a", "b", "c", "d")).to(1).from(3);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("from can not be greater than to in a forward-only sequence");
    }

    @Test
    public void step_throws_exception_if_negative() {
        thenThrownBy(() -> {
            new ForwardOnlySequence<>(Arrays.asList("a", "b", "c", "d")).step(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("step can not be negative for forward-only collections");
    }

    @Test
    public void can_loop_with_a_given_step() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new ForwardOnlySequence<>(list).step(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,2:c,4:e,");
    }
}
