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

import java.util.function.BiConsumer;

/**
 * A sequence that loops over a CharSequence. Note that we do not reuse
 * ArraySequence to work directly on the sequence itself, not on char[]
 * extracted from it.
 *
 */
public class CharacterSequence extends IndexedSequence<Character> {

    private CharSequence sequence;

    /**
     * Creates a new sequence for the given CharSequence.
     * @param sequence the CharSequence to loop over
     */
    public CharacterSequence(CharSequence sequence) {
        super();
        this.sequence = sequence;
    }

    @Override
    public <R> R loop(final BiConsumer<Integer, Character> consumer) {
        if (sequence == null || sequence.length() == 0) {
            return null;
        }

        adjustIndexes(sequence.length());

        return indexes.loop((i) -> consumer.accept(i, sequence.charAt(i)));
    }
}