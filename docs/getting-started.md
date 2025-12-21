---
layout: default
title: Getting Started
nav_order: 2
---

# Getting Started

λLoop provides a fluent and expressive way to create loops in Java, moving away
from the traditional, more verbose loop constructs. This guide will walk you
through the features of λLoop, from basic loops to more advanced use cases,
comparing them with standard Java loops.

## Maven Dependency

To get started, add the λLoop dependency to your project's `pom.xml`:

```xml
<dependency>
  <groupId>com.github.stefanofornari</groupId>
  <artifactId>lambda-loop</artifactId>
  <version>0.3.0</version>
</dependency>
```

## Numeric Loops

The most common type of loop is a numeric loop that iterates a specific number of times.

### Traditional `for` loop

A standard numeric loop in Java is written as follows:

```java
for (int i = 0; i <= 10; i++) {
    // i will be 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
}
```

### λLoop equivalent

With λLoop, you can write the same loop in a more fluent and readable way:

```java
import ste.lloop.Loop;

Loop.on().from(0).to(10).loop(i -> {
    // i will be 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
});
```

## Array and Iterable Loops

One of the most powerful features of λLoop is its unified approach to iterating
over arrays and any `java.lang.Iterable` (which includes `List`, `Set`, and other
collections). You no longer have to worry about converting between different itera
ble types. λLoop provides a consistent and fluent API for all, giving you the best
of all worlds!

### Looping over any Iterable

λLoop's `on()` method is overloaded to accept any `java.lang.Iterable`. This
means you can seamlessly loop over `List`s, `Set`s, or any custom class that
implements the `Iterable` interface. Note how λLoop provides the index even for
collections that don't naturally have one, like a `Set`.

```java
import ste.lloop.Loop;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

// Looping over a List (without index)
List<String> fruits = Arrays.asList("apple", "banana", "cherry");
Loop.on(fruits).loop(fruit -> {
    System.out.println("fruit: " + fruit);
});

// Looping over a Set (with index)
Set<Integer> numbers = new HashSet<>(Arrays.asList(10, 20, 30));
Loop.on(numbers).loop((index, number) -> {
    System.out.println("Set - index: " + index + ", number: " + number);
});
```

### Traditional `for-each` loop

The `for-each` loop is a simple way to iterate over the elements of a collection
or array:

```java
String[] array = {"a", "b", "c"};
for (String element : array) {
    System.out.println("element: " + element);
}
```

### λLoop equivalent

λLoop provides a similar, fluent alternative:

```java
import ste.lloop.Loop;

String[] array = {"a", "b", "c"};
Loop.on(array).loop(element -> {
    System.out.println("element: " + element);
});
```

### Traditional `for` loop with index

What if you need the index of the element? you would typically have to fall back
tp traditional `for` loop:

```java
String[] array = {"a", "b", "c"};
for (int i = 0; i < array.length; i++) {
    System.out.println("index: " + i + ", element: " + array[i]);
}
```

### λLoop equivalent with index

λLoop makes this cleaner by providing the index and the element directly to the
lambda:

```java
import ste.lloop.Loop;
import java.util.Arrays;
import java.util.List;

String[] array = {"a", "b", "c"};
Loop.on(array).loop((index, element) -> {
    System.out.println("index: " + index + ", element: " + element);
});

List<String> list = Arrays.asList("x", "y", "z");
Loop.on(list).loop((index, element) -> {
    System.out.println("index: " + index + ", element: " + element);
});
```

## Looping over Strings (and CharSequences)

λLoop provides dedicated support for looping over `java.lang.CharSequence`
implementations, such as `String` and `StringBuilder`. This allows you to
iterate through the characters of a string with the same fluent API used
for arrays and collections.

### Traditional `for` loop

To iterate over the characters of a string and access their index, you would
typically use a standard `for` loop:

```java
String text = "Hello";
for (int i = 0; i < text.length(); i++) {
    System.out.println("Character at index " + i + ": " + text.charAt(i));
}
```

### λLoop equivalent

With λLoop, you can achieve the same result more fluently:

```java
import ste.lloop.Loop;

String text = "Hello";
Loop.on(text).loop((index, character) -> {
    System.out.println("Character at index " + index + ": " + character);
});
```

## Looping over Enumerations

Legacy Java APIs often use `java.util.Enumeration`. λLoop allows you to iterate
over them with the same consistent API.

### Traditional `while` loop

To iterate over an `Enumeration`, you typically use a `while` loop:

```java
import java.util.StringTokenizer;
import java.util.Enumeration;

Enumeration<Object> tokens = new StringTokenizer("Hello world");
while (tokens.hasMoreElements()) {
    System.out.println("token: " + tokens.nextElement());
}
```

### λLoop equivalent

With λLoop, it's much simpler and consistent:

```java
import ste.lloop.Loop;
import java.util.StringTokenizer;

Loop.on(new StringTokenizer("Hello world")).loop(token -> {
    System.out.println("token: " + token);
});
```

### λLoop equivalent with index

You can also get the index of the current element:

```java
import ste.lloop.Loop;
import java.util.StringTokenizer;

Loop.on(new StringTokenizer("Hello world")).loop((index, token) -> {
    System.out.println("index: " + index + ", token: " + token);
});
```

## Looping over Iterators

λLoop also supports looping over `java.util.Iterator`.

### Looping over a Scanner

A common use case for `Iterator` is reading from a `Scanner`, for example to process input from the standard input:

### Traditional `while` loop

To iterate over any other `Iterator`, you typically use a `while` loop:

```java
Scanner scanner = new Scanner(System.in);
while (true) {
    String line = scanner.nextLine();
    if ("quit".equals(line)) {
        break;
    }
    System.out.println("You entered: " + line);
}
```

### λLoop equivalent

With λLoop, you can use the same consistent API:

```java
import ste.lloop.Loop;
import java.util.Scanner;

Scanner scanner = new Scanner(System.in);
Loop.on(scanner).loop(line -> {
    if ("quit".equals(line)) {
        Loop.brk();
    }
    System.out.println("You entered: " + line);
});
```

### λLoop equivalent with index

You can also get the index of the current element:

```java
import ste.lloop.Loop;
import java.util.Iterator;
import java.util.Arrays;

Iterator<String> iterator = Arrays.asList("one", "two", "three").iterator();
Loop.on(iterator).loop((index, element) -> {
    System.out.println("index: " + index + ", element: " + element);
});
```

## Looping over Files, Paths and BufferReaders (line by line)

λLoop simplifies reading files line by line, offering a fluent API for `File`, `Path`, and `BufferedReader`. It handles underlying `IOException`s by wrapping them in `RuntimeException`s (or `IllegalArgumentException`s for invalid file/path inputs) and automatically closes the reader when the loop finishes or breaks.

### Traditional approach (BufferedReader)

Traditionally, reading a file line by line involves explicit `BufferedReader` handling and error management:

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

File file = new File("path/to/file.txt");
try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println("Line: " + line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

### λLoop equivalent

With λLoop, you can read a file line by line with a single, fluent expression:

```java
import ste.lloop.Loop;
import java.io.File;

File file = new File("path/to/file.txt");
Loop.on(file).loop(line -> {
    System.out.println("Line: " + line);
});
```

Using `java.nio.file.Path` is equally straightforward:

```java
import ste.lloop.Loop;
import java.nio.file.Path;
import java.nio.file.Paths;

Path path = Paths.get("path/to/file.txt");
Loop.on(path).loop((index, line) -> {
    System.out.println("Index: " + index + ", Line: " + line);
});
```

If you already have a `BufferedReader` instance, you can loop over it directly:

```java
import ste.lloop.Loop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

File file = new File("path/to/file.txt");
try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    Loop.on(reader).loop(line -> {
        System.out.println("Line: " + line);
    });
} catch (IOException e) {
    e.printStackTrace();
}
```

## Looping over Maps

Maps are another fundamental data structure. λLoop treats them as first-class citizens, allowing iteration over key-value pairs with ease.

### Traditional `forEach`

```java
import java.util.Map;

Map<String, Integer> map = Map.of("one", 1, "two", 2);
map.forEach((key, value) -> {
    System.out.println(key + " = " + value);
});
```

### λLoop equivalent

λLoop provides a similar fluent interface but also adds the ability to access the iteration index, which is not directly available in `Map.forEach`.

```java
import ste.lloop.Loop;
import java.util.Map;

Map<String, Integer> map = Map.of("one", 1, "two", 2);

// Standard key-value loop
Loop.on(map).loop((key, value) -> {
    System.out.println("key: " + key + ", value: " + value);
});

// Loop with index, key, and value
Loop.on(map).loop((index, key, value) -> {
    System.out.println(index + ": " + key + " = " + value);
});
```

## Advanced Looping

λLoop also supports more advanced looping scenarios.

### Stepping

You can specify a `step` to control the increment of the loop.

It’s important to understand that when `to` is set, the loop always progresses
from `from` to `to`. The `step` value controls the size of each increment or
decrement, but not the direction. The direction is implicitly determined by the
relative values of `from` and `to`.

But what if `to` is not provided? In an endless loop (i.e., when `to` is not
set), the sign of `step` determines the direction: a negative `step` moves the
sequence backward (decrementing), while a positive `step` moves it forward
(incrementing).

```java
// Traditional for loop with a step
for (int i = 0; i <= 10; i += 2) {
    // i will be 0, 2, 4, 6, 8, 10
}

// λLoop equivalent
Loop.on().from(0).to(10).step(2).loop(i -> {
    // i will be 0, 2, 4, 6, 8, 10
});
```

### Looping Backwards

Looping backwards is straightforward with λLoop.

You can achieve a backward loop by setting the `to` value to be less than the `from` value.

```java
// Traditional for loop, looping backwards
for (int i = 10; i >= 0; i--) {
    // i will be 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0
}

// Looping backwards with to < from
Loop.on().from(10).to(0).loop(i -> {
    // i will be 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0
});
```

For infinite loops, you can use a negative step to loop backwards indefinitely until `Loop.brk()` is called.

```java
// Infinite loop going backwards from 0 with a step of -1
Loop.on().from(0).step(-1).loop(i -> {
    // i will be 0, -1, -2, ...
    // This loop will run indefinitely until Loop.brk() is called.
});
```

Note that a negative step is only allowed when the `to` value is not set. If you attempt to use a negative step when `to` is defined, an `IllegalArgumentException` will be thrown.

### Looping over a sub-section of an array

You can easily loop over a specific portion of an array by using the `from` and
`to` methods.

```java
String[] array = {"a", "b", "c", "d", "e"};

// Traditional for loop
for (int i = 1; i <= 3; i++) {
    System.out.println("index: " + i + ", element: " + array[i]);
}

// λLoop equivalent
Loop.on(array).from(1).to(3).loop((index, element) -> {
    System.out.println("index: " + i + ", element: " + element);
});
```

Note: If the `to` value is greater than the array's length, λLoop will
automatically cap it to the last valid index of the array.

```java
String[] array = {"a", "b", "c"};

// The `to` value is greater than the array length, so the loop will stop at the last element
Loop.on(array).from(0).to(100).loop((index, element) -> {
    System.out.println("index: " + index + ", element: " + element);
});
// This will print:
// index: 0, element: a
// index: 1, element: b
// index: 2, element: c
```

## Capturing a Return Value

A common issue with lambda expressions is that they can only access final or
effectively final local variables. This prevents you from modifying a local
variable from within a lambda.

For example, the following code will not compile:

```java
int count = 0;
Loop.on("a", "b", "c").loop(element -> {
    count++; // compilation error
});
```

To solve this, λLoop provides a `ReturnValue` holder to capture the result.

Here's how you can count the total number of characters in an array of strings:

```java
import ste.lloop.Loop;
import ste.lloop.ReturnValue;

final ReturnValue<Integer> totalChars = new ReturnValue<>(0);

Loop.on("a", "b", "c")
    .loop(element -> {
        totalChars.value = totalChars.value + element.length();
    });

System.out.println("Total characters: " + totalChars); // prints "Total characters: 3"
```

## Breaking out of a loop

Have you ever been frustrated by the limitations of `forEach` when you need to
break out of a loop and return a value?

With a `forEach` loop, you cannot use `break` or `return` to exit the loop and
return a value. You would need to use a more verbose approach, like a
traditional `for` loop or a flag.

For example, to find the first even number in a list with a `forEach` loop, you
would have to do something like this:

```java
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
final AtomicReference<Integer> firstEven = new AtomicReference<>();
numbers.stream()
    .filter(n -> n % 2 == 0)
    .findFirst()
    .ifPresent(firstEven::set);
```

### The λLoop brk()

With λLoop, you can use the `brk()` method to stop the loop and return a value. An alias method, `_break_()`, is also available for the same purpose.

Here's how you can find the first even number in a range:

```java
import static ste.lloop.Loop.brk;
import ste.lloop.Loop;

Integer firstEven = Loop.on().from(1).to(100).<Integer>loop(i -> {
    if (i % 2 == 0) {
        brk(i);
    }
});

System.out.println("The first even number is " + firstEven); // prints "The first even number is 2"
```

You can return any value you want, not just an element of the series.

Here's an example where we loop through a list of strings and return a custom
message when we find a specific element:

```java
import static ste.lloop.Loop.brk;
import ste.lloop.Loop;
import java.util.Arrays;
import java.util.List;

List<String> fruits = Arrays.asList("apple", "banana", "cherry", "date");

String message = Loop.on(fruits).<String>loop((index, fruit) -> {
    if (fruit.equals("cherry")) {
        brk("Found a cherry at index " + index + "!");
    }
});

System.out.println(message); // prints "Found a cherry at index 2!"
```

This approach allows you to exit the loop at any point and return a value,
making your code more readable and expressive.