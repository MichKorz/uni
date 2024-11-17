#include "types.h"
#include "user.h"

int main(int argc, char *argv[]) 

{
    if (argc != 4) 
    {
        printf(2, "Usage: expr <val> <operator> <val>\n");
        exit();
    }

    int val1 = atoi(argv[1]);
    int val2 = atoi(argv[3]);
    char operator = argv[2][0];
    int result;

    switch (operator) 
    {
        case '+':
            result = val1 + val2;
            break;
        case '-':
            result = val1 - val2;
            break;
        case '*':
            result = val1 * val2;
            break;
        case '/':
            if (val2 == 0) {
                printf(2, "Devision by 0\n");
                exit();
            }
            result = val1 / val2;
            break;
        case '%':
            if (val2 == 0) {
                printf(2, "Devision by 0\n");
                exit();
            }
            result = val1 % val2;
            break;
        default:
            printf(2, "Error: Unknown operator '%c'\n", operator);
            exit();
    }

    printf(1, "%d\n", result);
    exit();
}