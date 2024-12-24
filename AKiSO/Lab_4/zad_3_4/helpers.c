#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

void redirect(char ** input_file, char ** output_file, char ** error_file)
{
    // Handle input redirection
    if (*input_file) 
    {
        int fd = open(*input_file, O_RDONLY);
        if (fd < 0) {
            perror("Failed to open input file");
            exit(1);
        }
        dup2(fd, STDIN_FILENO);
        close(fd);
    }

    // Handle output redirection
    if (*output_file) 
    {
        int fd = open(*output_file, O_WRONLY | O_CREAT | O_TRUNC, 0644);
        if (fd < 0) {
            perror("Failed to open output file");
            exit(1);
        }
        dup2(fd, STDOUT_FILENO);
        close(fd);
    }

    // Handle error redirection
    if (*error_file) 
    {
        int fd = open(*error_file, O_WRONLY | O_CREAT | O_TRUNC, 0644);
        if (fd < 0) {
            perror("Failed to open error file");
            exit(1);
        }
        dup2(fd, STDERR_FILENO);
        close(fd);
    }
}