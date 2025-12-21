/*
 * Copyright 2025 the original author or authors from the λLoop project (https://lambda-loop.github.io/).
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

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class LinesSequenceTest {

    @TempDir
    Path tempDir;

    private File createTestFile(String content) throws IOException {
        File file = tempDir.resolve("test.txt").toFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
        return file;
    }

    private Path createTestPath(String content) throws IOException {
        Path path = tempDir.resolve("test.txt");
        Files.write(path, content.getBytes());
        return path;
    }

    @Test
    void loops_through_lines_from_file_and_path() throws IOException {
        // Test File
        File file = createTestFile("line1\nline2\nline3");
        AtomicInteger counter = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();

        new LinesSequence(file).loop(line -> {
            counter.incrementAndGet();
            sb.append(line).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("line1,line2,line3,");

        // Test Path
        counter.set(0);
        sb.setLength(0);
        Path path = createTestPath("path1\npath2");

        new LinesSequence(path).loop(line -> {
            counter.incrementAndGet();
            sb.append(line).append(",");
        });

        then(counter.get()).isEqualTo(2);
        then(sb.toString()).isEqualTo("path1,path2,");
    }

    @Test
    void loops_through_lines_from_file_and_path_with_index() throws IOException {
        // Test File
        File file = createTestFile("line1\nline2\nline3");
        AtomicInteger counter = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();

        new LinesSequence(file).loop((index, line) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(line).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:line1,1:line2,2:line3,");

        // Test Path
        counter.set(0);
        sb.setLength(0);
        Path path = createTestPath("path1\npath2");

        new LinesSequence(path).loop((index, line) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(line).append(",");
        });

        then(counter.get()).isEqualTo(2);
        then(sb.toString()).isEqualTo("0:path1,1:path2,");
    }

    @Test
    void loops_through_lines_from_buffered_reader() {
        BufferedReader reader = new BufferedReader(new StringReader("reader1\nreader2"));
        AtomicInteger counter = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();

        new LinesSequence(reader).loop(line -> {
            counter.incrementAndGet();
            sb.append(line).append(",");
        });

        then(counter.get()).isEqualTo(2);
        then(sb.toString()).isEqualTo("reader1,reader2,");
    }

    @Test
    void null_file_does_not_loop() {
        AtomicInteger counter = new AtomicInteger(0);
        new LinesSequence((File) null).loop(line -> counter.incrementAndGet());
        then(counter.get()).isEqualTo(0);
    }

    @Test
    void null_path_does_not_loop() {
        AtomicInteger counter = new AtomicInteger(0);
        new LinesSequence((Path) null).loop(line -> counter.incrementAndGet());
        then(counter.get()).isEqualTo(0);
    }

    @Test
    void null_buffered_reader_does_not_loop() {
        AtomicInteger counter = new AtomicInteger(0);
        new LinesSequence((BufferedReader) null).loop(line -> counter.incrementAndGet());
        then(counter.get()).isEqualTo(0);
    }

    @Test
    void non_existent_file_or_path_throws_illegal_argument_exception() {
        File nonExistentFile = tempDir.resolve("nonexistent_file.txt").toFile();
        thenThrownBy(() -> new LinesSequence(nonExistentFile).loop(line -> {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Path does not exist");

        Path nonExistentPath = tempDir.resolve("nonexistent_path.txt");
        thenThrownBy(() -> new LinesSequence(nonExistentPath).loop(line -> {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Path does not exist");
    }

    @Test
    void directory_as_file_or_path_throws_illegal_argument_exception() throws IOException {
        Path dir = tempDir.resolve("a_directory");
        Files.createDirectory(dir);

        thenThrownBy(() -> new LinesSequence(dir.toFile()).loop(line -> {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Path is not a regular file");

        thenThrownBy(() -> new LinesSequence(dir).loop(line -> {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Path is not a regular file");
    }

    @Test
    void non_readable_file_or_path_throws_illegal_argument_exception() throws IOException {
        File nonReadableFile = tempDir.resolve("nonreadable_file.txt").toFile();
        Files.write(nonReadableFile.toPath(), "test".getBytes());
        nonReadableFile.setReadable(false);
        thenThrownBy(() -> new LinesSequence(nonReadableFile).loop(line -> {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Path is not readable");

        Path nonReadablePath = tempDir.resolve("nonreadable_path.txt");
        Files.write(nonReadablePath, "test".getBytes());
        Files.getFileAttributeView(nonReadablePath, java.nio.file.attribute.PosixFileAttributeView.class)
             .setPermissions(java.util.Set.of(java.nio.file.attribute.PosixFilePermission.OWNER_WRITE)); // Remove read permission
        thenThrownBy(() -> new LinesSequence(nonReadablePath).loop(line -> {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Path is not readable");
    }

    @Test
    void returns_lines_from_and_to_indexes() throws IOException {
        File file = createTestFile("line0\nline1\nline2\nline3\nline4");
        AtomicInteger counter = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();

        new LinesSequence(file).from(1).to(3).loop((index, line) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(line).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("1:line1,2:line2,3:line3,");
    }

    @Test
    void returns_lines_with_step() throws IOException {
        File file = createTestFile("line0\nline1\nline2\nline3\nline4");
        AtomicInteger counter = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();

        new LinesSequence(file).step(2).loop((index, line) -> {
            counter.incrementAndGet();
            sb.append(index).append(":").append(line).append(",");
        });

        then(counter.get()).isEqualTo(3);
        then(sb.toString()).isEqualTo("0:line0,2:line2,4:line4,");
    }

    @Test
    void loop_returns_value_on_break() throws IOException {
        File file = createTestFile("a\nb\nc\nd\ne");
        String result = new LinesSequence(file).<String>loop((index, element) -> {
            if (index == 2) {
                Loop.brk(element);
            }
        });
        then(result).isEqualTo("c");
    }
}
