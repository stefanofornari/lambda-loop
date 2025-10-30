# Getting Started

To get started with λLoop, you can add it as a dependency to your project.

## Maven Dependency

```xml
<dependency>
  <groupId>com.github.stefanofornari</groupId>
  <artifactId>lambda-loop</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage

Here are a few examples of how to use λLoop:

### Numeric loop

```java
import ste.lloop.Loop;

public class Example {
    public static void main(String[] args) {
        // loops from 0 to 10 (inclusive)
        Loop.on().from(0).to(10).loop(i -> {
            // i will be 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        });

        // loops from 0 to 10, with a step of 2
        Loop.on().from(0).to(10).step(2).loop(i -> {
            // i will be 0, 2, 4, 6, 8, 10
        });

        // loops from 0 to 10, with a negative step of -2 (inverts direction)
        Loop.on().from(0).to(10).step(-2).loop(i -> {
            // i will be 10, 8, 6, 4, 2, 0
        });
    }
}
```

### Array loop

```java
import ste.lloop.Loop;

public class Example {
    public static void main(String[] args) {
        // loops over items using varargs
        Loop.on("a", "b", "c", "d", "e").from(1).to(4).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });

        String[] array = {"one", "two", "three"};
        // loops over items using an array
        Loop.on(array).from(0).to(2).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });

        // If a null array is provided, the loop will not execute.
        Loop.on((String[]) null).loop((index, element) -> {
            System.out.println("This will not be printed.");
        });

        // loops over items with a step of 2
        Loop.on("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k").from(0).to(10).step(2).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });

        // loops over items with a negative step of -2 (inverts direction)
        Loop.on("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k").from(0).to(10).step(-2).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });
    }
}
```
