#!/bin/bash

get_cpu_base()
{
    local data=($(awk 'NR==1' /proc/stat))

    local sum=0

    # Loop starting from index 1
    for ((i=1; i<${#data[@]}; i++)); do
        sum=$((sum + data[i]))
    done

    sum=$((sum - data[5]))
    base_total=$sum

    sum=$((sum - data[4]))
    base=$sum

    #echo "${array[1]}"
}

get_cpu_usage()
{
    local data=($(awk 'NR==1' /proc/stat))

    local sum=0

    # Loop starting from index 1
    for ((i=1; i<${#data[@]}; i++)); do
        sum=$((sum + data[i]))
    done

    sum=$((sum - data[5]))
    local total=$sum

    sum=$((sum - data[4]))
    local delta=$((sum - base))
    
    total=$((total - base_total))

    local usage=$(echo "scale=8; $delta / $total * 100" | bc)

    echo -e "CPU_usage:\t$usage"
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

timer=0
base=0
base_total=0

main()
{
    while (( timer < 100 )); do
        get_cpu_base
        sleep 1

        clear
        get_cpu_usage
        get_uptime
        #get_power_info
        get_loadavg_info
        get_mem_info

        timer=$((timer + 1))
        
    done
}

main
