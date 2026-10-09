# MallPlanner

Say you have a shopping mall and an empty floor. How much of it can you actually rent out, once the corridors and the bathrooms have taken their share? And does the floor make money?

That is what this app answers.

**Course:** Software Project 1 - Metropolia UAS, F2026
**Team:** Abdullah Al-Tameemi, Farha Mim, Sarujan Mathyruban
**Method:** Scrum, four sprints

---

## What it does

You start with the mall: a name, a total area, and how many floors. The area is the ground the building sits on. The floors stack, so 1000 m² with three floors gives you 3000 m² to play with.

Then you take the floors one at a time. How much goes to corridors, bathrooms, restaurants, lounges. The app subtracts it all and hands you one number: what is left for shops. Everything else grows out of that number.

Now you fill it. Add a shop with a name, an area and a category. Change your mind and edit it, or delete it - the free space and the profit follow along. Try to squeeze in a shop that does not fit and the app stops you, and tells you how many square metres short you are.

Space left at the end? The app has ideas. 60 free square metres take two shops of 30, or three of 20, and it tells you which one earns more. Say yes and it puts them there for you, named Shop 1, Shop 2 and so on. Rename them later if you like.

Then the report: floor by floor, the rentable area, what the shops took, what is still empty, the income and the profit. Which floor did best, and how full the mall is overall. The desktop version draws the profit as a bar chart.

The whole thing runs in English or in Finnish. The console asks you when it starts, the desktop version has the two buttons in the corner and switches language on the spot.

It is a calculator, not an architect. It will not draw you a floor plan and it does not know anything about tenants or contracts.

---

## How it is put together

Layered architecture. Five layers, and each one only talks to the one below it:

```
console / ui      the screens - console text or JavaFX windows
      ↓
controller        MallController, the one door into the logic
      ↓
service           the maths: AreaService, ShopService, ProfitService,
                  SuggestionService, MallService, FloorService
      ↓
dao               the SQL: MallDAO, FloorDAO, ServiceAreaDAO, ShopDAO
      ↓
model             Mall, Floor, Shop, ServiceArea, Suggestion, FloorProfit
```

Nothing jumps a layer and nothing points upwards. A service class prints nothing and has no idea whether a console or a window called it.

The class diagram and the use case diagram are here: [Diagrams/uml/uml.md](Diagrams/uml/uml.md)

---

## The database

Four tables: a mall, its floors, and on every floor the service areas and the shops.

The ER diagram, the relational schema and the two design decisions behind them are here: [Diagrams/db/db.md](Diagrams/db/db.md)

---

## Running it

You need Java 21, Maven and a MariaDB server.

1. Create the database with `src/main/resources/db/schema.sql`
2. Copy `db.properties.example` to `db.properties` and fill in your own host, port, database name, user and password. It is in `.gitignore`. Leave it there.
3. `mvn clean test`
4. Run `Main`

That starts the console. For the desktop windows:

```
mvn javafx:run
```

Both use the same logic underneath, so it is the same app either way - pick whichever you like.

The console asks for the language first, then the mall.

### Or skip all of that

```
docker run -it abdullah22hel/mallplanner:latest
```

No Java, no Maven, nothing to install.

Keep the `-it`. The console waits for you to type, and without it you cannot.

The desktop windows come out of the container too, but a container has no screen of its own, so it borrows yours. Start Xming with "No Access Control" ticked, then:

```
docker run -it abdullah22hel/mallplanner:latest java -cp app.jar ui.FxLauncher
```

One thing we should be upfront about: the database is not in the image. The app runs, the screens work, the maths is right, but nothing gets saved unless a MariaDB is reachable from inside the container. By default it looks for one on your machine, and you can send it somewhere else without rebuilding anything:

```
docker run -it -e DB_HOST=host.docker.internal -e DB_PORT=3307 -e DB_NAME=mallplanner -e DB_USER=root -e DB_PASS=secret abdullah22hel/mallplanner:latest
```

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

```
mvn clean test
```

Report: <https://abdullah22-22.github.io/MallPlanner/>

The project sits at 42%, and that number deserves a sentence:

| exception | 100% |
| --- | --- |
| service | 95% |
| model | 82% |
| dao | 79% |
| console | 0% |

The thinking happens in the services, so that is where the tests went. The console and the JavaFX classes just sit there waiting for someone to type or click, and we have no way to test that automatically yet. We would take 95% where it matters over a prettier average.

---

## Functional testing

The unit tests prove the maths. They do not prove that someone sitting in front of the app can actually build a mall, so we walked the whole thing by hand as well, on both user interfaces.

### Does it do the job

| # | What we did | What we expected | Result |
|---|---|---|---|
| 1 | Mall "Kamppi", 3000 m², 3 floors | Saved, and it moves on to floor 1 | Pass |
| 2 | Floor 1000 m², corridors 15%, bathrooms 50 | 800 m² left for shops | Pass |
| 3 | Added a shop of 200 m² | Free space drops to 600, table shows the shop | Pass |
| 4 | Edited it from 200 to 300 | Free space drops to 500, no second row appears | Pass |
| 5 | Deleted it | The 300 m² come straight back | Pass |
| 6 | Opened the suggestions with 80 m² free | Three options, the 4 × 20 marked as the best earner | Pass |
| 7 | Applied that option | Four shops appear, named Shop 1 to Shop 4 | Pass |
| 8 | Opened the report | Every floor listed, best floor and occupancy on the cards | Pass |
| 9 | Pressed Suomi halfway through | Every label switches, nothing typed in is lost | Pass |
| 10 | Closed the app and opened it again | The mall, floors and shops are still in the database | Pass |

### Does it stop you doing the wrong thing

Every bad value raises an alert and nothing is written to the database. The service throws a message key, the screen turns it into a sentence in the language you picked.

| # | What we did | What we expected | Result |
|---|---|---|---|
| 11 | Left the mall name empty | "The name cannot be empty" | Pass |
| 12 | Typed `abc` where a number goes | "That is not a number", no crash | Pass |
| 13 | Floor area bigger than the whole mall | "The floor does not fit in the mall" | Pass |
| 14 | Services adding up to more than the floor | "The service areas do not fit", floor not saved | Pass |
| 15 | Corridors set to 150% | "Give a percentage between 0 and 100" | Pass |
| 16 | A negative service area | "The value cannot be negative" | Pass |
| 17 | A shop of 500 m² into 300 m² of free space | Alert naming the shortfall: 200 m² short | Pass |
| 18 | A shop with no name | "Give the shop a name" | Pass |
| 19 | Stopped MariaDB, then pressed Save | "Could not save", the app stays open | Pass |

Number 17 is the one we care about most. Telling someone that a shop does not fit is easy. Telling them it is 200 m² too big is the thing that makes the app worth using, and it is the same number whichever interface you are looking at.

---

## Jenkins

Every commit to `main` kicks off the pipeline: checkout, build, unit tests, coverage, package. Then it builds the Docker image and pushes it to Docker Hub, tagged with the build number and with `latest`. It is all in the `Jenkinsfile`.

So the image on Docker Hub is never something one of us uploaded by hand. It is whatever came out of the last green build.

It leaves out the four DAO tests. They want a real database and the build server has not got one, so those stay on our machines. It is also why Jenkins reports a lower coverage number than the published one - it never sees the `dao` package.

---

## Where we are

| Sprint 1 | vision, backlog, Figma, Trello | done |
| --- | --- | --- |
| Sprint 2 | database, first screens, unit tests, JaCoCo | done |
| Sprint 3 | shops, profit report, suggestions, two languages, Jenkins, Docker | done |
| Sprint 4 | JavaFX screens, chart, language switch on the fly | done |

All four sprints are in. Two small things we never got to: the app does not offer sensible starting values for the service areas, and the report gives you the shop area but not how many shops there are. Both on the board, neither in the way.

---

## Links

- Database design: [Diagrams/db/db.md](Diagrams/db/db.md)
- Coverage: <https://abdullah22-22.github.io/MallPlanner/>
- Docker image: <https://hub.docker.com/r/abdullah22hel/mallplanner>
- Backlog: <https://trello.com/b/j3UZmFMi/product-backlog>
- Sprint reports: <https://github.com/Abdullah22-22/MallPlanner/tree/main/Documents/Sprint>