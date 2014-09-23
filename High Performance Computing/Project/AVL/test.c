#include "avltree.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>


 
#define N 800
#define MAX_RULES 2000001
#define RULE_SIZE 11
#define INPUT_SIZE 10
#define RULES_FILE "rules.csv"
#define INPUTS_FILE "debug_input_transactions.csv"
#define MAX_DIFF 10000

main() {


 
  AvlTree T;
    Position P;
    int i;
    int j = 0;

    T = MakeEmpty(NULL);
 

	int *input = malloc(sizeof(int)* RULE_SIZE);
	FILE * rulesFile = fopen(RULES_FILE,"r");
	while (fscanf(rulesFile,"%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",&input[0],&input[1],&input[2],&input[3],&input[4],&input[5],&input[6],&input[7],&input[8],&input[9],&input[10]) != -1) {		
    		T = Insert(input, T);

	}

	double tstart, tstop, ttime;
	tstart = (double)clock()/CLOCKS_PER_SEC;
	
	int *transation = malloc(sizeof(int)* INPUT_SIZE);
	FILE * transFile = fopen(INPUTS_FILE,"r");
	int total = 0;
	while (fscanf(transFile,"%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",&transation[0],&transation[1],&transation[2],&transation[3],&transation[4],&transation[5],&transation[6],&transation[7],&transation[8],&transation[9]) != -1) {
		
		Find(transation, T);
		total++;
	}
	tstop = (double)clock()/CLOCKS_PER_SEC;

	ttime= tstop-tstart; /*ttime is how long your code run */
	printf("%f\n",total/ttime);


 /*
    for (i = 0; i < N; i++)
        if ((P = Find(i, T)) == NULL || Retrieve(P) != i)
            printf("Error at %d\n", i);
 
    printf("Min is %d, Max is %d\n", Retrieve(FindMin(T)),
            Retrieve(FindMax(T)));
 */
    return 0;
}
