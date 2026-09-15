# MallPlanner - Sprint 2 Review Report

**Course:** Software Project 1

**Sprint:** 2 (01.09.2026 - 14.09.2026)

**Team:**

- Member 1 : Abdullah Al-Tameemi.
- Member 2 : Farha Mim
- Member 3 : Sarujan Mathyruban

**Scrum Master for Sprint 2:** Sarujan Mathyruban

**Review date:** 15.09.2026

-----

## 1. Sprint Goal

Build the base of the application.

We wanted a working database, the first screens, unit tests and a code coverage report. After this sprint the program should keep the data after it is closed, instead of losing everything when it stops.

-----

## 2. What We Did

**Database**

- Four tables created in MariaDB: mall, floor, service_area and shop
- Foreign keys so the floors stay inside a mall and the shops stay inside a floor
- Save, find, update and delete written for every table
- Four DAO tests that save a row, read it back and delete it again
- Tested by hand: add shops, close the program, open it again, the shops are still there

**Logic**

- AreaService: rentable area and free space
- ShopService: add, edit and delete a shop, and tell how much space is missing
- NotEnoughSpaceException carries the missing area
- MallController connects the screens to the services and the DAOs

**Screens**

- Console screens for the mall setup, the floor setup and the shops
- The shops are shown as a numbered table, so edit and delete pick a real shop
- All the text comes from messages_en.properties

**Testing**

- 25 JUnit tests, all passing
- JaCoCo added to pom.xml
- Coverage report published online

-----

## 3. Team Work

| Team Member Name | Assigned Tasks | Time Spent (hrs) | In-class tasks |
|---|---|---|---|
| Abdullah Al-Tameemi | AreaService, ShopService, the exception class and their tests, MallController, connecting the screens to the database, JaCoCo | _ | Submitted |
| Farha Mim | Console screens, the English text file, manual testing of the shop screen | _ | Submitted |
| Sarujan Mathyruban | Model classes, schema.sql, MallDAO, FloorDAO, ServiceAreaDAO, ShopDAO, DAO tests | _ | Submitted |

Total: about _ hours.

This time we did split the work. Each user story was cut into three layers, one for each of us, so we could work at the same time without waiting.

-----

## 4. How We Worked Together

We kept the same rhythm as in Sprint 1. The Monday meeting at 11:00 stayed, and the second meeting of the week we agree on Monday.

Discord is still where everything is written down. We also started opening a pull request for every branch instead of pushing straight to main, so the others can see what changed before it is merged.

-----

## 5. Sprint Result

- The database works and the data survives a restart
- The program runs from the mall setup to the shops, floor by floor
- 25 unit tests, all green
- Coverage report is online: https://abdullah22-22.github.io/MallPlanner/

Two stories are done: Mall setup (US-01 to US-03) and Floor and space distribution (US-04 to US-06). Shop CRUD (US-07 to US-10) was started in this sprint and moves to Sprint 3. The add, edit and delete already work and the shops are saved, but the category field is still missing, so we did not close the card.

Coverage numbers: exception 100%, service 94%, model 77%, dao 74%, whole project 40%.

The total started at 24% and reached 40% when the DAO tests were added at the end of the sprint. It is still held down by the console screens, which wait for the user to type, so we cannot test them automatically yet. We put the tests where the thinking happens.

-----

## 6. What Was Hard

**Work lost after a pull**

One branch was finished but never merged, so a later pull brought back the old files and a day of work disappeared from the working copy. We got it back from the branch, but after that we agreed to merge a branch as soon as it is done instead of leaving it open.

**db.properties ended up in the repository**

While publishing the coverage report we emptied a branch, which removed .gitignore with it, and the database file with the password was committed. We removed it, but it taught us to check `git status` before every commit and never to trust that .gitignore is there.

**Publishing the report**

The school server refused our password because it wants an SSH key. We did not want to lose time on it during the sprint, so we published the report with GitHub Pages instead.

**Delete removed an area, not a shop**

The first version of the shop screen asked for a number of square metres to delete, so the user could delete an amount that belonged to no shop at all. We changed it to a numbered table where you pick a real shop.

-----

## 7. What We Learned

- Merge a finished branch straight away. A branch that sits open is work waiting to be lost.
- The screen should not do the maths. When the counting lived in the screen it was wrong after a restart; when it moved to the controller it stayed right.
- Coverage is not one number. 40% for the whole project and 94% for the logic tell two different stories, and the second one matters more here.
- Check what you are committing. One careless `git add .` published a password.

-----

## 8. Sprint 3 Plan

**15.09 - 28.09 - Finish the shops, then JavaFX and the profit report**

- Sarujan Mathyruban - the category column in the shop table, model and DAO
- Farha Mim - ask for the category on the shop screen, then the first JavaFX screens from the Figma design
- Abdullah Al-Tameemi - ProfitService and SuggestionService

We start by closing Shop CRUD (US-07 to US-10), because only the category is left. After that we take the profit report and the space suggestion.

Jenkins is the topic of the Sprint 3 lectures, so we will also look at running our tests automatically instead of by hand.

The console stays as it is until the JavaFX screens work, so we always have something to demo.

-----

## 9. Links

- GitHub: https://github.com/Abdullah22-22/MallPlanner
- Trello board sprint 2: https://trello.com/b/PZpE5OLe/sprint-2
- Trello product-backlog : https://trello.com/b/j3UZmFMi/product-backlog
- Coverage report: https://abdullah22-22.github.io/MallPlanner/
