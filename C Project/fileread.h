#ifndef FILEREAD_H
#define FILEREAD_H

int read_words(const char *filename, char **words, int n, const char *wtype, char **skipwords, int skipcount);
void split_alpha_words(const char *word, char **words, int *count, int max_count, char **skipwords, int skipcount);
void split_alphanum_words(const char *word, char **words, int *count, int max_count, char **skipwords, int skipcount);

#endif
