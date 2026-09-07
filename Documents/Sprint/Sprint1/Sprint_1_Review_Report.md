# MallPlanner - Sprint 1 Review Report

**Course:** Software Project 1

**Sprint:** 1 (18.08.2026 - 31.08.2026)

**Team:**

- Member 1 : Abdullah Al-Tameemi.
- Member 2 : Farha Mim
- Member 3 : Sarujan Mathyruban

**Scrum Master for Sprint 1:** Member 1

**Review date:** 01.09.2026

-----

## 1. Sprint Goal

Plan the project properly before writing any code.

We wanted the team, the topic, the documents and the tools to be ready, so that in Sprint 2 we can start on the database and the first classes without stopping to decide things.

-----

## 2. What We Did

**Planning**

- Team formed and roles agreed
- Project topic selected
- Scope and risks defined
- Scrum Master chosen for Sprint 1

**Documents**

- Product Vision written and submitted to Oma
- Project Plan written and submitted to Oma
- 16 user stories with acceptance criteria
- Wireframe with 6 screens

**Setup**

- Git repository created
- README and .gitignore added
- Maven pom with Java 17 and JUnit 5
- Trello board with 7 lists and 8 labels

-----

## 3. Team Work

| Team Member Name | Assigned Tasks | Time Spent (hrs) | In-class tasks |
|---|---|---|---|
| Abdullah Al-Tameemi | Scrum Master, project plan, Trello board, GitHub setup, Maven | 15 | Submitted |
| Farha Mim | Product Vision, user stories, helped the group with the other tasks | 10 | Submitted |
| Sarujan Mathyruban | Wireframe and 6 screens, helped the group with the other tasks | 9 | Submitted |

Total: about 35 hours.

In this sprint we did not split the work strictly. Everyone helped on every task, because most of it was planning and it was easier to decide things together.

-----

## 4. How We Worked Together

We had two meetings a week. The first one is every Monday at 11:00, where we go through the Trello board, agree what each of us does next, and clear anything that is blocked. The second one is flexible and we agree the day at the Monday meeting, depending on what everyone has that week.

Between the meetings we use Discord. Every Trello update is also written in the group, so nobody has to open the board to know what changed.

-----

## 5. Sprint Result

All the Sprint 1 cards are done.

- 12 cards planned, 15 cards finished (we added a few during the sprint)
- 16 user stories written, each one with acceptance criteria
- 6 screens designed
- Both documents submitted to Oma before the deadline

-----

## 6. What Was Hard

**Scope was too big**

Our first idea included the architectural drawing, the shop placement inside the floor, and the tenants. We removed all three. A small project that is finished is worth more than a big one that is half done.

**Empty pom.xml**

Maven did not load, so IntelliJ would not let us create Java classes at all. We fixed it by writing the pom with Java 17 and JUnit 5, then syncing the project.

-----

## 7. What We Learned

- Cut the scope early. It is easier to add later than to remove.
- Move the Trello cards while you work. Moving everything the night before a review is visible in the activity log.
- Agree the code structure before writing code, so nobody blocks anyone.

-----

## 8. Sprint 2 Plan

**01.09 - 14.09 - Database, first classes and unit tests**

- Sarujan Mathyruban - Mall and Floor tables, DatabaseConnection, MallDAO and FloorDAO
- Abdullah Al-Tameemi - MallService, FloorService, the exception classes and their unit tests
- Farha Mim - Messages, the English language file, ConsoleInput, ConsoleOutput and the setup screen

We take two stories only: Mall setup (US-01 to US-03) and Floor and space distribution (US-04 to US-06). Each story is split into three layers, one for each of us, so we can work at the same time.

We swapped the database and screen roles after the Sprint 1 meeting.

-----

## 9. Links

- GitHub: https://github.com/Abdullah22-22/MallPlanner
- Trello board: https://trello.com/b/IQkJLoWE/mallplanner
