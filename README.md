# λLoop

A fluent and expressive way to create loops in Java.

## Documentation

The full documentation for λLoop can be found at [https://stefanofornari.github.io/lambda-loop/](https://stefanofornari.github.io/lambda-loop/).

## Usage

Here is a simple example of how to use λLoop:

```java
import ste.lloop.Loop;

public class Example {
    public static void main(String[] args) {
        Loop.from(0).to(10).loop(i -> {
            System.out.println("Hello, world! " + i);
        });
    }
}
```

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.
