# Getting Started

To get started with &lambda;Loop, you can add it as a dependency to your project.

## Maven Dependency

```xml
<dependency>
  <groupId>com.github.stefanofornari</groupId>
  <artifactId>lambda-loop</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage

Here is a simple example of how to use &lambda;Loop:

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
