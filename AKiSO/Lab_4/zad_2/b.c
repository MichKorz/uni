#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

int main() {
    pid_t pid = 1;

    printf("Wysyłam sygnał SIGKILL do procesu %d\n", pid);
    if (kill(pid, SIGKILL) == -1) {
        perror("Nie udało się wysłać sygnału");
        exit(1);
    }

    return 0;
}