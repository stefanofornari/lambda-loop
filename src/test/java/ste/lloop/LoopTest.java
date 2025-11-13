
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

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class LoopTest {

    @Test
    public void on_returns_NumericSeries() {
        then(Loop.on()).isInstanceOf(NumericSeries.class);
    }

    @Test
    public void on_with_iterable_returns_IterableSequence() {
        then(Loop.on(java.util.Arrays.asList("one", "two", "three"))).isInstanceOf(IterableSequence.class);
    }

    @Test
    public void on_with_array_returns_ArraySequence() {
        then(Loop.on()).isInstanceOf(NumericSeries.class);
        then(Loop.on("one", "two", "three")).isInstanceOf(ArraySequence.class);
        then(Loop.on(new String[] { "one", "two", "three" })).isInstanceOf(ArraySequence.class);
    }

    @Test
    public void brk_throws_ReturnValue() {
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
    }
}
