
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
 */package ste.lloop;

import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class LoopTest {

    @Test
    public void on_returns_NumericSequence() {
        then(Loop.on()).isInstanceOf(NumericSequence.class);
    }

    @Test
    public void on_with_iterable_returns_IterableSequence() {
        then(Loop.on(new java.util.HashSet<>(java.util.Arrays.asList("one", "two", "three")))).isInstanceOf(IterableSequence.class);
    }

    @Test
    public void on_with_list_returns_ListSequence() {
        then(Loop.on(java.util.Arrays.asList("one", "two", "three"))).isInstanceOf(ListSequence.class);
    }

    @Test
    public void on_with_array_returns_ArraySequence() {
        then(Loop.on()).isInstanceOf(NumericSequence.class);
        then(Loop.on("one", "two", "three")).isInstanceOf(ArraySequence.class);
        then(Loop.on(new String[] { "one", "two", "three" })).isInstanceOf(ArraySequence.class);
    }

    @Test
    public void on_with_CharSequence_returns_StringSequence() {
        then(Loop.on("Hello World!")).isInstanceOf(CharacterSequence.class);
        then(Loop.on(new StringBuilder(""))).isInstanceOf(CharacterSequence.class);
    }

    @Test
    public void on_with_Enumeration_returns_IteratorSequence() {
        then(Loop.on(new StringTokenizer("Hello world"))).isInstanceOf(IteratorSequence.class);
    }

    @Test
    public void on_with_Iterator_returns_IteratorSequence() {
        then(Loop.on(java.util.Arrays.asList("one", "two").iterator())).isInstanceOf(IteratorSequence.class);
    }

    @Test
    public void on_with_Map_returns_MapSequence() {
        then(Loop.on(new java.util.HashMap<>())).isInstanceOf(MapSequence.class);
    }

    @Test
    public void on_null_enumeration_does_nothing() {
        final AtomicInteger counter = new AtomicInteger(0);
        Loop.on((java.util.Enumeration) null).loop(element -> {
            counter.incrementAndGet();
        });
        then(counter.get()).isEqualTo(0);
    }

    @Test
    public void on_with_file_returns_LinesSequence() {
        then(Loop.on(new java.io.File("pom.xml"))).isInstanceOf(LinesSequence.class);
    }

    @Test
    public void on_with_path_returns_LinesSequence() {
        then(Loop.on(java.nio.file.Paths.get("pom.xml"))).isInstanceOf(LinesSequence.class);
    }

    @Test
    public void on_with_buffered_reader_returns_LinesSequence() {
        then(Loop.on(new java.io.BufferedReader(new java.io.StringReader("")))).isInstanceOf(LinesSequence.class);
    }

    @Test
    public void brk_throws_ReturnValue() {
        //
        // with values
        //
        final String expectedValue = "test value";
        thenThrownBy(() -> Loop.brk(expectedValue))
            .isInstanceOf(ReturnValue.class)
            .extracting("value")
            .isEqualTo(expectedValue);

        final Integer expectedIntValue = 123;
        thenThrownBy(() -> Loop.brk(expectedIntValue))
            .isInstanceOf(ReturnValue.class)
            .extracting("value")
            .isEqualTo(expectedIntValue);

        //
        // without value
        //
        thenThrownBy(() -> Loop.brk())
            .isInstanceOf(ReturnValue.class)
            .extracting("value")
            .isNull();
    }

    @Test
    public void cntn_throws_Continue() {
        //
        // with values
        //
        thenThrownBy(() -> Loop.cntn())
            .isInstanceOf(Continue.class)
            .hasMessage(null);
    }
}
