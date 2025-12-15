

# Introduction
During this project, I initialized an SQL container using docker then initialized a database from a given SQL file. I then proceeded to write out all the necessary queries required in order to have all sorts of usefull information extracted properly from the database. These queries includes simple ones such as Insert, Update and Delete statement sample queries for the future along with more other queries ones such as various searches utilizing table joins and string concatenations. All teh queries are stored within the queries.sql for future use and reference. 

# SQL Queries

###### Table Setup (DDL)
```
# Make Database
CREATE DATABASE cd;

# Make members table
CREATE TABLE members (
    memid INT PRIMARY KEY,
    surname VARCHAR(200),
    firstname VARCHAR(200),
    address VARCHAR(300),
    zipcode INT,
    telephone VARCHAR(20),
    recommendedby INT,
    joindate TIMESTAMP,
    FOREIGN KEY (recommendedby) REFERENCES members(memid)
)

# Make Facilities Table
CREATE TABLE facilities (
    facid INT PRiMARY KEY,
    name VARCHAR(100),
    membercost NUMERIC,
    guestcost NUMERIC,
    initialoutlay NUMERIC,
    monthlymaintenance NUMERIC
)

CREATE TABLE bookings (
    bookid INT PRIMARY KEY,
    facid INT,
    memid INT,
    starttime TIMESTAMP,
    slots INT,
    FOREIGN KEY (facid) REFERENCES facilities(facid),
    FOREIGN KEY (memid) REFERENCES members(memid)
)


```
###### Question 1: Insert Data
```
INSERT INTO [table_name] VALUES [data]
```


###### Question 2: Select in insert
```
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) (
  VALUES
    (
      SELECT
        max(facid) 
      FROM
        cd.facilities
    )+ 1, 
    'Spa', 
    20, 
    30, 
    100000, 
    800
);
```

###### Question 3: Update
```
UPDATE 
  cd.facilities 
SET
  initialoutlay = 8000 
WHERE
  name = 'Tennis Court 2â
```
###### Question 4: Update with Calculation
```
UPDATE
  cd.facilities facs 
SET 
  membercost = facs2.membercost * 1.1, 
  guestcost = facs2.guestcost * 1.1 
FROM
  (
    SELECT
      * 
    FROM
      cd.facilities 
    WHERE
      facid = 0
  ) facs2 
WHERE 
  facs.facid = 1;
```
###### Question 5: Delete All
```
DELETE FROM
  cd.bookings *;\
```
###### Question 6: Delete Conditions
```
DELETE FROM 
  cd.members 
where 
  memid = 37
```
###### Question 7: Control which rows are retrieved - part 2
```
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
from 
  cd.facilities 
where 
  membercost > 0 
  AND membercost < monthlymaintenance * 1 / 50
```
###### Question 8: Basic string searches
```
SELECT 
  * 
from 
  cd.facilities 
where 
  name LIKE '%Tennis%'
```
## Question 9: Retrieve facilities with facid 1 and 5
```sql
SELECT *
FROM cd.facilities
WHERE facid IN (1,5);
```

## Question 10: Members who joined after 2012-09-01
```sql
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate
FROM cd.members
WHERE joindate >= '2012-09-01';
```

## Question 11: Union surnames and facility names
```sql
SELECT surname 
FROM cd.members
UNION
SELECT name 
FROM cd.facilities;
```

## Question 12: Bookings by David Farrell
```sql
SELECT 
  book.starttime
FROM cd.bookings book
INNER JOIN cd.members mem 
  ON book.memid = mem.memid
WHERE mem.firstname = 'David' 
  AND mem.surname = 'Farrell';
```

## Question 13: Tennis Court bookings on 2012-09-21
```sql
SELECT 
  book.starttime AS start, 
  fac.name AS name
FROM cd.facilities fac
INNER JOIN cd.bookings book 
  ON book.facid = fac.facid
WHERE fac.name LIKE '%Tennis Court%'
  AND book.starttime >= '2012-09-21'
  AND book.starttime < '2012-09-22'
ORDER BY book.starttime;
```

## Question 14: Members and their recommender
```sql
SELECT 
  mem1.firstname AS memfname,
  mem1.surname  AS memsname,
  mem2.firstname AS recfname,
  mem2.surname  AS recsname
FROM cd.members mem1
LEFT JOIN cd.members mem2
  ON mem2.memid = mem1.recommendedby
ORDER BY memsname, memfname;
```

## Question 15: Distinct recommenders
```sql
SELECT DISTINCT 
  mem2.firstname AS firstname, 
  mem2.surname AS surname
FROM cd.members mem1
INNER JOIN cd.members mem2
  ON mem1.recommendedby = mem2.memid
ORDER BY surname, firstname;
```

## Question 16: Count recommendations by recommender
```sql
SELECT 
  mem.recommendedby AS recommendedby, 
  COUNT(*) 
FROM cd.members mem
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby;
```

## Question 17: Total slots booked per facility
```sql
SELECT 
  fac.facid AS facid, 
  SUM(book.slots) AS "Total Slots"
FROM cd.facilities fac
INNER JOIN cd.bookings book 
  ON fac.facid = book.facid
GROUP BY fac.facid
ORDER BY fac.facid;
```

## Question 18: Total slots per facility (Sept 2012)
```sql
SELECT 
  facid, 
  SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE starttime >= '2012-09-01'
  AND starttime < '2012-10-01'
GROUP BY facid
ORDER BY "Total Slots";
```

## Question 19: Monthly slot usage for 2012
```sql
SELECT 
  facid,
  EXTRACT(month FROM starttime) AS month,
  SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE EXTRACT(year FROM starttime) = 2012
GROUP BY facid, month
ORDER BY facid, month;
```

## Question 20: Count distinct members who made bookings
```sql
SELECT DISTINCT COUNT(DISTINCT memid)
FROM cd.bookings;
```

## Question 21: Earliest booking per member (after Sept 1, 2012)
```sql
SELECT 
  mem.surname AS surname,
  mem.firstname AS firstname,
  mem.memid AS memid,
  MIN(book.starttime) AS starttime
FROM cd.members mem
INNER JOIN cd.bookings book
  ON mem.memid = book.memid
WHERE book.starttime >= '2012-09-01'
GROUP BY mem.surname, mem.firstname, mem.memid
ORDER BY mem.memid;
```

## Question 22: Count rows using a window function
```sql
SELECT 
  COUNT(*) OVER(), 
  firstname, 
  surname
FROM cd.members
ORDER BY joindate;
```

## Question 23: Row number by join date (window function)
```sql
SELECT 
  COUNT(*) OVER(ORDER BY joindate), 
  firstname, 
  surname
FROM cd.members
ORDER BY joindate;
```

## Question 24: Concatenate firstname and surname
```sql
SELECT 
  firstname || ', ' || surname AS name
FROM cd.members;
```

## Question 25: Members with "(" in telephone number
```sql
SELECT 
  memid, 
  telephone
FROM cd.members
WHERE telephone LIKE '%(%'
ORDER BY memid;
```

## Question 26: Count members by first letter of surname
```sql
SELECT 
  SUBSTR(mems.surname, 1, 1) AS letter,
  COUNT(*) AS count
FROM cd.members mems
GROUP BY letter
ORDER BY letter;
```


