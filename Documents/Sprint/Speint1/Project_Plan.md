# MallPlanner - Project Plan

**Course:** Software Project 1

**Team:**

- Member 1 : Abdullah Al-Tameemi.
- Member 2
- Member 3

**Scrum Master for Sprint 1:** Member 1

**Date:** August 2026

-----

## 1. Project Overview

**Title:** MallPlanner

**Problem**

If a person wants to open a shopping mall, from the first moment he has to pay a huge amount of money. The person planning everything has to know, before the project starts, if it is profitable or not, or he loses a big part of his money. Through our project, we solve this problem — we give the customer a simple view.

**Users**

Anyone who wants to open a shopping mall, a residential building, or a hotel can use it, but it is mainly directed to malls.

**Main features**

- The user enters everything about the mall
- We divide each floor into the main services requested by the customer, like bathrooms, lounge, corridors
- We calculate the remaining space for each floor after subtracting the general spaces
- If a shop's area is bigger than the remaining space, the project refuses to create the shop
- Suggest what to do with some empty spaces
- Show the profit of each floor
- We use two languages, Finnish and English

-----

## 2. Project Objectives

- First, we focus on making the console version work
- Separate the logic from the interface
- Input and output in their own classes, so we don't write the logic again for the GUI
- We aim for around 70% test coverage on the logic classes
- We use Jenkins in Sprint 3
- Docker in Sprint 4

-----

## 3. Scope and Deliverables

**Included**

- All the mall data
- Space distribution
- CRUD operations — add, edit or delete a floor or a shop, and the free space changes with it
- The project suggests what to do with the empty spaces
- A report for the profit of each floor

**Not included**

- No architectural drawing
- No deciding the location of each shop inside the floor
- No tenants

**What we deliver**

- Sprint 1 - vision, plan, user stories, Trello, GitHub, wireframe
- Sprint 2 - database, first classes, unit tests, coverage
- Sprint 3 - calculation finished, console ready, Jenkins
- Sprint 4 - Docker, SonarQube, presentation

-----

## 4. Project Timeline

We follow the sprint schedule of the course. Each sprint is approximately two weeks, and all tasks are managed on our Trello board.

**Sprint 1 (18.08 - 31.08) – Planning and Design**
Team setup, project topic, vision, project plan, backlog, user stories, GitHub repository, and wireframes.

**Sprint 2 (01.09 - 14.09) – Development Foundation**
Database, first classes, DAO structure, JUnit tests, and test coverage.

**Sprint 3 (15.09 - 28.09) – Core Functionality**
Calculation logic, working console application, Maven build, and Jenkins CI.

**Sprint 4 (29.09 - 06.10) – Quality and Delivery**
Docker, SonarQube, final testing, documentation, and final presentation.

**Deadlines**

- Plan and vision – 31.08
- Sprint 1 review – 01.09
- Sprint 2 review – 15.09
- Sprint 3 review – 29.09
- Final presentation – 06.10

**Dependencies**

Our development tasks follow a logical dependency order:

- The database must exist before we implement the DAO classes
- The classes and DAO layer must exist before we implement the service layer
- The service classes must exist before we can properly test them
- The service layer must be ready before we implement the final calculation logic
- Maven must build the project successfully before Jenkins can run the build automatically
- Jenkins must work before we use it as part of the Docker build process

We plan approximately five or six cards in each sprint. With three team members, this gives roughly two tasks per person per sprint. The tasks are divided on the Trello board and reviewed at the end of each sprint.

-----

## 5. Resource Allocation

**Who does what**

We are three, so everyone does more than one thing.

- Member 1 — Scrum Master, calculation classes
- Member 2 — database classes, unit tests, coverage
- Member 3 — console and JavaFX screens

Jenkins and Docker we do together, Member 1 and Member 3. Nobody has tried this before, so we don't want one person alone in it.

The Scrum Master role changes every sprint, so everyone takes it once.

**How much time we have**

Each of us can give 10 to 12 hours a week. That's around 60 to 70 hours for the team per sprint. We used this number when we decided how many tasks fit in a sprint.

**Tools**

Java 17 and IntelliJ for the code. Maven for the build. MariaDB for the database. JavaFX for the screens later. JUnit, JaCoCo, and SonarQube for testing and quality. Jenkins and Docker for DevOps. GitHub for the code and Trello for the tasks.

**Hardware**

Our own laptops. Jenkins, SonarQube, and the database run locally, so we don't need a server.

**Help from outside**

Lectures and demos in class, tool documentation, and the teacher during lecture hours.

-----

## 6. Risk Management

**Risk 1 - We don't work as a team**
Likelihood: medium. Impact: high.
This is the risk we're most afraid of. If one of us doesn't do his part, or we all work alone without talking, the project stops even if the code is fine.
What we do: a meeting every week, one name on every Trello card, and the code split into packages so nobody blocks the others.

**Risk 2 - Nobody has used Jenkins before**
Likelihood: high. Impact: medium.
The first time can take much longer than we expect.
What we do: start right after the Sprint 3 lecture, follow the class demo, and ask the teacher early instead of losing days alone.

**Risk 3 - JavaFX inside Docker**
Likelihood: medium. Impact: medium.
A program with a screen needs an X server inside the container, and that's not simple.
What we do: keep a console mode that runs without a screen, so the Docker image still works.

**Risk 4 - Not enough time**
Likelihood: high. Impact: medium.
We have other courses at the same time.
What we do: small sprint goals, and JavaFX left for later sprints.

**Risk 5 - We are only three**
Likelihood: high. Impact: medium.
Every task takes longer than in a bigger team. If one of us is sick, we feel it right away.
What we do: keep the scope small, and finish the console version first so we always have something that works.

**Risk 6 - Merge conflicts in Git**
Likelihood: medium. Impact: low.
What we do: each one works in his own package, small commits, and pull before push.

-----

## 7. Testing and Quality Assurance

We focus our tests mainly on the logic, especially the calculation parts, to make sure the result the customer needs is correct. There can also be manual tests on the console and the GUI.

**What kind of tests**

- Unit tests on the logic classes. We test normal numbers and also bad ones, like zero or a negative area
- Tests on the database classes, to see that the data is saved and read back
- Manual tests on the console and later the GUI, at the end of each sprint

**When is the quality good enough**

- Tests pass in Jenkins
- At least 70%+ coverage
- Wrong input is not allowed
- Console gives the same result as the GUI
- No big problems in SonarQube

**Tools**

JUnit for the tests, JaCoCo for the coverage, SonarQube for the code quality, and Jenkins to run them after every push.

-----

## 8. Documentation and Reporting

**What we write**

In Sprint 1 we set up the plan, split the work, and clarified everything. In the next sprints we write:

- Sprint 2 - the database schema
- Sprint 3 - a README with how to build and run the project
- Sprint 4 - a short user manual
- Every sprint - a planning report and a review report, with the hours each of us spent

We put them all on GitHub, so everything is in one place.

**How we follow the progress**

We have two meetings a week — one on Monday, and another one that is flexible, to talk about the progress. There can also be daily communication through Discord messages. We follow Trello daily, and any update should also be written in the Discord group.

-----

## 9. Submission

Member 1 submits this plan.
