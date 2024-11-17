#!/bin/bash

# pid - process ID
# comm - command name
# state - state (R: running, S: sleeping, D: uninterruptible sleep, Z: zombie, T: traced or stopped)
# ppid - parent process ID
# pgrp - process group ID
# session - session ID
# tty_nr - controlling terminal
# rss - resident set size
# lsof - number of open file descriptors - via /proc/PID/fd

(echo -e "PPID\tPID\tCOMM\tSTATE\tTTY\tRSS\tPGID\tSID\tOPEN_FILES"

for pid in $(ls /proc | grep '^[0-9]'); do
    if [[ -f /proc/$pid/stat && -d /proc/$pid/fd ]]; then

        ppid=$(awk '/^PPid:/ {print $2}' /proc/$pid/status)
        comm=$(awk '/^Name:/ {print $2}' /proc/$pid/status)
        comm="${comm//[()]/}"
        state=$(awk '/^State:/ {print $2}' /proc/$pid/status)
        tty=$(awk '{print $7}' /proc/$pid/stat)
        rss=$(awk '/^VmRSS:/ {print $2}' /proc/$pid/status)
        pgid=$(awk '{print $5}' /proc/$pid/stat)
        sid=$(awk '{print $6}' /proc/$pid/stat)

        open_files=$(ls /proc/$pid/fd | wc -l)

        rss=${rss:-0}
        tty=${tty:-'?'}
        ppid=${ppid:-'?'}

        echo -e "$ppid\t$pid\t$comm\t$state\t$tty\t$rss\t$pgid\t$sid\t$open_files"
    fi
done) | sort -k2 -n | column -t -s $'\t'