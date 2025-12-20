# λLoop

A fluent and expressive way to create loops in Java.

It allows you to use the same consistent pattern to loop over all supported sequences (arrays, collections, strings, etc.), instead of different ways to iterate over them.

## Documentation

The full documentation for λLoop can be found at [https://stefanofornari.github.io/lambda-loop/](https://stefanofornari.github.io/lambda-loop/).

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
        // (a negative step is only allowed for infinite loops)
        Loop.on().from(0).to(10).step(2).loop(i -> {
            // i will be 0, 2, 4, 6, 8, 10
        });

        // infinite loop going backwards from 0 with a step of -1
        Loop.on().from(0).step(-1).loop(i -> {
            // i will be 0, -1, -2, ...
            // This loop will run indefinitely until Loop.brk() is called.
        });
    }
}
```

### Looping over sequences

```java
import ste.lloop.Loop;
import java.util.Arrays;
import java.util.List;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class Example {
    public static void main(String[] args) {
        // loops over items using varargs
        Loop.on("a", "b", "c", "d", "e").from(1).to(4).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });

        // loops over items using varargs, without index
        Loop.on("a", "b", "c").loop(element -> {
            System.out.println("element: " + element);
        });

        String[] array = {"one", "two", "three"};
        // loops over items using an array
        Loop.on(array).from(0).to(2).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });

        List<String> list = Arrays.asList("alpha", "beta", "gamma");
        // loops over items using a List (any Iterable)
        Loop.on(list).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });

        // loops over items using an Enumeration (e.g. StringTokenizer)
        Enumeration<Object> tokens = new StringTokenizer("one two three");
        Loop.on(tokens).loop(element -> {
             System.out.println("element: " + element);
        });

        // If a null array is provided, the loop will not execute.
        Loop.on((String[]) null).loop((index, element) -> {
            System.out.println("This will not be printed.");
        });

        // loops over items with a step of 2
        Loop.on("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k").from(0).to(10).step(2).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });
    }
}
```

### Looping over Maps

```java
import ste.lloop.Loop;
import java.util.Map;

public class Example {
    public static void main(String[] args) {
        Map<String, Integer> map = Map.of("one", 1, "two", 2);

        // loops over map entries
        Loop.on(map).loop((key, value) -> {
            System.out.println("key: " + key + ", value: " + value);
        });

        // loops over map entries with index
        Loop.on(map).loop((index, key, value) -> {
             System.out.println(index + ": " + key + " = " + value);
        });
    }
}
```

### Breaking out of a loop

```java
import static ste.lloop.Loop.brk;
import ste.lloop.Loop;

public class Example {
    public static void main(String[] args) {
        Integer firstEven = Loop.on().from(1).to(100).<Integer>loop(i -> {
            if (i % 2 == 0) {
                brk(i);
            }
        });
        System.out.println("The first even number is " + firstEven); // prints "The first even number is 2"
    }
}
```

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.