# Getting Started

λLoop provides a fluent and expressive way to create loops in Java, moving away from the traditional, more verbose loop constructs. This guide will walk you through the features of λLoop, from basic loops to more advanced use cases, comparing them with standard Java loops.

## Maven Dependency

To get started, add the λLoop dependency to your project's `pom.xml`:

```xml
<dependency>
  <groupId>com.github.stefanofornari</groupId>
  <artifactId>lambda-loop</artifactId>
  <version>1.0-SNAPSHOT</version>
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

## Array and Collection Loops

Looping over arrays or collections is another common use case.

### Traditional `for-each` loop

The `for-each` loop is a simple way to iterate over the elements of a collection or array:

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

If you need the index of the element, you would typically use a traditional `for` loop:

```java
String[] array = {"a", "b", "c"};
for (int i = 0; i < array.length; i++) {
    System.out.println("index: " + i + ", element: " + array[i]);
}
```

### λLoop equivalent with index

λLoop makes this cleaner by providing the index and the element directly to the lambda:

```java
import ste.lloop.Loop;

String[] array = {"a", "b", "c"};
Loop.on(array).loop((index, element) -> {
    System.out.println("index: " + index + ", element: " + element);
});
```

## Advanced Looping

λLoop also supports more advanced looping scenarios.

### Stepping

You can specify a `step` to control the increment of the loop.

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

Looping backwards is straightforward with λLoop. A negative step will automatically invert the loop direction. This can also be achieved by setting the `to` value to be less than the `from` value.

```java
// Traditional for loop, looping backwards
for (int i = 10; i >= 0; i--) {
    // i will be 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0
}

// λLoop equivalent with negative step
Loop.on().from(0).to(10).step(-1).loop(i -> {
    // i will be 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0
});

// Looping backwards with to < from
Loop.on().from(10).to(0).loop(i -> {
    // i will be 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0
});
```

### Looping over a sub-section of an array

You can easily loop over a specific portion of an array by using the `from` and `to` methods.

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

Note: If the `to` value is greater than the array's length, λLoop will automatically cap it to the last valid index of the array.

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

A common issue with lambda expressions is that they can only access final or effectively final local variables. This prevents you from modifying a local variable from within a lambda.

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