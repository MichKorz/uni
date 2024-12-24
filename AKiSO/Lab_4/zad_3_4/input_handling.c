#include <stdlib.h>
#include <stdio.h>
#include <string.h>


#define LSH_RL_BUFSIZE 1024
char *lsh_read_line(void)
{
  int bufsize = LSH_RL_BUFSIZE;
  int position = 0;
  char *buffer = malloc(sizeof(char) * bufsize);
  int c;

  if (!buffer) {
    fprintf(stderr, "lsh: allocation error\n");
    exit(EXIT_FAILURE);
  }

  while (1) {
    // Read a character
    c = getchar();

    if (c == EOF) {
        buffer[0] = EOF;
        return buffer;
    }
    if (c == '\n') {
      buffer[position] = '\0';
      return buffer;
    } else {
      buffer[position] = c;
    }
    position++;

    // If we have exceeded the buffer, reallocate.
    if (position >= bufsize) {
      bufsize += LSH_RL_BUFSIZE;
      buffer = realloc(buffer, bufsize);
      if (!buffer) {
        fprintf(stderr, "lsh: allocation error\n");
        exit(EXIT_FAILURE);
      }
    }
  }
}

char** split_line(const char* input, int* count) {

    const char* delimiter = "|";

    // Copy the input string because strtok modifies the string it processes
    char* input_copy = strdup(input);
    if (!input_copy) {
        perror("Failed to allocate memory");
        exit(EXIT_FAILURE);
    }

    // First pass: Count the number of tokens
    char* token = strtok(input_copy, delimiter);
    int num_tokens = 0;
    while (token) {
        num_tokens++;
        token = strtok(NULL, delimiter);
    }

    // Allocate memory for the result array
    char** result = malloc((num_tokens + 1) * sizeof(char*));
    if (!result) {
        perror("Failed to allocate memory");
        free(input_copy);
        exit(EXIT_FAILURE);
    }

    // Reset the input_copy for the second pass
    strcpy(input_copy, input);

    // Second pass: Extract tokens
    int index = 0;
    token = strtok(input_copy, delimiter);
    while (token) {
        result[index++] = strdup(token);
        token = strtok(NULL, delimiter);
    }

    // Null-terminate the array of strings
    result[index] = NULL;

    // Cleanup and return
    free(input_copy);
    if (count) {
        *count = num_tokens;
    }
    return result;
}

void free_split_line(char** strings) {
    if (!strings) return;

    for (int i = 0; strings[i] != NULL; i++) {
        free(strings[i]);
    }
    free(strings);
}

#define LSH_TOK_BUFSIZE 64
#define LSH_TOK_DELIM " \t\r\n\a"
char ***lsh_split_command(char **commands, int command_count)
{
    char ***args = malloc((command_count) * sizeof(char**));

    for (int i = 0; i < command_count; i++)
    {
        int tokbufsize = LSH_TOK_BUFSIZE, position = 0;
        char **tokens = malloc(tokbufsize * sizeof(char*));
        char *token;

        if (!tokens) 
        {
            fprintf(stderr, "lsh: allocation error\n");
            exit(EXIT_FAILURE);
        }

        token = strtok(commands[i], LSH_TOK_DELIM);
        while (token != NULL) 
        {
            tokens[position] = token;
            position++;

            if (position >= tokbufsize) 
            {
                tokbufsize += LSH_TOK_BUFSIZE;
                tokens = realloc(tokens, tokbufsize * sizeof(char*));
                if (!tokens) {
                fprintf(stderr, "lsh: allocation error\n");
                exit(EXIT_FAILURE);
                }
            }

            token = strtok(NULL, LSH_TOK_DELIM);
        }
        tokens[position] = NULL;
        args[i] = tokens;
    }
    return args;
}

char** get_redirections(char** command, char** input_file, char** output_file, char** error_file)
{
  //, char* input, char* output, char* err

  int position = 0;
  int token_count = 0;
  while (command[position] != NULL)
  {
    position++;
    token_count++;
  }

  if (token_count < 3) return command;

  char* input = "<";
  char* output = ">";
  char* err = "2>";

  int start = 0;
  int end = token_count;

  char** args = malloc(token_count * sizeof(char**) + 1); //Space for null termination

  if (strcmp(command[1], input) == 0)
  {
    start = 2;
    *input_file = command[0];
  }

  if (strcmp(command[token_count-2], output) == 0)
  {
    end -= 2;
    *output_file = command[token_count-1];
  }
  if (strcmp(command[token_count-2], err) == 0)
  {
    end -= 2;
    *error_file = command[token_count-1];
  }

  if (token_count > 3)
  {
    if (strcmp(command[token_count-4], output) == 0) 
    {
      end -= 2;
      *output_file = command[token_count-3];
    }
    if (strcmp(command[token_count-4], err) == 0)
    {
      end -= 2;
      *error_file = command[token_count-3];
    }
  }

  for (int i = start; i < end; i++)
  {
    args[i - start] = command[i];
  }

  args[end - start] = NULL;
  return args;
}