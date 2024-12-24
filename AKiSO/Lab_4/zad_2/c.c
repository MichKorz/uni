#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void handle_sigusr1(int sig) {
    static int count = 0;
    count++;
    printf("Otrzymano SIGUSR1. Liczba: %d\n", count);
}

int main() {
    signal(SIGUSR1, handle_sigusr1);

    // Wysyłamy kilka sygnałów SIGUSR1
    pid_t pid = getpid();
    for (int i = 0; i < 100; i++) {
        kill(pid, SIGUSR1);
    }

    sleep(10);

    return 0;
}