#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/*
sudo chown root:root zad_1    # Change the owner to root
sudo chmod u+s zad_1         # Set the setuid bit
*/

int main() {
    if (setuid(0) == -1) {
        perror("Failed to setuid to root");
        return 1;
    }

    execl("/bin/bash", "bash", NULL);

    perror("Failed to execute shell");
    return 1;
}
