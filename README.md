# λLoop

A fluent and expressive way to create loops in Java.

## Documentation

The full documentation for λLoop can be found at [https://stefanofornari.github.io/lambda-loop/](https://stefanofornari.github.io/lambda-loop/).

## Usage

Here are a few examples of how to use λLoop:

### Numeric loop

```java
import ste.lloop.Loop;

public class Example {
    public static void main(String[] args) {
        Loop.on().from(0).to(10).loop(i -> {
            System.out.println("Hello, world! " + i);
        });
    }
}
```

### Array loop

```java
import ste.lloop.Loop;

public class Example {
    public static void main(String[] args) {
        String[] array = {"a", "b", "c", "d", "e"};

        Loop.on(array).from(1).to(4).loop((index, element) -> {
            System.out.println("index: " + index + ", element: " + element);
        });
    }
}
```

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.
