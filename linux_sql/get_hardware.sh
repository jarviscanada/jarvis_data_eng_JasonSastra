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
cpu_mhz=$(echo "$cpu_info" | egrep "^cpu MHz" |awk -F: '{print $2}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "L2 cache:" | awk -F: '{print $2}' | xargs)
total_mem= $(vmstat --unit M | tail -1 | awk '{print $4}')
timestamp= $(date "+%F %T") # current timestamp in `2019-11-26 14:40:19` format; use `date` cmd

# usage info
memory_free=$(vmstat --unit M | tail -1 | awk -v col="4" '{print $col}')
cpu_idle=$(vmstat --unit M | tail -1 | awk -v col="15" '{print $col}')
cpu_kernel=$(vmstat --unit M | tail -1 | awk -v col="14" '{print $col}')
disk_io=$(vmstat --unit M -d | tail -1 | awk -v col="10" '{print $col}')
disk_available=$(df -BM / | tail -1 | awk -v col="4" '{print $col}')