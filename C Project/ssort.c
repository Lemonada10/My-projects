#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "wordtype.h"
#include "fileread.h"
#include "output.h"
#include "ssort.h"

int main(int argc, char *argv[]) {
    if (argc < 4) {
        fprintf(stderr, "Enter characteristics in this format: %s <inputfile> <n> <wtype> [<sorttype>] [<skipword1> <skipword2> ...]\n", argv[0]);
        return 1;
    }

    const char *inputfile = argv[1];
    int n = atoi(argv[2]);
    if (n <= 0) {
        fprintf(stderr, "Error: (n must be > 0).\n");
        return 1;
    }
    
    const char *wtype = argv[3];
    const char *sorttype = (argc > 4 && (strcmp(argv[4], "ASC") == 0 || strcmp(argv[4], "DESC") == 0)) ? argv[4] : "ASC";
    char **skipwords = (argc > 5) ? &argv[5] : NULL;
    int skipcount = (argc > 5) ? argc - 5 : 0;

    char **words = (char **)malloc(n * sizeof(char *));
    for (int i = 0; i < n; i++) {
        words[i] = (char *)malloc(MAX_WORD_LENGTH * sizeof(char));
    }

    int count = read_words(inputfile, words, n, wtype, skipwords, skipcount);
    if (count == -1) {
        for (int i = 0; i < n; i++) {
            free(words[i]);
        }
        free(words);
        return 1;
    }

    sort_words(words, count, sorttype);
    print_words(words, count);

    for (int i = 0; i < n; i++) {
        free(words[i]);
    }
    free(words);

    return 0;
}
