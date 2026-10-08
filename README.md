
Image: <https://hub.docker.com/r/abdullah22hel/mallplanner>

---

## What we used and why

| | | |
| --- | --- | --- |
| Java 21 | language | the course is in Java and so is every example |
| Maven | build | one command builds and tests the lot |
| MariaDB | database | the course wants a relational one, this is the one from the lectures |
| JUnit 5 | tests | the standard, and Maven already knows it |
| JaCoCo | coverage | makes the HTML report we publish |
| Jenkins | CI/CD | builds and tests every commit to main |
| Docker | container | runs anywhere, installs nothing |
| Console | first UI | quick to build, so the logic could come first |
| JavaFX | desktop UI | the Sprint 4 screens, same logic underneath |
| .properties files | languages | built into Java, no extra library |

### The one choice worth explaining

The logic never touches the screen. A service class prints nothing and has no idea the console exists. When something goes wrong it throws a key like `error.area.zero`, and the screen works out what to say.

We did it that way so the JavaFX version could pick up the same code instead of copying it. That is exactly what happened in Sprint 4: new screens, not one line of the maths rewritten.

Same reason there is no text sitting inside a `.java` file anywhere. It all lives in `messages_en.properties` and `messages_fi.properties`. A third language is one more file, nothing else.

---

## Tests

45 of them, all green.