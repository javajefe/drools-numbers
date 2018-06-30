Simple demo of Drools engine. It makes an analysis of integer numbers (for example, it identifies if the number is "perfect" or prime, etc.).
The goal is to demonstrate how to use Drools engine features, there is no goal to implement the most efficient solution for that task :-).

The demo also has two parts implemented in two separate rule sets:
1. "typed" rule set is implemented in a standard Drools way, it uses POJOs to model facts;
2. "untyped" rule set uses "schema-less" approach, so it operates with key-value model instead.

There are pros and cons in every approach:
- "typed" implementation is **much** more efficient (in terms of memory consumption and performance), and also better readable, so it is easier to support;
- from the other side, "untyped" implementation is more flexible to extend with new fact types.

### How to build and run
Just run `./gradlew run` or `gradlew.bat run` depending on your OS. You will get some numbers with their short characteristics in console output.
You can define your own numbers for the analysis in the `Main` class. All rules can be found inside `src/main/resources` directory, and session definitions are in the `src/main/resources/META-INF/kmodule.xml` file.