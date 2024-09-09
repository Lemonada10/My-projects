#include "ssort.h"
#include <ctype.h>
#include <string.h>

int is_valid_word(const char *word, const char *wtype) {
    if (strcmp(wtype, "ALPHA") == 0) {
        for (int i = 0; i < strlen(word); i++) {
            if (!isalpha(word[i])) {
                return 0;
            }
        }
    } else if (strcmp(wtype, "ALPHANUM") == 0) {
        int has_alpha = 0, has_num = 0;
        for (int i = 0; i < strlen(word); i++) {
            if (isalpha(word[i])) {
                has_alpha = 1;
            }
            if (isdigit(word[i])) {
                has_num = 1;
            }
            if (!isalnum(word[i])) {
                return 0; 
            }
        }
        if (!(has_alpha && has_num)) {
            return 0;
        }
    } else if (strcmp(wtype, "ALL") == 0) {
        
        return 1;
    }
    return 1;
}
