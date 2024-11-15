# malobide

Malobide is an app to help you analyze your stomach aches or digestion problems based on your diet and other factors (
sleep, stress, drugs, etc...)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Videos explaining the code commit by commit

| Commit Name                                             | Video                                                                                                                    |
|---------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| feat: add a dish of one ingredient to the existing meal | [![Initial Commit](https://img.youtube.com/vi/t2jV7QWGE1g/0.jpg)](https://www.youtube.com/watch?v=t2jV7QWGE1g)           |
| feat: first usecases when dish is not added to meal     | [![Feature X Implementation](https://img.youtube.com/vi/WiZQWEZC2V8/0.jpg)](https://www.youtube.com/watch?v=WiZQWEZC2V8) |
| feat: add a dish of multiple ingredients to the meal    | [![Feature X Implementation](https://img.youtube.com/vi/mjL2sm1b8Yw/0.jpg)](https://www.youtube.com/watch?v=mjL2sm1b8Yw) |

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/malobide-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## Related Guides

- REST JSON-B ([guide](https://quarkus.io/guides/rest#json-serialisation)): JSON-B serialization support for Quarkus
  REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on
  it.
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
