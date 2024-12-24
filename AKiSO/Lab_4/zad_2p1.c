#include <signal.h>
#include <unistd.h>
#include <stdio.h>

void handle_signal(int signum)
{
    printf("Sinal cought %d\n", signum);
}

int main()
{
    signal(SIGINT, handle_signal);

    printf("%d", getpid());

    while(1)
    {
        pause();
    }

    return 0;
}