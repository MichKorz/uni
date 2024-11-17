#include <stdio.h>

int main() {
    
    for (int r = 0; r <= 255; r++)
    {
        for (int g = 0; g <= 255; g++)
        {
            for (int b = 0; b <= 255; b++)
            {
                printf("\033[38;2;%d;%d;%dmHello World!", r, g, b);
            }
        }
    }

    printf("\033[0m");

    return 0;
}