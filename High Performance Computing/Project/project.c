#include <stdio.h>
#include <stdlib.h>

#define MAX_RULES 2000001
#define RULE_SIZE 11
#define INPUT_SIZE 10
#define RULES_FILE "rules.csv"
#define INPUTS_FILE "debug_input_transactions.csv"



FILE * inputsFile;
FILE * rulesFile;
int ** rules;
int totalRules=MAX_RULES;

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
	
	int i,j;

	rules = malloc(sizeof(int*)*MAX_RULES);
	
	for(i=0;i<MAX_RULES;i++){
		rules[i] = malloc(sizeof(int) * RULE_SIZE);
	}


	rulesFile = fopen(RULES_FILE,"r");

	i = 0;

	while(fscanf(rulesFile,"%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",&rules[i][0],&rules[i][1],&rules[i][2],&rules[i][3],&rules[i][4],&rules[i][5],&rules[i][6],&rules[i][7],&rules[i][8],&rules[i][9],&rules[i][10]) != -1){
		i++;
		
	}
	totalRules = i;

	fclose(rulesFile);
			

	inputsFile = fopen(INPUTS_FILE,"r");
	
	int *input;
	input = malloc(sizeof(int)* RULE_SIZE);
	int read = 1;

	
	
	while (fscanf(inputsFile,"%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",&input[0],&input[1],&input[2],&input[3],&input[4],&input[5],&input[6],&input[7],&input[8],&input[9]) != -1) {


		for(j=0;j<totalRules;j++){
			if (compareVector(input,rules[j])!= -1){
				printVectorRule(input,rules[j]);
			}

		}


	}
	
	fclose(inputsFile);
system("pause");
}
