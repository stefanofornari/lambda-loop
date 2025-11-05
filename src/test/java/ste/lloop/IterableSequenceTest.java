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

public class IterableSequenceTest {
    @Test
    public void on_loops_through_the_elements_of_an_iterable() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new IterableSequence<>(list).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,");
    }

    @Test
    public void to_throws_exception_if_less_than_from() {
        thenThrownBy(() -> {
            new IterableSequence<>(Arrays.asList("a", "b")).from(1).to(0);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("to can not be less than zero or from");
    }

    @Test
    public void on_can_loop_from_a_given_index() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new IterableSequence<>(list).from(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("2:c,3:d,4:e,");
    }

    @Test
    public void to_throws_exception_if_less_than_zero() {
        thenThrownBy(() -> {
            new IterableSequence<>(Arrays.asList("a", "b")).to(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("to can not be less than zero or from");
    }

    @Test
    public void on_can_loop_to_a_given_index() {
        final List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        final AtomicInteger counter = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();

        new IterableSequence<>(list).to(2).loop((index, element) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(element).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:a,1:b,2:c,");
    }
}