# MallPlanner - Sprint 3 Review Report

**Course:** Software Project 1

**Sprint:** 3 (15.09.2026 - 28.09.2026)

**Team:**

- Member 1 : Abdullah Al-Tameemi.
- Member 2 : Farha Mim
- Member 3 : Sarujan Mathyruban

**Scrum Master for Sprint 3:** Farha Mim

**Review date:** 29.09.2026

-----

## 1. Sprint Goal

Finish the application and put real tooling around it.

We wanted the last features done, the tests running by themselves on every commit, and the program in a container that anyone can start without installing Java. After this sprint the project should be something we can hand to someone else, not something that only runs on our machines.

-----

## 2. What We Did

**Features**

- Shop CRUD closed: the category column added to the table, the model, the DAO and the shop screen
- ProfitService: income, cost and profit per floor, plus the best floor and the occupancy of the whole mall
- SuggestionService: from the free area it builds the options (2 x 30, 3 x 20, 1 x 60), sorts them by income and marks the best one
- Choosing a suggestion now creates the shops, named Shop 1, Shop 2 and so on
- ReportScreen: every floor with its rentable area, shop area, free space, income and profit

**Two languages**

- LanguageScreen asks for English or Finnish before anything else and calls Messages.setLanguage
- messages_fi.properties written with the same keys as the English file
- All the text that was still written inside Main and MallSetupScreen moved into the properties files
- Checked every service, model, DAO and controller class: none of them touches the console or the message file

**CI/CD**

- Jenkinsfile in the repository: checkout, build, unit tests, coverage
- The pipeline runs on every commit to main
- The four DAO tests are left out of the pipeline, because they need a database the build server has not got
- Jenkins and Docker were not in the original sprint board. We added them during the sprint, after the lectures covered them.

**Docker**

- Dockerfile that builds the jar with Maven and runs it on Java 21
- maven-shade-plugin added so the app and the MariaDB driver end up in one runnable jar
- Image built, tested locally and published on Docker Hub

**Testing**

- 45 JUnit tests, all passing
- Coverage report regenerated and published

-----

## 3. Team Work

| Team Member Name | Assigned Tasks | Time Spent (hrs) | In-class tasks |
|---|---|---|---|
| Abdullah Al-Tameemi | ShopService and its tests, ProfitService, SuggestionService and their tests, MallController, Main, LanguageScreen, the code review that kept the logic away from the screen, Jenkinsfile, Dockerfile and the Docker Hub image, README | _ | Submitted |
| Farha Mim | Scrum Master, ShopScreen with the numbered table and the category field, SuggestionScreen, ReportScreen, messages_en.properties, manual testing of the shop and report screens | _ | Submitted |
| Sarujan Mathyruban | The category column in the shop table, Shop and ShopDAO, schema.sql, ShopDAOTest, FloorProfit and Suggestion models, db.properties, manual database testing | _ | Submitted |

Total: about _ hours.

38 of 38 planned tasks finished: 14 for Shop CRUD, 16 for the calculation and report, 8 for the system and localisation stories.

We kept the layer split from Sprint 2. Each story was cut into a database part, a logic part and a screen part, so the three of us could work on the same feature at the same time.

-----

## 4. How We Worked Together

The Monday meeting at 11:00 stayed, and the second meeting of the week we still agree on Monday.

The pull request habit from Sprint 2 held. Nothing went into main directly this sprint. It paid off once: the profit bug needed a change inside ProfitService, and the pull request is where we agreed on it instead of after the fact.

-----

## 5. Sprint Result

- The program runs end to end: mall, floors, shops, suggestions, report
- It runs in English and in Finnish, chosen at startup, no restart needed
- 45 unit tests, all green
- Jenkins builds and tests every commit to main
- The Docker image runs on any machine with Docker

Shop CRUD (US-07 to US-10) is closed. The profit report, the space suggestions and the localisation stories (US-14 to US-16) are done.

Coverage numbers: exception 100%, service 95%, model 82%, dao 79%, whole project 42%.

The number went from 40% to 42%. It is still held down by the console package, which sits at 0% because those classes wait for the user to type. We put the tests where the thinking happens, and the services are at 95%.

Jenkins reports a lower number than the published report. That is not a second measurement - the pipeline skips the DAO tests, so the dao package is not counted there at all.

-----

## 6. What Was Hard

**The profit never changed**

Income was calculated on the whole rentable area instead of the area the shops actually take. An empty floor and a full floor came out with the same profit, and adding or deleting a shop made no difference to the report. The tests did not catch it, because they were written by the same person who wrote the calculation and carried the same assumption. We changed it to the rented area and updated four assertions.

**The shop menu was never called**

Main set up the floor and jumped straight to the suggestions. The line that opens the shop screen was missing, so there was no way to add a shop at all and every report showed zero shops. It had been like that for days without anyone noticing, because nobody had run the program from start to finish since the screen was written.

**Every error said the database failed**

A catch-all in Main was swallowing InvalidInputException, so a wrong percentage came out as "could not save to the database". We added a separate catch before the general one.

**db.properties reached the public branch again**

The same mistake as Sprint 2, in a different place: the gh-pages branch has no .gitignore, so `git add .` there picked up the whole working copy including the database file. The password in it was a dummy one, but it was a public branch. We cleaned the branch and gave it its own .gitignore.

**Jenkins refused to build**

The built-in node was offline because the disk space monitor reported the workspace below its 1 GiB threshold, although the drive had 235 GB free. Clearing the temp folder did not help. We turned off the two space monitors instead.

**DAO tests in the pipeline**

The four DAO tests need a MariaDB server and the build server has not got one, so the pipeline failed on every run. We excluded them from Jenkins rather than weaken the tests. They still run locally.

-----

## 7. What We Learned

- Tests written by the author of the code inherit the author's assumptions. The profit bug sat behind green tests for two sprints. Someone who did not write the calculation has to read what it is supposed to do, not only whether it runs.
- Run the whole program before every review. Two of the three code bugs above were found by typing through the app from the first screen to the last, not by reading code.
- .gitignore belongs to a branch, not to a repository. We learned that the expensive way, twice.
- An error message that lies is worse than no message. "Could not save to the database" sent us looking in the wrong place.

-----

## 8. Sprint 4 Plan

**29.09 - 12.10 - JavaFX**

The console version stays working until the JavaFX one is finished, so there is always something to demo.

We split the tasks at the sprint planning meeting after this review.

Two small things are still open and go into this sprint: the app does not offer starting values for the service areas, and the report shows the shop area but not the number of shops.

-----

## 9. Links

- GitHub: https://github.com/Abdullah22-22/MallPlanner
- Trello board sprint 3: https://trello.com/b/1JtVRYc0/sprint-3
- Trello product-backlog : https://trello.com/b/j3UZmFMi/product-backlog
- Coverage report: https://abdullah22-22.github.io/MallPlanner/
- Docker image: https://hub.docker.com/r/abdullah22hel/mallplanner