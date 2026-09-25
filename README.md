# MallPlanner

A Java app that helps you split the space inside a shopping mall between services and shops, and tells you how much money each floor makes.

**Course:** Software Project 1 - Metropolia UAS, F2026
**Team:** Abdullah Al-Tameemi, Farha Mim, Sarujan Mathyruban
**Method:** Scrum, four sprints

---

## What it does

You start with the mall: a name, a total area, and how many floors.

Then you go floor by floor. You say how much space the corridors take, and how much goes to bathrooms, restaurants and lounges. The app subtracts all of that and tells you what is left for shops. Everything else in the app is built on that one number.

After that you add shops. Name, area, category. You can edit a shop or delete it, and the free space updates right away. If a shop does not fit, the app says no and tells you exactly how many square metres are missing.

If there is space left over, the app suggests what to do with it. 60 free square metres fit two shops of 30, or three of 20, and it shows which brings more income.

At the end you get a report: every floor with its rentable area, shop area, free space, income and profit. Plus the best floor and how full the mall is.

It runs in English and in Finnish.

It is a space calculator and a profit analyzer. It does not draw floor plans and it does not handle tenants or contracts.

---

## How to run it

You need Java 21, Maven and a MariaDB server.

1. Create the database with `schema.sql`
2. Copy `db.properties.example` to `db.properties` and put in your own host, port, database name, user and password. This file is in `.gitignore`. Do not commit it.
3. `mvn clean test`
4. Run `Main`

It asks for the language first, then the mall.

### Or just use Docker

```
docker run -it abdullah22hel/mallplanner:1.0
```

That is the whole thing. No Java, no Maven, nothing to install.

The `-it` matters - the program reads from the keyboard, and without it you cannot type anything.

One honest note: the database is not inside the image. The app starts, all the screens work, the calculations are right, but saving fails unless a MariaDB server is reachable from the container. We are putting the two together with Docker Compose in Sprint 4.

Image: <https://hub.docker.com/r/abdullah22hel/mallplanner>

---

## What we used and why

| | | |
| --- | --- | --- |
| Java 21 | language | the course is in Java and so are all the examples |
| Maven | build | one command builds and tests everything |
| MariaDB | database | the course asks for a relational one, and this is the one from the lectures |
| JUnit 5 | tests | the standard, and Maven already knows it |
| JaCoCo | coverage | makes the HTML report we publish |
| Jenkins | CI/CD | builds and tests every commit to main |
| Docker | container | runs the app anywhere without installing anything |
| Console | UI for now | quick to build, so we could finish the logic first |
| JavaFX | UI in Sprint 4 | same logic underneath, new screens on top |
| .properties files | languages | built into Java, no extra library |

### One decision worth explaining

The logic never touches the screen. A service class does not print anything and does not know the console exists. When something is wrong it throws a key like `error.area.zero`, and the screen decides how to show it.

We did it this way so the JavaFX version in Sprint 4 can reuse the same code instead of copying it.

Same reason there is no text written inside any `.java` file. It all lives in `messages_en.properties` and `messages_fi.properties`. A third language would just be one more file.

---

## Tests

45 tests, all green.

```
mvn clean test
```

Report: <https://abdullah22-22.github.io/MallPlanner/>

The whole project sits at 42%, and that number needs a sentence of explanation:

| exception | 100% |
| --- | --- |
| service | 95% |
| model | 82% |
| dao | 79% |
| console | 0% |

The thinking happens in the services, so that is where we put the tests. The console classes just wait for someone to type, and we cannot test that automatically yet. We would rather have 95% where it counts than a nicer-looking average.

---

## Jenkins

The pipeline runs on every commit to `main`: checkout, build, unit tests, coverage. It is all in the `Jenkinsfile`.

It skips the four DAO tests. Those need a real database and the build server does not have one, so they only run on our machines. That is also why Jenkins shows a lower coverage number than the published report - it is not counting the `dao` package at all.

---

## Where we are

| Sprint 1 | vision, backlog, Figma, Trello | done |
| --- | --- | --- |
| Sprint 2 | database, first screens, unit tests, JaCoCo | done |
| Sprint 3 | shops, profit report, suggestions, two languages, Jenkins, Docker | done |
| Sprint 4 | JavaFX | next |

Still missing, and we know it: the app does not offer sensible default values for the service areas, the report shows shop area but not the number of shops, and nothing stops you from giving two floors more area than the whole mall has.

---

## Links

- Coverage: <https://abdullah22-22.github.io/MallPlanner/>
- Docker image: <https://hub.docker.com/r/abdullah22hel/mallplanner>
- Backlog: <https://trello.com/b/j3UZmFMi/product-backlog>
- Sprint reports: <https://github.com/Abdullah22-22/MallPlanner/tree/main/Documents/Sprint>