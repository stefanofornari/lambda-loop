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
 * A functional interface for consuming items in a map loop.
 *
 * @param <K> the type of the map key
 * @param <V> the type of the map value
 */
@FunctionalInterface
public interface MapLoopConsumer<K, V> {
    /**
     * Performs this operation on the given arguments.
     *
     * @param index the index of the current item
     * @param key the key of the current item
     * @param value the value of the current item
     */
    void accept(Integer index, K key, V value);
}
