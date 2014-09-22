%{


/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/
#include <string.h>
#include "estruturas.h"
#include "y.tab.h"


void commenteof(){
 						printf ("Line %d: unexpected EOF inside comment\n",line);
}
%}

A				[aA]
B				[bB]
C				[cC]
D				[dD]
E				[eE]
F				[fF]
G				[gG]
H				[hH]
I				[iI]
J				[jJ]
K				[kK]
L				[lL]
M				[mM]
N				[nN]
O				[oO]
P				[pP]
Q				[qQ]
R				[rR]
S				[sS]
T				[tT]
U				[uU]
V				[vV]
W				[wW]
X				[xX]
Y				[yY]
Z				[zZ]

%%						

{C}{A}{S}{E}					return(RESERVED);
{C}{O}{N}{S}{T}					return(RESERVED);
{F}{I}{L}{E}					return(RESERVED);
{G}{O}{T}{O}					return(RESERVED);
{I}{N}						return(RESERVED);
{L}{A}{B}{E}{L}					return(RESERVED);
{M}{A}{I}{N}					return(RESERVED);
{N}{I}{L}					return(RESERVED);
{P}{A}{C}{K}{E}{D}				return(RESERVED);
{P}{R}{O}{C}{E}{D}{U}{R}{E}			return(RESERVED);
{R}{E}{C}{O}{R}{D}				return(RESERVED);
{R}{E}{P}{E}{A}{T}				return(RESERVED);
{S}{E}{T}					return(RESERVED);
{T}{Y}{P}{E}					return(RESERVED);
{U}{N}{T}{I}{L}					return(RESERVED);
{W}{I}{T}{H}					return(RESERVED);
"/"						{yylval.op4 = (char*) strdup(yytext); return(OP4);}
"*"						{yylval.op4 = (char*) strdup(yytext); return(OP4);}
"-"						{yylval.op3 = (char*) strdup(yytext); return(OP3);}
"+"						{yylval.op3 = (char*) strdup(yytext); return(OP3);}
">="				 		{yylval.op2 = (char*) strdup(yytext); return(OP2);}
"<="						{yylval.op2 = (char*) strdup(yytext); return(OP2);}
"<>"						{yylval.op2 = (char*) strdup(yytext); return(OP2);}
"="						{yylval.op2 = (char*) strdup(yytext); return(OP2);}
">"						{yylval.op2 = (char*) strdup(yytext); return(OP2);}
"<"						{yylval.op2 = (char*) strdup(yytext); return(OP2);}
{W}{R}{I}{T}{E}{L}{N}				return(WRITELN);
{W}{H}{I}{L}{E}					return(WHILE);
{V}{A}{R}					return(VAR);
{V}{A}{L}					return(VAL);
{T}{O}						return(TO);
{T}{H}{E}{N}					return(THEN);
";"						return(SEMICOLON);
")"						return(RPAREN);
"]"						return(RBRAC);
{P}{R}{O}{G}{R}{A}{M}				return(PROGRAM);
{P}{A}{R}{A}{M}{S}{T}{R}			return(PARAMSTR);
{O}{R} 						{yylval.op1b = (char*) strdup(yytext); return(OP1b);}
{O}{F}						return(OF);
{N}{O}{T}					return(NOT);
{M}{O}{D}					{yylval.op4 = (char*) strdup(yytext); return(OP4);}
"("						return(LPAREN);
"["						return(LBRAC);
{I}{F}						return(IF);
{F}{U}{N}{C}{T}{I}{O}{N}			return(FUNCTION);
{F}{O}{R}{W}{A}{R}{D}				return(FORWARD);
{F}{O}{R}					return(FOR);
{E}{N}{D}					return(END);
{E}{L}{S}{E}					return(ELSE);
{D}{O}{W}{N}{T}{O}				return(DOWNTO);
".."						return(DOTDOT);
"."						return(DOT);
{D}{O}						return(DO);
{D}{I}{V}					{yylval.op4 = (char*) strdup(yytext); return(OP4);}
","						return(COMMA);
":"						return(COLON);
":="						return(ASSIGN);
{B}{E}{G}{I}{N}					return(BEGI);
{A}{R}{R}{A}{Y}					return(ARRAY);
{A}{N}{D} 					{yylval.op1a = (char*) strdup(yytext); return(OP1a);}
[0-9]+  					{
							yylval.digseq=atoi(yytext); 					/*Guarda valor em yylval e*/
        						return DIGSEQ;							/*envia token reconhecido ao YACC*/
						}
[0-9]+"."[0-9]+					{
							yylval.realnumber=atof(yytext); 				/*Guarda valor em yylval e*/
        						return REALNUMBER;						/*envia token reconhecido ao YACC*/
						}
						
[a-zA-Z]([a-zA-Z0-9_])*				{
							yylval.id = (char*) strdup(yytext);				/*Duplica a string encontrada*/	
							return ID;
						}	
						
						

"(*" 						{ 
							register int c;
							while ((c = input()))
							{

								if (c == '*'){
									if ((c = input()) == ')')
										break;
								      	else
										unput (c);
								}
								else if (c == '\n')
								       	line++;
								else if (c == EOF){
									commenteof();
								       	break;
						   		}
							 }
						 }
    
[ \t\f\r]*					;
"\n"						line++;
.						{
				 			printf ("Line %d: illegal character ('%c')\n",line, yytext[0]);
						}
					
%%


int yywrap(){
	return 1;
}
void yyerror (char *s) {
	printf ("Line %d: %s: %s\n", line, s, yytext);
}


