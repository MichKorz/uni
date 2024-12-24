#ifndef INPUT_HANDLING_H
#define INPUT_HANDLING_H

char *lsh_read_line(void);

char** split_line(const char* input, int* count);

void free_split_line(char** strings);

char*** lsh_split_command(char** commands, int command_count);

char** get_redirections(char** command, char** input_file, char** output_file, char** error_file);

#endif