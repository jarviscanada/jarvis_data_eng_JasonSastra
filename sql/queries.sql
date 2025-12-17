-- Table Setup (DDL)
CREATE DATABASE cd;

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
);

CREATE TABLE facilities (
    facid INT PRIMARY KEY,
    name VARCHAR(100),
    membercost NUMERIC,
    guestcost NUMERIC,
    initialoutlay NUMERIC,
    monthlymaintenance NUMERIC
);

CREATE TABLE bookings (
    bookid INT PRIMARY KEY,
    facid INT,
    memid INT,
    starttime TIMESTAMP,
    slots INT,
    FOREIGN KEY (facid) REFERENCES facilities(facid),
    FOREIGN KEY (memid) REFERENCES members(memid)
);

-- Q1
INSERT INTO [table_name] VALUES [data];

-- Q2
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost,
  initialoutlay, monthlymaintenance
) (
  VALUES
    (
      SELECT max(facid) FROM cd.facilities
    ) + 1,
    'Spa',
    20,
    30,
    100000,
    800
);

-- Q3
UPDATE cd.facilities
SET initialoutlay = 8000
WHERE name = 'Tennis Court 2';

-- Q4
UPDATE cd.facilities facs
SET
  membercost = facs2.membercost * 1.1,
  guestcost = facs2.guestcost * 1.1
FROM (
    SELECT *
    FROM cd.facilities
    WHERE facid = 0
) facs2
WHERE facs.facid = 1;

-- Q5
DELETE FROM cd.bookings;

-- Q6
DELETE FROM cd.members
WHERE memid = 37;

-- Q7
SELECT
  facid,
  name,
  membercost,
  monthlymaintenance
FROM cd.facilities
WHERE membercost > 0
  AND membercost < monthlymaintenance * 1 / 50;

-- Q8
SELECT *
FROM cd.facilities
WHERE name LIKE '%Tennis%';

-- Q9
SELECT *
FROM cd.facilities
WHERE facid IN (1, 5);

-- Q10
SELECT
  memid,
  surname,
  firstname,
  joindate
FROM cd.members
WHERE joindate >= '2012-09-01';

-- Q11
SELECT surname
FROM cd.members
UNION
SELECT name
FROM cd.facilities;

-- Q12
SELECT
  book.starttime
FROM cd.bookings book
INNER JOIN cd.members mem
  ON book.memid = mem.memid
WHERE mem.firstname = 'David'
  AND mem.surname = 'Farrell';

-- Q13
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

-- Q14
SELECT
  mem1.firstname AS memfname,
  mem1.surname  AS memsname,
  mem2.firstname AS recfname,
  mem2.surname  AS recsname
FROM cd.members mem1
LEFT JOIN cd.members mem2
  ON mem2.memid = mem1.recommendedby
ORDER BY memsname, memfname;

-- Q15
SELECT DISTINCT
  mem2.firstname,
  mem2.surname
FROM cd.members mem1
INNER JOIN cd.members mem2
  ON mem1.recommendedby = mem2.memid
ORDER BY surname, firstname;

-- Q16
SELECT
  mem.recommendedby,
  COUNT(*)
FROM cd.members mem
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby;

-- Q17
SELECT
  fac.facid,
  SUM(book.slots) AS "Total Slots"
FROM cd.facilities fac
INNER JOIN cd.bookings book
  ON fac.facid = book.facid
GROUP BY fac.facid
ORDER BY fac.facid;

-- Q18
SELECT
  facid,
  SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE starttime >= '2012-09-01'
  AND starttime < '2012-10-01'
GROUP BY facid
ORDER BY "Total Slots";

-- Q19
SELECT
  facid,
  EXTRACT(month FROM starttime) AS month,
  SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE EXTRACT(year FROM starttime) = 2012
GROUP BY facid, month
ORDER BY facid, month;

-- Q20
SELECT DISTINCT COUNT(DISTINCT memid)
FROM cd.bookings;

-- Q21
SELECT
  mem.surname,
  mem.firstname,
  mem.memid,
  MIN(book.starttime) AS starttime
FROM cd.members mem
INNER JOIN cd.bookings book
  ON mem.memid = book.memid
WHERE book.starttime >= '2012-09-01'
GROUP BY mem.surname, mem.firstname, mem.memid
ORDER BY mem.memid;

-- Q22
SELECT
  COUNT(*) OVER(),
  firstname,
  surname
FROM cd.members
ORDER BY joindate;

-- Q23
SELECT
  COUNT(*) OVER(ORDER BY joindate),
  firstname,
  surname
FROM cd.members
ORDER BY joindate;

-- Q24
SELECT
  firstname || ', ' || surname AS name
FROM cd.members;

-- Q25
SELECT
  memid,
  telephone
FROM cd.members
WHERE telephone LIKE '%(%'
ORDER BY memid;

-- Q26
SELECT
  SUBSTR(mems.surname, 1, 1) AS letter,
  COUNT(*) AS count
FROM cd.members mems
GROUP BY letter
ORDER BY letter;
