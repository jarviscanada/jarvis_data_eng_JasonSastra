# Introduction

- After a succesfull analysis on the previous small scale dataset from LGS, this time, we are handling a much larger dataset from LGS which could not be computed locally. Due to that, it is decided to use Azure Database to store the data alongside Databricks in order to run the spark processes. Other than that, also used RapidVantage API to create a day-to-day stock analysis on Databricks Delta Live Table.
- Utilized Azure SQL Databse to store the CSV data by using Azure Data Studio to load the data in. Utilized Azure Data Delta Lake in order to store the JSON data such that it is possible to use external volumes to load in the data to Databricks. In Databricks, utilized PySpark in order to parse and analyze the data using Three Medallion Architecture.

# Databricks

- For the financial fraud utilized 5 datasets. Transaction_Data.csv contain all the transaction related data and is stored in Azure SQL Database. cards_data.csv involves the cards used on the tranasactions and is also stored in Azure SQL Database. Users_data.csv involves the users in the transactions and is similarly stored in Azure SQL Database. Frauds_labels.json identify whether a transaction is a fraud and is stored in Azure Delta Data Lake and MCC_codes.json identify the types of industry related to the transaction and is also stored in Azure Delta Data Lake
- For the Stock Analysis, the data comes from the Rapid Vantage API which is updated daily and includes daily stock data including closing, opening, high, and low prices alongside the metadata required for each company.
- Data is stored in Azure and the Data itself is processed utilizing the medallion Architecture in Databricks. Bronze, Silver and Gold table is saved back into the DBFS in order to create seperation between the three layers. Parsed data is then displayed using Databricks Dashboards.

# Future Improvement

- Improve the visual aspect of the dashboard to more easily lead users to relevant information
- Include an easy to use pipeline to upload data and update the dashboard
- Include more stocks instead of just 4 stock.
