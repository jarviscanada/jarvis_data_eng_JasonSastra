psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

memory_free=$(echo "$vmstat_mb"| tail -1 | awk -v col="4" '{print $col}')
cpu_idle=$(echo "$vmstat_mb"| tail -1 | awk -v col="15" '{print $col}')
cpu_kernel=$(echo "$vmstat_mb"| tail -1 | awk -v col="14" '{print $col}')
disk_io=$(echo "$vmstat_mb"| tail -1 | awk -v col="10" '{print $col}')
disk_available=$(df / | tail -1 | awk -v col="4" '{print $col}')

timestamp=$(vmstat -t | tail -1 | awk '{print $18 " " $19}')

host_id="(SELECT id FROM host_info WHERE hostname='$hostname')"

insert_message="INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES('$timestamp', $host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available);"

echo $insert_message

export PGPASSWORD=$psql_password

psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$insert_message"
exit $?