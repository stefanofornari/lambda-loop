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

/**
 * A holder for a return value that can be used in a lambda expression.
 *
 * @param <R> the type of the return value
 */
public class ReturnValue<R> {

    /**
     * The value of the return value.
     */
    public R value;

    /**
     * Constructs a new {@link ReturnValue} instance with the given value.
     *
     * @param value the initial value
     */
    public ReturnValue(R value) {
        this.value = value;
    }

    /**
     * Constructs a new {@link ReturnValue} instance with a null value.
     */
    public ReturnValue() {
        this(null);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
