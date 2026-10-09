# MallPlanner — design

The diagrams behind the app: what the planner does with it, and how the data is stored.

Four tables. One mall has many floors. Each floor has services and shops.

---

## Use case diagram

What the planner can do with the app.

![Use case diagram](images/ULM.png)

One actor: the **Planner**. They enter the building information, define how much of each floor goes to services, and manage the shops. Managing shops **includes** calculating the rentable area, because that number is recomputed on every add, edit and delete — it is never stored. When a shop does not fit, the insufficient-space warning **extends** that flow and tells the planner how many square metres short they are.

The rest — the suggestions, the floor report, comparing floors and switching language — all read the same four tables and write nothing new to them.

---

## ER diagram

Entities, attributes and relationships, drawn in ERDPlus.

![ER diagram](images/erdplus-er.png)

A mall **has** floors. A floor **contains** service areas and **houses** shops.

---

## Relational schema

The same model as tables, with the keys shown.

![Relational schema](images/erdplus-schema.png)

---

## Tables

**MALL** — Mall_id, name, total_area

**FLOOR** — Floor_id, Mall_id (FK), floor_number, area, rent_price, cost

**SERVICE_AREA** — Service_area_id, Floor_id (FK), type, size

**SHOP** — Shop_id, Floor_id (FK), name, area, category

Anything ending in `_id` marked (FK) points at another table. The foreign keys protect the order: a floor cannot exist without its mall, and a shop cannot exist without its floor. Delete from the bottom up — shops and service areas first, then the floor, then the mall.

---

## Two things we decided on purpose

**Free space is not stored.** There is no `free_space` column anywhere. It is calculated every time:

```
rentable area = floor.area - sum of the service areas on that floor
free space    = rentable area - sum of the shop areas on that floor
```

If we stored it, we would have to update it on every add, edit and delete, and one missed update would leave the number wrong forever. Calculating it costs nothing and cannot drift.

**Service areas are rows, not columns.** We could have put `bathrooms`, `restaurants` and `lounges` as three columns on FLOOR. Instead each one is a row in SERVICE_AREA with a `type`. Adding a fourth kind of service later means inserting a row, not changing the table.

---

## Creating it

```
mysql -u root -p < src/main/resources/db/schema.sql
```

The tables are MariaDB. `schema.sql` creates all four with their foreign keys.