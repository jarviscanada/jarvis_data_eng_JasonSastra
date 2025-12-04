#!/bin/sh

if [ "$#" -ne 5 ]; then
  echo "Incorrect amount of CLI Args, Got $# need 5"
  exit 1
fi
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# save hostname as a variable
hostname=$(hostname -f)
cpu_info=$(cat /proc/cpuinfo)
# save the number of CPUs to a variable
lscpu_out=`lscpu`
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk -F: '{print $2}' | xargs)
# tip: `xargs` is a trick to remove leading and trailing white spaces
# tip: the $2 is instructing awk to find the second field

# hardware info
cpu_architecture=$(echo "$lcspu_out" | egrep "^Architecture:" | awk -F: '{print $2}' |xargs)
cpu_model=$(echo "$lcspu_out" | egrep "^Model Name" | awk -F: '{print $2}' |xargs)
cpu_mhz=$(echo "$cpu_info" | egrep "^cpu MHz" |awk -F: '{print $2}' | xargs |  cut -d' ' -f1)
l2_cache=$(echo "$lscpu_out"  | egrep "L2 cache:" | awk -F: '{print $2}' | xargs | cut -d' ' -f1)
total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}')
timestamp=$(date "+%F %T") # current timestamp in `2019-11-26 14:40:19` format; use `date` cmd


insert_message="INSERT INTO public.host_info
(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, \"timestamp\", total_mem)
VALUES
('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, '$timestamp', $total_mem);"

echo $insert_message

export PGPASSWORD=$psql_password

psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$insert_message"

exit $?