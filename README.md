# Java Streams — Real-World Demo

Learn Java Streams the way interviewers expect: not with toy `Arrays.asList(1,2,3)`
examples, but with **realistic pipelines** — e-commerce revenue, HR analytics,
log triage, nested-collection flattening, report building, and the parallel-stream
traps that senior interviewers love to ask about.

## What are Streams? (the 2-paragraph version)

A **Stream** (Java 8+) is a sequence of elements that supports functional-style
bulk operations. You don't write loops; you describe *what* to compute and the
stream pipeline does it: take a source (a `List`, an array, a file's lines),
chain **intermediate operations** (`filter`, `map`, `sorted`, `flatMap`,
`distinct` — lazy, they do nothing until asked), and finish with a **terminal
operation** (`collect`, `sum`, `count`, `forEach`, `reduce` — this is what
actually triggers the work). Streams never modify the source collection, and a
stream can be consumed only once.

Why they matter in real code: pipelines replace pages of nested loops with a
few readable lines, and they compose — filtering, transforming, grouping and
summarizing snap together like LEGO. That's exactly what this project shows:
each example is a small but genuine business task (revenue per category, top
earners, error hotspots) solved as a stream pipeline, with JUnit tests proving
the numbers.

## Project layout

```
src/main/java/com/streamsdemo/
  orders/    Order, OrderItem, OrderStatus, OrderAnalytics, OrderDemo
  hr/        Employee, EmployeeAnalytics, EmployeeDemo
  logs/      LogEntry, LogLevel, LogAnalyzer, LogDemo
  pipeline/  ProductPipeline (flatMap), PipelineDemo
  reports/   ReportBuilder (joining, toMap), ReportDemo
  parallel/  ParallelCaution, ParallelDemo
  data/      SampleData (shared realistic datasets)
src/test/java/com/streamsdemo/   20 JUnit 5 tests
docs/        3 diagrams (PNG)
```

## How to run

Prerequisites: Java 17+, Maven.

```bash
cd project-11-java-streams-demo

# run all tests
mvn test

# run a demo (after mvn package -DskipTests, or mvn compile)
mvn -q compile
java -cp target/classes com.streamsdemo.orders.OrderDemo
java -cp target/classes com.streamsdemo.hr.EmployeeDemo
java -cp target/classes com.streamsdemo.logs.LogDemo
java -cp target/classes com.streamsdemo.pipeline.PipelineDemo
java -cp target/classes com.streamsdemo.reports.ReportDemo
java -cp target/classes com.streamsdemo.parallel.ParallelDemo
```

## What each example teaches

| Example | Streams concepts |
|---|---|
| **E-commerce orders** (`OrderDemo`) | `filter` + `mapToDouble` + `sum` (revenue), `groupingBy` + `summingDouble` (revenue per category), `partitioningBy` (high-value vs regular) |
| **Employee analytics** (`EmployeeDemo`) | `sorted` + `limit` (top-N), `groupingBy` + `averagingDouble`, `summarizingDouble` (count/sum/min/avg/max in one pass) |
| **Log analysis** (`LogDemo`) | parsing lines into objects, `filter` on ERROR, `groupingBy` hour + `counting`, most-frequent via `max` on grouped counts |
| **flatMap pipeline** (`PipelineDemo`) | `flatMap` turns `Stream<List<X>>` into one flat `Stream<X>` — the tool for nested collections; `distinct` + `sorted` |
| **Report building** (`ReportDemo`) | `Collectors.joining` (with delimiter/prefix/suffix), `toMap` **with a merge function** (required when keys repeat) |
| **Parallel caution** (`ParallelDemo`) | `parallel()` timing vs sequential; `forEach` loses order on parallel streams, `forEachOrdered`/`collect` keep it; never mutate shared state |

## Interview Q&A cheat sheet

1. **Intermediate vs terminal operations?** Intermediate (`filter`, `map`, `sorted`,
   `flatMap`, `distinct`, `limit`, `peek`) are lazy and return a new stream.
   Terminal (`collect`, `forEach`, `count`, `sum`, `reduce`, `min/max`,
   `anyMatch/allMatch/noneMatch`, `findFirst/findAny`) trigger execution and
   close the stream.
2. **What does "lazy evaluation" mean?** Nothing runs until a terminal operation
   is called. `list.stream().filter(...)` alone does zero work — add `.count()`
   and the whole pipeline executes, often fusing operations per element.
3. **Can you reuse a stream?** No — a stream is single-use. Calling a second
   terminal op throws `IllegalStateException: stream has already been operated
   upon`. Create a new stream (or a `Supplier<Stream<T>>`) instead.
4. **Do streams modify the source collection?** No. They read from it; the
   source list is untouched (unless you deliberately mutate inside `forEach`,
   which is bad practice).
5. **map vs flatMap?** `map` turns each element into one value (`Stream<List>`
   stays nested). `flatMap` turns each element into a *stream* and merges them
   into one flat stream — essential for nested collections.
6. **How does groupingBy work?** `collect(groupingBy(classifier, downstream))`
   buckets elements by the classifier and applies the downstream collector
   (`counting()`, `summingDouble()`, `averagingDouble()`, `toList()`…) per
   bucket.
7. **Why does toMap sometimes throw?** Duplicate keys. Always supply a merge
   function — e.g. `toMap(keyFn, valFn, Double::sum)` — when keys can repeat.
8. **When do parallel streams help?** Large datasets + CPU-bound work + no shared
   mutable state + order-insensitive (or ordered collectors). They usually HURT
   on small lists (thread overhead), blocking I/O, or stateful lambdas.
9. **forEach vs forEachOrdered on a parallel stream?** `forEach` makes no order
   guarantees in parallel — output order is scrambled. `forEachOrdered`
   preserves encounter order (at a performance cost). Prefer `collect()`, which
   keeps order safely.
10. **What's wrong with mutating a shared ArrayList inside parallel forEach?**
    Race conditions — lost updates and corrupted state. Use `collect()` (designed
    for safe parallel accumulation) or thread-safe structures, and treat
    lambdas as stateless.

## Tech

Java 17 · Maven · JUnit 5 · plain `java.util.stream` — no frameworks needed.
