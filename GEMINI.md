Project Goal: To provide a fluent and expressive Java API for creating and executing loops.

Core Features:

*   **Fluent API:** The library offers a chained, intuitive interface for building loops, starting with the `Loop` class.
*   **Two Loop Types:**
    *   **Numeric Loops (`NumericSeries`):**
        *   Iterate over a range of integers.
        *   Define a start (`from`) and end (`to`) value (inclusive).
        *   Support both forward and backward iteration.
        *   Requires the `to` value to be set before execution.
    *   **Array Loops (`ArraySeries`):**
        *   Iterate over an array of any object type.
        *   Optionally specify `from` and `to` indices to loop over a sub-section of the array.
        *   The loop body receives both the element and its index.

Functional Requirements & Constraints:

*   **Null/Empty Safety:** The `ArraySeries` loop must not execute if the provided array is `null` or empty.
*   **Boundary Checks:**
    *   `ArraySeries` `from` index cannot be negative.
    *   `ArraySeries` `to` index is automatically capped at the array's upper bound.
*   **Default Behavior:** If no `to` index is specified for an `ArraySeries`, the loop runs to the end of the array.
*   **Immutability:** The main `Loop` class is a non-instantiable utility class. The `NumericSeries` and `ArraySeries` classes are mutable builders for loop configuration.

Technical Requirements:

*   **Language:** Java 11
*   **Build:** Maven
*   **Testing:** The project uses JUnit 5 and AssertJ for its test suite, which covers all core functionality and edge cases.
*   **Testing Style:** Use BDD fluent assertions (`then()`) from AssertJ instead of `assertThat()`.