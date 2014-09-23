#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define MAX_RULES 2000001
#define RULE_SIZE 11
#define INPUT_SIZE 10
#define RULES_FILE "rules.csv"
#define INPUTS_FILE "debug_input_transactions.csv"
#define MAX_DIFF 10000


FILE * inputsFile;
FILE * rulesFile;
int ** rules;
int totalRules=MAX_RULES;
time_t tstart, tend;
int * ruleStartIndex;

int comparefun2(const void* a, const void* b) {

  int** da = (int**)a;

  int** db = (int**)b;
  
  return da[0][0]-db[0][0];

}

int compareVector(int* input, int* rule){
	/*Given 1 input and 1 rules returns the rule if belongs to it or else -1*/
	int i;
	
	for (i=0;i<INPUT_SIZE;i++){
		if(input[i] == rule[i] || rule[i] == 0){
			
		}
		else{
			return -1;
		}
	}

	return rule[RULE_SIZE-1];
}

void printVectorRule(int * vec, int* rule){

	int i;
	
	for (i=0;i<INPUT_SIZE;i++){
		printf("%d,",vec[i]);
	}
	printf("%d\n",rule[RULE_SIZE-1]);
}

int main(){
		
	printf("before sorting\n");

	int i,j;

	ruleStartIndex = malloc(sizeof(int)*MAX_DIFF);

	rules = malloc(sizeof(int*)*MAX_RULES);
	
	for(i=0;i<MAX_RULES;i++){
		rules[i] = malloc(sizeof(int) * RULE_SIZE);
	}

	
	printf("before sorting\n");
	rulesFile = fopen(RULES_FILE,"r");

	i = 0;

	while(fscanf(rulesFile,"%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",&rules[i][0],&rules[i][1],&rules[i][2],&rules[i][3],&rules[i][4],&rules[i][5],&rules[i][6],&rules[i][7],&rules[i][8],&rules[i][9],&rules[i][10]) != -1){
		i++;
		
	}
	totalRules = i;

	fclose(rulesFile);
			

	
	/*
	sorting

	*/
	
	printf("before sorting\n");
	qsort(rules,totalRules,sizeof( int*),comparefun2);
	printf("after sorting\n");

	int valAnt = -1;
	int ind = 0;

	for (i=0;i<totalRules;i++){
		for (j=0;j<RULE_SIZE;j++){
			printf("%d ", rules[i][j]);

		}
		printf("\n");
	}

	for(i=0;i<5;i++){
		printf("%d\n",ruleStartIndex[i]);
	}

	inputsFile = fopen(INPUTS_FILE,"r");
	
	int *input;
	input = malloc(sizeof(int)* RULE_SIZE);


	
	tstart = time(0);
	int ntransactions = 0;
	while (fscanf(inputsFile,"%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",&input[0],&input[1],&input[2],&input[3],&input[4],&input[5],&input[6],&input[7],&input[8],&input[9]) != -1) {

		

		for(j=0;j<totalRules;j++){

			
			if (compareVector(input,rules[j])!= -1){
				printVectorRule(input,rules[j]);
			}

		}

		ntransactions++;
	}

	tend = time(0);
	printf("Trans/seg: %f\n",(float )ntransactions /difftime(tend, tstart));

	fclose(inputsFile);

system("pause");

	return 0;

}
