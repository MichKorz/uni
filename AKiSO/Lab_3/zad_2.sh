#!/bin/bash

#stress --cpu 4 --io 2 --vm 2 --vm-bytes 1G --timeout 60s

usage_base=0
usage_sum=0
past_usage=()
for ((i=0; i<15; i++)); do
    past_usage+=(0)
done
max_usage=1

read_network_usage()
{
    declare -n sum_ref=$1

    sum_ref=0

    local recieved=($(awk '{print $2}' /proc/net/dev))
    local sent=($(awk '{print $10}' /proc/net/dev))

    for ((i=2; i<${#recieved[@]}; i++)); do
        sum_ref=$((sum_ref + recieved[i]))
    done

    for ((i=1; i<${#sent[@]}; i++)); do
        sum_ref=$((sum_ref + sent[i]))
    done
}

get_network_usage()
{
    read_network_usage usage_sum

    usage=$((usage_sum - usage_base))

    usage_base=$usage_sum


    if [[ $max_usage -lt $usage ]]; then
        max_usage=$usage
    fi

    find_new_max=0;
    if [[ $max_usage -lt ${past_usage[14]} ]]; then
        find_new_max=1
    fi

    for ((i=14; i>0; i--)); do
        past_usage[i]=${past_usage[i-1]}
    done
    past_usage[0]=$usage

    if [[ $find_new_max -gt 0 ]]; then
        max_usage=1
        for ((i=0; i<15; i++)); do
            if [[ $max_usage -lt ${past_usage[i]} ]]; then
            max_usage=${past_usage[i]}
            fi
        done
    fi

    y=()

    for ((j=0; j<15; j++)); do
        x=${past_usage[j]}

        x=$(echo "scale=1; ($x / $max_usage) * 10" | bc)
        x=$(echo "$x / 1" | bc)
        y+=($x)
    done

    for ((i=10; i>0; i--)); do
    echo -n "@"
        for ((j=0; j<15; j++)); do
            echo -n " "
            x=${y[j]}

            if [[ $x -ge $i ]]; then
                echo -n "#"
            else
                echo -n " "
            fi
        done
        echo ""
    done
    
    for ((i=0; i<=30; i++)); do
        echo -n "@"
    done

    echo ""
}

core_base=()
core_data=()

read_cpu_usage()
{
    declare -n arr_ref=$1

    arr_ref=()

    local count=2
    local line=$(awk -v cnt="$count" 'NR==cnt' /proc/stat)
    local line_split=($line)

    while [[ $line_split[0] == cpu* ]]; do
        arr_ref+=("$line")
        count=$((count + 1))
        line=$(awk -v cnt="$count" 'NR==cnt' /proc/stat)
        line_split=($line)
    done
}

get_cpu_usage()
{
    read_cpu_usage core_data

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

        echo -e "${data[0]} usage:\t$usage%"

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
    file="/sys/class/power_supply/BAT0/uevent"

    if [[ -e $file ]]; then
        total=$(awk 'NR==10' /sys/class/power_supply/BAT0/uevent)
        current=$(awk 'NR==12' /sys/class/power_supply/BAT0/uevent)

        x=$(echo ${total:24})
        y=$(echo $x | awk '{print $1+0}')
        #power=$((total * 100 / current))
        echo -e "batteryStatus:\t$x"
    fi
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


main()
{
    read_cpu_usage core_base
    read_network_usage usage_base

    while true; do
        sleep 1
        clear

        get_network_usage
        get_cpu_usage
        get_uptime
        get_power_info
        get_loadavg_info
        get_mem_info
        
    done
}

main
