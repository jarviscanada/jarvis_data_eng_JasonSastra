-- Show table schema
\d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
SELECT COUNT(*) FROM retail

-- number of clients (e.g. unique client ID)
select count(distinct customer_id) from retail
-- Q4: invoice date range (e.g. max/min dates)
select MAX(invoice_date) as max, MIN(invoice_date) as min from retail

-- Q5: number of SKU/merchants (e.g. unique stock code)
select count(distinct stock_code) from retail

-- Q6: Calculate average invoice amount excluding invoices with a negative amount (e.g. canceled orders have negative amount)
SELECT AVG(combined.quantity)
FROM (
    SELECT SUM(quantity) AS quantity
    FROM retail
    GROUP BY invoice_no
    HAVING SUM(quantity) >= 0
) AS combined;


-- Q7: Calculate total revenue (e.g. sum of unit_price * quantity)
SELECT sum(unit_price * quantity) from retail

-- Q8: Calculate total revenue by YYYYMM