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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class MapSequenceTest {

    @Test
    void loops_through_map_entries_with_index_key_value() {
        //
        // Given
        //
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("one", 1);
        testMap.put("two", 2);
        testMap.put("three", 3);

        StringBuilder result = new StringBuilder();
        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<>(testMap).loop((index, key, value) -> {
            result.append(index).append(":").append(key).append("=").append(value).append(";");
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(result.toString()).isEqualTo("0:one=1;1:two=2;2:three=3;");
        then(count.get()).isEqualTo(3);
    }

    @Test
    void loops_through_map_entries_with_key_value() {
        //
        // Given
        //
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("alpha", 10);
        testMap.put("beta", 20);

        StringBuilder result = new StringBuilder();
        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<>(testMap).loop((key, value) -> {
            result.append(key).append("=").append(value).append(";");
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(result.toString()).isEqualTo("alpha=10;beta=20;");
        then(count.get()).isEqualTo(2);
    }

    @Test
    void does_not_loop_on_null_map() {
        //
        // Given
        //
        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<String, Integer>(null).loop((index, key, value) -> {
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(count.get()).isEqualTo(0);
    }

    @Test
    void does_not_loop_on_empty_map() {
        //
        // Given
        //
        Map<String, Integer> emptyMap = new LinkedHashMap<>();
        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<>(emptyMap).loop((index, key, value) -> {
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(count.get()).isEqualTo(0);
    }

    @Test
    void can_loop_from_a_given_index() {
        //
        // Given
        //
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("one", 1);
        testMap.put("two", 2);
        testMap.put("three", 3);
        testMap.put("four", 4);

        StringBuilder result = new StringBuilder();
        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<>(testMap).from(2).loop((index, key, value) -> {
            result.append(index).append(":").append(key).append("=").append(value).append(";");
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(result.toString()).isEqualTo("2:three=3;3:four=4;");
        then(count.get()).isEqualTo(2);
    }

    @Test
    void can_loop_to_a_given_index() {
        //
        // Given
        //
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("one", 1);
        testMap.put("two", 2);
        testMap.put("three", 3);
        testMap.put("four", 4);

        StringBuilder result = new StringBuilder();
        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<>(testMap).to(1).loop((index, key, value) -> {
            result.append(index).append(":").append(key).append("=").append(value).append(";");
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(result.toString()).isEqualTo("0:one=1;1:two=2;");
        then(count.get()).isEqualTo(2);
    }

    @Test
    void can_loop_with_a_given_step() {
        //
        // Given
        //
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("a", 1);
        testMap.put("b", 2);
        testMap.put("c", 3);
        testMap.put("d", 4);
        testMap.put("e", 5);

        StringBuilder result = new StringBuilder();
        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<>(testMap).step(2).loop((index, key, value) -> {
            result.append(index).append(":").append(key).append("=").append(value).append(";");
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(result.toString()).isEqualTo("0:a=1;2:c=3;4:e=5;");
        then(count.get()).isEqualTo(3);
    }

    @Test
    void can_loop_backwards() {
        //
        // Given
        //
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("a", 1);
        testMap.put("b", 2);
        testMap.put("c", 3);
        testMap.put("d", 4);
        testMap.put("e", 5);

        StringBuilder result = new StringBuilder();
        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<>(testMap).from(4).to(0).step(2).loop((index, key, value) -> {
            result.append(index).append(":").append(key).append("=").append(value).append(";");
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(result.toString()).isEqualTo("4:e=5;2:c=3;0:a=1;");
        then(count.get()).isEqualTo(3);
    }

    @Test
    void does_not_loop_if_step_is_zero() {
        //
        // Given
        //
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("a", 1);
        testMap.put("b", 2);
        testMap.put("c", 3);
        testMap.put("d", 4);
        testMap.put("e", 5);

        AtomicInteger count = new AtomicInteger(0);

        //
        // When
        //
        new MapSequence<>(testMap).from(0).to(4).step(0).loop((index, key, value) -> {
            count.incrementAndGet();
        });

        //
        // Then
        //
        then(count.get()).isEqualTo(0);
    }

    @Test
    void from_throws_exception_if_less_than_zero() {
        //
        // Given
        //
        Map<String, Integer> testMap = new LinkedHashMap<>();

        //
        // Then
        //
        thenThrownBy(() -> new MapSequence<>(testMap).from(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("from can not be less than zero");
    }
}
