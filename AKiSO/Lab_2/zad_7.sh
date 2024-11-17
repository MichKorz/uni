#!/bin/bash

# Directory to process; defaults to the current directory if none specified
directory="${1:-.}"

# Loop through all files in the directory
for file in "$directory"/*; do
    # Check if it's a file (skip directories)
    if [ -f "$file" ]; then
        # Get the lowercase version of the filename
        lowercase_file="$(echo "$(basename "$file")" | tr '[:upper:]' '[:lower:]')"

        # Only rename if the lowercase version is different
        if [ "$file" != "$directory/$lowercase_file" ]; then
            mv "$file" "$directory/$lowercase_file"
            echo "Renamed '$file' to '$lowercase_file'"
        fi
    fi
done

