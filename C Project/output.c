#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ssort.h"

int compare_asc(const void *a, const void *b) {
    return strcmp(*(const char **)a, *(const char **)b);
}

int compare_desc(const void *a, const void *b) {
    return strcmp(*(const char **)b, *(const char **)a);
}

void sort_words(char **words, int count, const char *sorttype) {
    if (strcmp(sorttype, "ASC") == 0) {
        qsort(words, count, sizeof(char *), compare_asc);
    } else {
        qsort(words, count, sizeof(char *), compare_desc);
    }
}

void print_words(char **words, int count) {
    for (int i = 0; i < count; i++) {
        printf("%s", words[i]);
        if ((i + 1) % 10 == 0) {
            printf("\n");
        } else {
            printf(" ");
        }
    }
    if (count % 10 != 0) {
        printf("\n");
    }
}
