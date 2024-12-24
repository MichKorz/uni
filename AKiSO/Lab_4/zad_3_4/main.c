#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <signal.h>
#include <fcntl.h>

#include "input_handling.h"
#include "helpers.h"


/*
  Function Declarations for builtin shell commands:
 */
int lsh_cd(char **args);
int lsh_exit(char **args);

/*
  List of builtin commands, followed by their corresponding functions.
 */
char *builtin_str[] = {
  "cd",
  "exit"
};

int (*builtin_func[]) (char **) = {
  &lsh_cd,
  &lsh_exit
};

int lsh_num_builtins() {
  return sizeof(builtin_str) / sizeof(char *);
}

/*
  Builtin function implementations.
*/
int lsh_cd(char **args)
{
  if (args[1] == NULL) {
    fprintf(stderr, "lsh: expected argument to \"cd\"\n");
  } else {
    if (chdir(args[1]) != 0) {
      perror("lsh");
    }
  }
  return 1;
}

int lsh_exit(char **args)
{
  return 0;
}

int check_for_amp(char **args) {
    if (args == NULL) return 0; // Safety check for NULL input

    int i = 0;
    while (args[i] != NULL) { // Find the last non-NULL element
        i++;
    }

    // i now points to the size of the array, so the last word is args[i-1]
    if (i > 0 && strcmp(args[i - 1], "&") == 0) {
        args[i-1] = NULL;
        return 1; // Last word is "&"
    }
    return 0; // Last word is not "&"
}

//TODO Ctrl+C kills only currently run task, not shell istelf
pid_t cpid;

void kill_current_process(int signum)
{
    // Sending SIGKILL (signal 9) to the process with the given PID
    if (kill(cpid, SIGKILL) == -1) 
    {
        perror("Error sending SIGKILL\n");
        return;
    }
    printf("\n");
   return;
}

//TODO Zombie prevention
pid_t children[10] = {0};
int child_count = 0;



int lsh_launch(char **command)
{
  pid_t pid, wpid;
  int status;

  char* input_file = 0;
  char* output_file = 0;
  char* error_file = 0;

  char** args = get_redirections(command, &input_file, &output_file, &error_file);

  //TODO check for &
  int wait = check_for_amp(args);

  pid = fork();
  if (pid == 0) 
  {
    // Child process

    redirect(&input_file, &output_file, &error_file);

    setsid();
    if (execvp(args[0], args) == -1) 
    {
      perror("lsh");
    }
    exit(EXIT_FAILURE);
  } 
  else if (pid < 0) 
  {
    // Error forking
    perror("lsh");
  } 
  else 
  {
    // Parent process
    if (!wait)
    {
        cpid = pid;
        do 
        {
            wpid = waitpid(pid, &status, WUNTRACED);
        } 
        while (!WIFEXITED(status) && !WIFSIGNALED(status));
    }
    else
    {
        children[child_count] = pid;
        child_count++;
        return 1;
    }
  }

  return 1;
}

int lsh_execute(char **args)
{
  int i;

  if (args[0] == NULL) {
    // An empty command was entered.
    return 1;
  }

  for (i = 0; i < lsh_num_builtins(); i++) {
    if (strcmp(args[0], builtin_str[i]) == 0) {
      return (*builtin_func[i])(args);
    }
  }

  return lsh_launch(args);
}

//TODO zombie prevention implementation
void kill_zombies()
{
  for (int i = 0; i < child_count; i++)
  {
    pid_t pid = children[i];  
    if (pid == 0) continue;
    int status;

    if (waitpid(pid, &status, WNOHANG) > 0) children[i] = 0;
  }
}

int lsh_execute_chain(char*** commands, int command_count)
{
  int pipefd[2];
  pid_t pid;
  int in_fd = 0;

  for (int i = 0; i < command_count; i++) 
  {
    char ** command = commands[i];

    char* input_file = 0;
    char* output_file = 0;
    char* error_file = 0;

    char** args = get_redirections(command, &input_file, &output_file, &error_file);

    pipe(pipefd);

    if ((pid = fork()) == 0) 
    {
      redirect(&input_file, &output_file, &error_file);
      // Child process
      if (i != 0) dup2(in_fd, STDIN_FILENO); // Redirect input
      if (i < command_count - 1) 
      {
        dup2(pipefd[1], STDOUT_FILENO); // Redirect output
      }
      close(pipefd[0]); // Close unused read end
      if (execvp(args[0], args) == -1) 
      {
        perror("lsh");
      }
      exit(EXIT_FAILURE);
    } 
    else 
    {
      // Parent process
      wait(NULL); // Wait for child
      close(pipefd[1]); // Close unused write end
      in_fd = pipefd[0]; // Save read end for next command
    }
  }

  return 1;
}

void lsh_loop(void)
{
  char *line;
  char **splited_line;
  int command_count;
  char ***commands;
  int status;

  do {
    printf("> ");
    line = lsh_read_line();
    //TODO evicting zombies and ending upon EOF
    kill_zombies();
    if (line[0] == EOF)
    {
        printf("\n");
        free(line);
        break;
    }
    // TODO insert pipe separator
    splited_line = split_line(line, &command_count);
    commands = lsh_split_command(splited_line, command_count);

    if (command_count == 1) status = lsh_execute(commands[0]);
    else status = lsh_execute_chain(commands, command_count);

    free(line);
    free(commands);
  } 
  while (status);
}

int main(int argc, char **argv)
{
  // Load config files, if any.
  //signal(SIGINT, kill_current_process);
  if (signal(SIGINT, kill_current_process) == SIG_ERR) {
        perror("Failed to set signal handler");
        return 1;
  }

  // Run command loop.
  lsh_loop();

  // Perform any shutdown/cleanup.

  return EXIT_SUCCESS;
}
