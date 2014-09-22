%{

/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#include "estruturas.h"
#include "funcoes.h"
#include "mostrar.h"
#include "semantica.h"
#include "tabelaSimbolos.h"
#include "tradutor.h"

#include<stdio.h>

void yyerror (char *s);
int interpretador = 0;
is_block* my_program = NULL;
int erros = 0;
char** argvalue;

%}

%left  	    OP2             /* "<" | ">" | "=" | "<>" | "<=" | ">=" */
%left       OP3 OP1b             /* "+" "-"  "or"  */
%left       OP4 OP1a         /* "*" "/" "MOD" "DIV" "AND"*/
%right      NOT             /* "NOT "*/
%right      IF THEN ELSE
%left       LPAREN 

%union {
	char* id;
	char* op1a;
	char* op1b; 
	char* op2; 
	char* op3; 
	char* op4; 
	
	int digseq;
	double realnumber; 
	is_expression* ie;
	is_rep_expression* re;
	is_statement* sttm;
	is_statement_list* sttml;
	is_function_block* fb;
	is_var_dec_list* vdl;
	is_var_dec* vd;
	is_id_list* idl;
	is_rep_var_dec* rvd;
	is_function_definition* fd;
	is_function_heading* fh;
	is_rep_formal_args* rfa;
	is_formal_args* fa;
	is_function_declaration* fdec;
	is_rep_dec_def* rdd;
	is_block* bk;
	is_block* st;
}


%token BEGI AND ARRAY ASSIGN COLON COMMA DIV DO DOT DOTDOT DOWNTO 
%token ELSE END FOR FORWARD FUNCTION IF LBRAC LPAREN MOD NOT OF OR PARAMSTR PROGRAM     
%token RBRAC RPAREN THEN SEMICOLON TO VAL VAR WHILE WRITELN RESERVED

//Associação de tipos de dados aos tokens
%token <id> ID
%token <op1a> OP1a
%token <op1b> OP1b
%token <op2> OP2
%token <op3> OP3
%token <op4> OP4


%token <digseq> DIGSEQ 
%token <realnumber> REALNUMBER

%type <ie>Expression
%type <re>RepCommaExpression

%type <sttm>Statement
%type <sttml>StatementList
%type <fb>FunctionBlock
%type <vdl>VariableDeclarationList
%type <vd>VariableDeclaration
%type <idl>IDList
%type <rvd>RepVariableDeclaration
%type <fd>FunctionDefinition
%type <fh>FunctionHeading
%type <rfa> RepCommaFormalArgs
%type <fa>FormalArgs
%type <st>Start
%type <bk>Block
%type <rdd>RepDeclarationDefinition
%type <fdec>FunctionDeclaration

%% 

Start: 					  PROGRAM ID SEMICOLON Block DOT						{$$ = $4; $$->id = $2; my_program = $$;}
					;

Block:	 				  VariableDeclarationList RepDeclarationDefinition BEGI StatementList END	{$$ = insert_block1($1,$2,$4);}
					| 			  RepDeclarationDefinition BEGI StatementList END	{$$ = insert_block2($1,$3);}
					;

RepDeclarationDefinition: 		  RepDeclarationDefinition FunctionDeclaration					{$$ = insert_rep_dec($1,$2);}
					| RepDeclarationDefinition FunctionDefinition					{$$ = insert_rep_def($1,$2);}
					| 										{$$ = NULL;}
					;	

FunctionDefinition: 		  	FunctionHeading SEMICOLON FunctionBlock SEMICOLON				{$$ = insert_function_definition($1,$3);}
					;

FunctionDeclaration: 		  	FunctionHeading SEMICOLON FORWARD SEMICOLON					{$$ = insert_function_heading3($1);}
					;


FunctionHeading: 			  FUNCTION ID LPAREN RPAREN COLON ID						{$$= insert_function_heading($2,$6);}
					| FUNCTION ID LPAREN RepCommaFormalArgs RPAREN COLON ID				{$$= insert_function_heading2($2,$4,$7);}
					;

RepCommaFormalArgs:			  FormalArgs										{$$ = insert_rep_formal_args_list(NULL,$1);}
					| RepCommaFormalArgs SEMICOLON FormalArgs						{$$ = insert_rep_formal_args_list($1,$3);}
					;
		

FormalArgs: 				      IDList COLON          ID							{$$= insert_formal_args($1,$3,1);}
        				| VAR IDList COLON          ID							{$$= insert_formal_args($2,$4,2);}
       			 		|     IDList COLON ARRAY OF ID							{$$= insert_formal_args($1,$5,3);}
        				| VAR IDList COLON ARRAY OF ID							{$$= insert_formal_args($2,$6,4);}
        				;
        				

FunctionBlock: 				  BEGI StatementList END							{$$ = insert_bstatement_list($2);}
					| VariableDeclarationList BEGI StatementList END				{$$ = insert_var_bstatement_list($1,$3);}
					;

VariableDeclarationList:		  VAR VariableDeclaration							{$$ = insert_var_dec($2);}
					| VAR VariableDeclaration RepVariableDeclaration				{$$ = insert_var_dec_rep($2, $3);}
					;
							
VariableDeclaration: 			  IDList COLON ID SEMICOLON							{$$ = insert_idl_id($1,$3);}
					| IDList COLON ARRAY LBRAC DIGSEQ DOTDOT DIGSEQ RBRAC OF ID SEMICOLON		{$$ = insert_idl_id_2($1,$5,$7,$10);}
					;

RepVariableDeclaration: 		  VariableDeclaration								{$$ = insert_rep_var_dec(NULL,$1);}
					| RepVariableDeclaration VariableDeclaration					{$$ = insert_rep_var_dec($1,$2);}
					;
					
IDList: 				  ID 										{$$ = insert_id_list(NULL,$1);}
					| IDList COMMA ID								{$$ = insert_id_list($1,$3);}
        				;


StatementList: 				  Statement									{$$ = insert_statement_list(NULL,$1);}
					| StatementList SEMICOLON Statement						{$$ = insert_statement_list($1,$3);}
					;


		
Statement: 				  BEGI StatementList END							{$$ = insert_begin_statementlist_end($2);}
					| ID ASSIGN Expression								{$$ = insert_id_assign_expression($1,$3);}
					| ID LBRAC Expression RBRAC ASSIGN Expression					{$$ = insert_id_exp_exp($1,$3,$6);}
					| IF Expression THEN Statement							{$$ = insert_exp_stt($2,$4);}
					| IF Expression THEN Statement ELSE Statement					{$$ = insert_exp_stt_stt($2,$4,$6);}
					| WHILE Expression DO Statement							{$$ = insert_wexp_stt($2,$4);}
					| FOR ID ASSIGN Expression TO Expression DO Statement				{$$ = insert_id_exp_exp_stt($2,$4,$6,$8);}
					| FOR ID ASSIGN Expression DOWNTO Expression DO Statement 			{$$ = insert_id_exp_dexp_stt($2,$4,$6,$8);}
					| VAL LPAREN PARAMSTR LPAREN Expression RPAREN COMMA ID RPAREN			{$$ = insert_tokens_exp_id($5,$8);}
					| WRITELN LPAREN Expression RPAREN						{$$ = insert_writeln_exp($3);}
					|										{$$ = NULL;}
					;
					
					
					
Expression: 		                  DIGSEQ                  			     				{$$ = insert_DIGSEQ($1);}
					| REALNUMBER              			  		    		{$$ = insert_REALNUMBER($1);}
					| Expression OP1a Expression 							{$$ = insert_OP1($1, $2, $3);}
					| Expression OP1b Expression 							{$$ = insert_OP1($1, $2, $3);}	
					| Expression OP2 Expression							{$$ = insert_OP2($1, $2, $3);}
					| Expression OP3 Expression							{$$ = insert_OP3($1, $2, $3);}
					| Expression OP4 Expression							{$$ = insert_OP4($1, $2, $3);}
					| OP3 Expression								{$$ = insert_OP3_expression($1,$2);}
					| NOT Expression								{$$ = insert_NOT_expression($2);}
					| LPAREN Expression RPAREN							{$$ = $2;}
					| ID LBRAC Expression RBRAC							{$$ = insert_id_lbrac_expression_rbrac($1,$3);}
					| ID LPAREN RPAREN 								{$$ = insert_id_lparen_rep_expression_rparen($1,NULL);}
					| ID LPAREN RepCommaExpression RPAREN						{$$ = insert_id_lparen_rep_expression_rparen($1,$3);}
					| ID										{$$ = insert_id($1);}
					;

RepCommaExpression:		    	  Expression									{$$ = insert_rep_expression(NULL,$1);}
					| RepCommaExpression COMMA Expression						{$$ = insert_rep_expression($1,$3);}
					;					
											
					
%%
int main(int argc,char* argv[])
{
	line = 1;
	yyparse();
	if (argc == 2 && strcmp(argv[1],"-t")==0){
		if (my_program != NULL)
			show_program(my_program);
	}
	else if (argc == 2 && strcmp(argv[1],"-s")==0){
		if (my_program != NULL){
			verifica_program(my_program);	
			verifica_se_todas_funcoes_estao_definidas();	
			if (erros==0){
				show_table();
			}
				
	
		}
	}
	else{
	
		if (my_program != NULL){
			interpretador = 0;
			argvalue = argv;
			verifica_program(my_program);	
			verifica_se_todas_funcoes_estao_definidas();	
			translate(my_program);		
	}
	}
	
	return 0;
	
}


