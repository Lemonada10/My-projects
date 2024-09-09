#include <stdio.h>
#include <string.h>
#include "ssort.h"
#include "wordtype.h"

void split_alpha_words(const char *word, char **words, int *count, int max_count, char **skipwords, int skipcount) {
    char temp[MAX_WORD_LENGTH];
    int temp_index = 0;
    for (int i = 0; i < strlen(word); i++) {
        if (isalpha(word[i])) {
            temp[temp_index++] = word[i];
        } else {
            if (temp_index > 0) {
                temp[temp_index] = '\0';
                int skip = 0;
                for (int j = 0; j < skipcount; j++) {
                    if (strcmp(temp, skipwords[j]) == 0) {
                        skip = 1;
                        break;
                    }
                }
                if (!skip && *count < max_count) {
                    strncpy(words[*count], temp, MAX_WORD_LENGTH - 1);
                    words[*count][MAX_WORD_LENGTH - 1] = '\0'; 
                    (*count)++;
                }
                temp_index = 0;
            }
        }
    }
    if (temp_index > 0) {
        temp[temp_index] = '\0';
        int skip = 0;
        for (int j = 0; j < skipcount; j++) {
            if (strcmp(temp, skipwords[j]) == 0) {
                skip = 1;
                break;
            }
        }
        if (!skip && *count < max_count) {
            strncpy(words[*count], temp, MAX_WORD_LENGTH - 1);
            words[*count][MAX_WORD_LENGTH - 1] = '\0'; 
            (*count)++;
        }
    }
}

void split_alphanum_words(const char *word, char **words, int *count, int max_count, char **skipwords, int skipcount) {
    char temp[MAX_WORD_LENGTH];
    int temp_index = 0;
    int has_alpha = 0;
    int has_num = 0;
    
    for (int i = 0; i < strlen(word); i++) {
        if (isalnum(word[i])) {
            temp[temp_index++] = word[i];
            if (isalpha(word[i])) has_alpha = 1;
            if (isdigit(word[i])) has_num = 1;
        } else {
            if (temp_index > 0 && has_alpha && has_num) {
                temp[temp_index] = '\0';
                int skip = 0;
                for (int j = 0; j < skipcount; j++) {
                    if (strcmp(temp, skipwords[j]) == 0) {
                        skip = 1;
                        break;
                    }
                }
                if (!skip && *count < max_count) {
                    strncpy(words[*count], temp, MAX_WORD_LENGTH - 1);
                    words[*count][MAX_WORD_LENGTH - 1] = '\0'; 
                    (*count)++;
                }
                temp_index = 0;
                has_alpha = 0;
                has_num = 0;
            } else {
                temp_index = 0;
                has_alpha = 0;
                has_num = 0;
            }
        }
    }
    if (temp_index > 0 && has_alpha && has_num) {
        temp[temp_index] = '\0';
        int skip = 0;
        for (int j = 0; j < skipcount; j++) {
            if (strcmp(temp, skipwords[j]) == 0) {
                skip = 1;
                break;
            }
        }
        if (!skip && *count < max_count) {
            strncpy(words[*count], temp, MAX_WORD_LENGTH - 1);
            words[*count][MAX_WORD_LENGTH - 1] = '\0'; 
            (*count)++;
        }
    }
}

int read_words(const char *filename, char **words, int n, const char *wtype, char **skipwords, int skipcount) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        fprintf(stderr, "Error opening file %s\n", filename);
        return -1;
    }

    char word[MAX_WORD_LENGTH];
    int count = 0;
    while (fscanf(file, "%s", word) != EOF && count < n) {
        char *token = strtok(word, ",");
        while (token != NULL) {
            if (strcmp(wtype, "ALPHA") == 0) {
                split_alpha_words(token, words, &count, n, skipwords, skipcount);
            } else if (strcmp(wtype, "ALPHANUM") == 0) {
                split_alphanum_words(token, words, &count, n, skipwords, skipcount);
            } else if (is_valid_word(token, wtype)) {
                int skip = 0;
                for (int i = 0; i < skipcount; i++) {
                    if (strcmp(token, skipwords[i]) == 0) {
                        skip = 1;
                        break;
                    }
                }
                if (!skip && count < n) {
                    strncpy(words[count], token, MAX_WORD_LENGTH - 1);
                    words[count][MAX_WORD_LENGTH - 1] = '\0'; 
                    count++;
                }
            }
            token = strtok(NULL, ",");
        }
    }

    fclose(file);
    return count;
}
