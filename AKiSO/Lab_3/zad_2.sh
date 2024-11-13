#!/bin/bash

get_cpu_base()
{
    local count=2
    local line=$(awk -v cnt="$count" 'NR==cnt' /proc/stat)
    local line_split=($line)

    while [[ $line_split[0] == cpu* ]]; do
        core_base+=("$line")
        count=$((count + 1))
        line=$(awk -v cnt="$count" 'NR==cnt' /proc/stat)
        line_split=($line)
    done
}

get_cpu_usage()
{
    local count=2
    local line=$(awk -v cnt="$count" 'NR==cnt' /proc/stat)
    local line_split=($line)
    local core_data=()

    while [[ $line_split[0] == cpu* ]]; do
        core_data+=("$line")
        count=$((count + 1))
        line=$(awk -v cnt="$count" 'NR==cnt' /proc/stat)
        line_split=($line)
    done

    local index=0
    
    for core in "${core_data[@]}"; do
        local data=($core)
        local base_data=(${core_base[index]})

        local base_sum=0
        local sum=0

        # Loop starting from index 1
        for ((i=1; i<${#data[@]}; i++)); do
            base_sum=$((base_sum + base_data[i]))
            sum=$((sum + data[i]))
        done

        base_sum=$((base_sum - base_data[5]))
        sum=$((sum - data[5]))
        local base_total=$base_sum
        local total=$sum

        base_sum=$((base_sum - base_data[4]))
        sum=$((sum - data[4]))
        local delta=$((sum - base_sum))
        local total_delta=$((total - base_total))

        local usage=$(echo "scale=8; $delta / $total_delta * 100" | bc)


        index=$((index + 1))
    done

    core_base=("${core_data[@]}")
}

get_uptime()
{
    seconds=$(cut -d' ' -f1 /proc/uptime)
    minutes=$(echo "$seconds / 60" | bc)
    hours=$(echo "scale=2; $minutes / 60" | bc)
    days=$(echo "scale=2; $hours / 24" | bc)

    echo -e "uptime:\t$seconds s / $minutes min / $hours h / $days days"
}

get_power_info()
{
    total=$(awk 'NR==10' /sys/class/power_supply/BAT0/uevent)
    current=$(awk 'NR==12' /sys/class/power_supply/BAT0/uevent)

    x=$(echo ${total:24})
    y=$(echo $x | awk '{print $1+0}')

    #power=$((total * 100 / current))

    echo -e "batteryStatus:\t$x"
}

get_loadavg_info()
{
    loadavg_1min=$(awk '{print $1}' /proc/loadavg)
    threads=$(awk '{print $4}' /proc/loadavg)

    echo -e "loadAverageOver_1min:\t$loadavg_1min"
    echo -e "running/existing threads:\t$threads"
}

get_mem_info()
{
    echo "$(head -n 3 /proc/meminfo)"
}


base=0
base_total=0

core_base=()

main()
{
    get_cpu_base
    while true; do
        sleep 1
        clear

        get_cpu_usage
        get_uptime
        #get_power_info
        get_loadavg_info
        get_mem_info
        
    done
}

main
