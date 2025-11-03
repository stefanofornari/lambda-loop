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

public class ReturnValueTest {

    @Test
    public void constructor_initializes_value() {
        final String initialString = "test";
        ReturnValue<String> returnString = new ReturnValue<>(initialString);
        then(returnString.value).isEqualTo(initialString);

        final int initialInt = 1;
        ReturnValue<Integer> returnInt = new ReturnValue<>(initialInt);
        then(returnInt.value).isEqualTo(initialInt);

        returnString = new ReturnValue();
        then(returnString.value).isNull();

        returnString = new ReturnValue(null);
        then(returnString.value).isNull();
    }

    @Test
    public void to_string_returns_value_as_string() {
        final ReturnValue<Integer> returnValue = new ReturnValue<>(123);
        then(returnValue.toString()).isEqualTo("123");

        final ReturnValue<String> stringReturnValue = new ReturnValue<>("hello");
        then(stringReturnValue.toString()).isEqualTo("hello");

        final ReturnValue<Object> nullReturnValue = new ReturnValue<>(null);
        then(nullReturnValue.toString()).isEqualTo("null");
    }
}
