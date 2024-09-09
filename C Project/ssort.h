#ifndef SSORT_H
#define SSORT_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_WORD_LENGTH 100

void sort_words(char **words, int n, const char *sorttype);
void print_words(char **words, int n);
int read_words(const char *filename, char **words, int n, const char *wtype, char **skipwords, int skipcount);
int is_valid_word(const char *word, const char *wtype);

#endif
