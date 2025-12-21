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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import static ste.lloop.Loop._break_;

/**
 * A sequence that loops over lines from a File, Path, or BufferedReader.
 */
public class LinesSequence extends ForwardOnlySequence<String> {
    private BufferedReader reader;

    /**
     * Creates a new sequence for the given File.
     * @param file the File to loop over
     * @throws IllegalArgumentException if the file is null, does not exist, is a directory, or is not readable.
     */
    public LinesSequence(File file) {
        // Delegate to Path constructor
        this(file == null ? null : file.toPath());
    }

    /**
     * Creates a new sequence for the given Path.
     * @param path the Path to loop over
     * @throws IllegalArgumentException if the path is null, does not exist, is a directory, or is not readable.
     */
    public LinesSequence(Path path) {
        super();
        if (path == null) {
            return;
        }
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Path does not exist: " + path.toAbsolutePath());
        }
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Path is not a regular file: " + path.toAbsolutePath());
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("Path is not readable: " + path.toAbsolutePath());
        }
        try {
            this.reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error opening path: " + path.toAbsolutePath(), e);
        }
    }

    /**
     * Creates a new sequence for the given BufferedReader.
     * @param reader the BufferedReader to loop over
     */
    public LinesSequence(BufferedReader reader) {
        super();
        this.reader = reader;
    }

    @Override
    public <R> R loop(BiConsumer<Integer, String> consumer) {
        if (reader == null) {
            return null;
        }

        if (indexes.step == 0) {
            return null;
        }

        final AtomicInteger currentIndex = new AtomicInteger(0);

        try {
            return indexes.loop(targetIndex -> {
                String line = null;
                while (currentIndex.get() <= targetIndex) {
                    try {
                        line = reader.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading file lines", e);
                    }
                    if (line == null) { // EOF
                        _break_(); // Break the loop, no more lines
                    }
                    currentIndex.incrementAndGet();
                }
                consumer.accept(targetIndex, line);
            });
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // Ignore, or log if necessary
            }
        }
    }
}
