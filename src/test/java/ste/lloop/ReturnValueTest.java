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

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.Test;
import static ste.lloop.Loop.on;
import static ste.lloop.Loop._break_;

public class ReturnValueTest {

    @Test
    public void constructor_initializes_value() {
        final String initialString = "test";
        ReturnValue value = new ReturnValue(initialString);
        then((String)value.value()).isEqualTo(initialString);

        final int initialInt = 1;
        value = new ReturnValue(initialInt);
        then((int)value.value()).isEqualTo(initialInt);

        value = new ReturnValue();
        then((String)value.value()).isNull();

        value = new ReturnValue(null);
        then((Integer)value.value()).isNull();
    }

    @Test
    public void to_string_returns_value_as_string() {
        ReturnValue value = new ReturnValue(123);
        then(value.toString()).isEqualTo("123");

        value = new ReturnValue("hello");
        then(value.toString()).isEqualTo("hello");

        value = new ReturnValue(null);
        then(value.toString()).isEqualTo("null");
    }

    @Test
    public void return_value_as_a_throwable() {
        try {
            throw new ReturnValue("hello");
        } catch (ReturnValue v) {
            then((String)v.value()).isEqualTo("hello");
        }

        try {
            throw new ReturnValue(10);
        } catch (ReturnValue v) {
            then((Integer)v.value()).isEqualTo(10);
        }
    }

    @Test
    public void return_value_is_a_NoStackError() {
        then(new ReturnValue()).isInstanceOf(NoStackError.class);
    }

    @Test
    public void break_with_catch() {
        final StringBuffer sb = new StringBuffer();
        on("one", "two", "three").loop((i, s) -> {
            try {
                if (i%2  != 0) {
                    _break_();
                }
            } catch (Exception x) {}
            sb.append(s);
        });

        then(sb).asString().isEqualTo("one");
    }
}
