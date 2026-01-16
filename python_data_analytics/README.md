# Introduction

In this project, LGS have provided Jarvis with a data dump of their invoices for Jarvis to perform data analysis on. Due to lack of IT resources in LGS, as a data engineer in Jarvis, I have the task to transform and analyze the data to provide relevant information for the client. In order to do so, I have loaded the data dump into a Jupyter notebook to perform various transformation and analysis by utilizing pandas and numPy.

During the analytics, I performed various basic analysis such as average sales amount along with some user related information such as most recent purchase and average amount of new users. Finally, I performed some RFM calculation in order to segment the customers to different groups for differing marketing strategy that LGS would be able to use to maximize their sales.

In order to do the analysis, I utilized Jupyter Notebook as the environment for me to work on my data. The data itself is stored in both a PSQL server alongside a csv data dump on the side. To perform the actual mathematical queries and operations, I used pandas dataframe alongside the python language to do my data manipulation.

# Implementaion

In terms of implementation, both the Jupyter environment and the PSQL environment for the data was set up using Docker and connected using Docker network. For the dependencies, it utilized pandas dataframe extensively for data manipulation alongside both pandas and matplotlib to plot those data in descriptive graphs. The Jupyter notebook contains the various analysis that is done alongside with relevant visualizations for each analysis. The analysis itself is just done by python code manipulating the pandas dataframe.

For RFM Implementation
Recency = Months since last purchase
Frequency = Total amount of unique invoices for customer
Monetary = Largest purchase done on a single invoice

The values are then normalized to values within 1,2,3,4,5 then transformed into the customer segmentation.

## Project Architecture

The data itself comes from a data dump from the LGS App. It is then loaded into a data warehouse in a PSQL server. Then, the data is loaded into a Jupyter Notebook then cleaned and transformed to be ready for analysis. This data is stored as a pandas dataframe. Finally, the loaded data is analyzed to give useful information.

## Data Analytics and Wrangling

`./retail_data_analytics_wrangling.ipynb`

The above notebook contains the analysis of the data. LGS has not been getting as much new customers, so they might want to increase their marketing to attract new users or ensure that they retain their champion members. It should be a priority that the champion and loyal customers should be retained. Additionally, there are a lot of at risks customers that might need targeted marketing to retain them along with a lot of potential loyalist that might change into loyal customers with a bit of a push. Those are the RFM segmentations that LGS should focus on the most currently.

# Improvements

Some improvements that could still be done on this project includes - Identifying most popular items for future market development - Identify Champion customers individually and tailoring special data analysis on each Champion customer to create specialized marketing strategy - Analyze item purchases that leads to lost customers to figure out product improvement.
