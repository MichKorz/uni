#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

void handle_signal(int sig) 
{
    printf("Otrzymano sygnał: %d\n", sig);
}

int main() 
{
    for (int i = 1; i < NSIG; i++) 
    {
        if (signal(i, handle_signal) == SIG_ERR) 
        {
            printf("Nie udało się zarejestrować obsługi dla sygnału %d\n", i);
        }
    }
    return 0;
}