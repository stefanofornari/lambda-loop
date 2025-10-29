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

        // from == to, no loop
        final StringBuilder sb2 = new StringBuilder();
        new NumericSeries().from(5).to(5).loop(sb2::append);
        then(sb2.toString()).isEmpty();
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

    @Test
    public void loop_with_step() {
        // from(0).to(10).step(2) -> 0,2,..,10
        final StringBuilder sb1 = new StringBuilder();
        new NumericSeries().from(0).to(10).step(2).loop(sb1::append);
        then(sb1.toString()).isEqualTo("0246810");

        // from(10).to(0).step(2) -> 10,8,..,0
        final StringBuilder sb2 = new StringBuilder();
        new NumericSeries().from(10).to(0).step(2).loop(sb2::append);
        then(sb2.toString()).isEqualTo("1086420");

        // from(0).to(10).step(-2) -> 10,8,..,0
        final StringBuilder sb3 = new StringBuilder();
        new NumericSeries().from(0).to(10).step(-2).loop(sb3::append);
        then(sb3.toString()).isEqualTo("1086420");

        // from(10).to(0).step(-2) -> 0,2,..,10
        final StringBuilder sb4 = new StringBuilder();
        new NumericSeries().from(10).to(0).step(-2).loop(sb4::append);
        then(sb4.toString()).isEqualTo("0246810");

        // step is zero, no loop
        final StringBuilder sb5 = new StringBuilder();
        new NumericSeries().from(0).to(10).step(0).loop(sb5::append);
        then(sb5.toString()).isEmpty();
    }
}
