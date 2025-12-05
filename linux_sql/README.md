# Introduction
This project is an implementation of a way to monitor the performance and CPU usage of various different docker applications. For setup, it uses an SQL docker image to ensure SQL database could run properly. It also utilizes the same volume in the container for it to point towards the same database. It utilizes LINUX bash commands in order to obtain various statistics such as CPU usage and memory usage and periodically store them. It utilizes crontab in order to automate the check. This project is created for users that wants to keep track of the usages of their various linux products and monitor them such that they can take action if it is underperforming or overperforming.


# Quick Start
Use markdown code block for your quick-start commands
- Start a psql instance using psql_docker.sh
First, to run the program, it is necessary to create the docker this software will be ran in
```
#IF THE SOFTWARE HAS NO PERMS
chmod +x ./scripts/psql_docker.sh

./scripts/psql_docker.sh [create/stop/start] [username] [password]
```
- Create the database table to store usage and host data
```
./sql/ddl.sql 
```
- Store hardware specification by calling host_info once
```
./scripts/host_info.sh [psql_host] [psql_port_number] [db_name] [psql_user] [psql_password]

#Example

./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
```
- If you want to manually call host usage, it is possible to call it like below, otherwise, it is necessary to setup crontab later to automate it
```
./scripts/host_usage.sh [psql_host] [psql_port_number] [db_name] [psql_user] [psql_password]

#Example

./scripts/host_usage.sh "localhost" 5432 "host_agent" "postgres" "mypassword"

```
- To setup the CRONTAB to automate the system, call the following
```
#Open the Crontab editor
crontab -e

# Inside the Crontab editor, post the following command just like you would like to call host_usage, just with * * * * * to start it
./scripts/host_usage.sh [psql_host] [psql_port_number] [db_name] [psql_user] [psql_password]

#Optional tunnel it into a log so that it is possible to track it
./scripts/host_usage.sh [psql_host] [psql_port_number] [db_name] [psql_user] [psql_password] > /tmp/host_usage.log
```

# Implemenation
This project is implemented by using simple bash scripts in order to obtain the usage data within Linux and using crontab to automate it. Additionally, it uses a docker container with a SQL image such that it is possible to setup an SQL database to store the relevant information. All the code is either written in bash or in SQL.
## Architecture
Below is an example of the Database along with three host
![Architecture Diagram](assets/LinuxMonitoringDiagram.drawio.png)

## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh  
Creates, starts, and stop the docker container used for the scripts
```
# Create Container
./psql_docker.sh create db_username db_password

# Start the container
./psql_docker.sh start

# Stop the container
./psql_docker.sh stop
```
- host_info.sh  
 Stores hardware specification of host. Gets field using shell scripts and stores it into the host_info table in the database
```
./scripts/host_info.sh [psql_host] [psql_port_number] [db_name] [psql_user] [psql_password]

#Example

./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
```
- host_usage.sh  
Gets and Stores usage information. Gets field using shell scripts and stores it into the host_usage table in the database
```
./scripts/host_usage.sh [psql_host] [psql_port_number] [db_name] [psql_user] [psql_password]

#Example

./scripts/host_usage.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
```
- crontab  
The tool used to automate the shell scripts. Call crontab and add a new task into it to periodically gets called (Default option is 1 per minute)
```
#Open the Crontab editor
crontab -e

# Inside the Crontab editor, post the following command just like you would like to call host_usage, just with * * * * * to start it
./scripts/host_usage.sh [psql_host] [psql_port_number] [db_name] [psql_user] [psql_password]
```

## Database Modeling

### host_info

| Column Name       | Data Type | Description |
|------------------|-----------|-------------|
| **id** | SERIAL | Unique identifier for the host (primary key). |
| **hostname** | VARCHAR | Name of the host; must be unique. |
| **cpu_number** | INT2 | Number of CPU cores. |
| **cpu_architecture** | VARCHAR | CPU architecture (e.g., x86_64). |
| **cpu_model** | VARCHAR | CPU model name. |
| **cpu_mhz** | FLOAT8 | CPU frequency in MHz. |
| **l2_cache** | INT4 | Size of L2 cache in KB. |
| **timestamp** | TIMESTAMP | Time when host info was recorded. |
| **total_mem** | INT4 | Total memory in MB. |

---

### host_usage

| Column Name       | Data Type | Description                                       |
|------------------|-----------|---------------------------------------------------|
| **timestamp** | TIMESTAMP | Time when usage metrics were collected.           |
| **host_id** | SERIAL | ID of the host (foreign key to access host_info). |
| **memory_free** | INT4 | Free memory in MB.     |
| **cpu_idle** | INT2 | Percentage of CPU idle time.                      |
| **cpu_kernel** | INT2 | Percentage of CPU time spent in kernel mode.      |
| **disk_io** | INT4 | Number of disk I/O operations.                    |
| **disk_available** | INT4 | Available disk space in MB.                       |


# Test
The various scripts were tested by running it manually in the shell and then either checking the output or logging into the sql database to see if changes were made. The crontab was tested by checking both the database and the resulting logs from tunneling the log.
# Deployment
The application is deployed when the crontab has been setup. In terms of where it is located, it has a remote repository in GitHub that can be cloned to be ran locally.
# Improvements
Three improvements that could be made
- Make an all around startup script that goes through all the process such that it is not needed to run it one by one in shell (i.e deploy.sh to run the entire quick start section)
- Create a visual aid to help process the data obtained, a simple script that would graph average usage per hour/day in a line graph among a certain amount of time for each machine
- Create a shell script that allows for easy crontab scheduling accessibility (canceling/changing frequency/starting/pausing)