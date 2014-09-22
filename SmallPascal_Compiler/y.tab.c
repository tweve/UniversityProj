#ifndef lint
static const char yysccsid[] = "@(#)yaccpar	1.9 (Berkeley) 02/21/93";
#endif

#define YYBYACC 1
#define YYMAJOR 1
#define YYMINOR 9
#define YYPATCH 20101229

#define YYEMPTY        (-1)
#define yyclearin      (yychar = YYEMPTY)
#define yyerrok        (yyerrflag = 0)
#define YYRECOVERING() (yyerrflag != 0)

#define YYPREFIX "yy"

#define YYPURE 0

#line 2 "SmallPascalCompiler.y"

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

#line 33 "SmallPascalCompiler.y"
#ifdef YYSTYPE
#undef  YYSTYPE_IS_DECLARED
#define YYSTYPE_IS_DECLARED 1
#endif
#ifndef YYSTYPE_IS_DECLARED
#define YYSTYPE_IS_DECLARED 1
typedef union {
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
} YYSTYPE;
#endif /* !YYSTYPE_IS_DECLARED */
#line 78 "y.tab.c"
/* compatibility with bison */
#ifdef YYPARSE_PARAM
/* compatibility with FreeBSD */
# ifdef YYPARSE_PARAM_TYPE
#  define YYPARSE_DECL() yyparse(YYPARSE_PARAM_TYPE YYPARSE_PARAM)
# else
#  define YYPARSE_DECL() yyparse(void *YYPARSE_PARAM)
# endif
#else
# define YYPARSE_DECL() yyparse(void)
#endif

/* Parameters sent to lex. */
#ifdef YYLEX_PARAM
# define YYLEX_DECL() yylex(void *YYLEX_PARAM)
# define YYLEX yylex(YYLEX_PARAM)
#else
# define YYLEX_DECL() yylex(void)
# define YYLEX yylex()
#endif

/* Parameters sent to yyerror. */
#define YYERROR_DECL() yyerror(const char *s)
#define YYERROR_CALL(msg) yyerror(msg)

extern int YYPARSE_DECL();

#define OP2 257
#define OP3 258
#define OP1b 259
#define OP4 260
#define OP1a 261
#define NOT 262
#define IF 263
#define THEN 264
#define ELSE 265
#define LPAREN 266
#define BEGI 267
#define AND 268
#define ARRAY 269
#define ASSIGN 270
#define COLON 271
#define COMMA 272
#define DIV 273
#define DO 274
#define DOT 275
#define DOTDOT 276
#define DOWNTO 277
#define END 278
#define FOR 279
#define FORWARD 280
#define FUNCTION 281
#define LBRAC 282
#define MOD 283
#define OF 284
#define OR 285
#define PARAMSTR 286
#define PROGRAM 287
#define RBRAC 288
#define RPAREN 289
#define SEMICOLON 290
#define TO 291
#define VAL 292
#define VAR 293
#define WHILE 294
#define WRITELN 295
#define RESERVED 296
#define ID 297
#define DIGSEQ 298
#define REALNUMBER 299
#define YYERRCODE 256
static const short yylhs[] = {                           -1,
    0,   14,   14,   15,   15,   15,   10,   16,   11,   11,
   12,   12,   13,   13,   13,   13,    5,    5,    6,    6,
    7,    7,    9,    9,    8,    8,    4,    4,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    2,
};
static const short yylen[] = {                            2,
    5,    5,    4,    2,    2,    0,    4,    4,    6,    7,
    1,    3,    3,    4,    5,    6,    3,    4,    2,    3,
    4,   11,    1,    2,    1,    3,    1,    3,    3,    3,
    6,    4,    6,    4,    8,    8,    9,    4,    0,    1,
    1,    3,    3,    3,    3,    3,    2,    2,    3,    4,
    3,    4,    1,    1,    3,
};
static const short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    6,    0,    0,   25,    0,
    0,    0,    1,    0,    0,    5,    0,    4,   23,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   27,    0,    0,    0,   24,    0,    0,   26,    0,    0,
    0,    0,    0,   40,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    3,    0,    0,    0,    0,    0,    0,
    0,   21,    2,    0,   48,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   29,    0,    0,    0,    0,    0,
    0,   28,    0,    0,    0,    0,   11,    0,    8,    7,
    0,    0,   49,   51,    0,    0,    0,    0,    0,    0,
   46,   42,    0,    0,    0,   34,   38,    0,    0,    0,
    0,    0,    0,   17,    0,    0,    0,   52,   50,    0,
    0,    0,    0,    0,    9,    0,    0,   13,    0,   12,
   18,    0,    0,   33,    0,    0,    0,    0,    0,   14,
    0,   10,    0,    0,    0,    0,    0,   15,    0,   36,
   35,    0,   16,    0,   37,   22,
};
static const short yydgoto[] = {                          2,
   46,   96,   31,   32,   59,    6,   10,   11,   20,   16,
   17,   86,   87,    7,    8,   18,
};
static const short yysindex[] = {                      -278,
 -244,    0, -234, -230, -225,    0, -201, -206,    0, -225,
 -107, -191,    0,  -81, -212,    0, -172,    0,    0, -225,
 -265, -168,  -81, -198,  -81, -124, -115, -198,  -85, -190,
    0, -141,  -79, -231,    0,  -87,  -93,    0, -140, -198,
 -198, -198, -256,    0,    0,   10, -119,  -58,  -43,  -29,
 -198, -198, -198,    0,  -81,  -33,  -81,  -46,  -32,  -10,
  -39,    0,    0, -245,    0, -146, -211, -198, -198, -198,
 -198, -198, -198,  -81,    0, -198,   -5,  -81,  -67,   18,
  -40,    0,   -9, -225,  -62,  -55,    0, -118,    0,    0,
  -81,  -13,    0,    0,   18, -261,  -34,   22, -245, -245,
    0,    0,    7, -239, -198,    0,    0,    3,  -12,  -25,
 -264,   -6,  -60,    0, -116,  -14, -198,    0,    0,  -81,
 -198, -198,  -53, -198,    0, -263,    2,    0,   -7,    0,
    0,   -1,   18,    0,  -19,   -8,   16,   18,    5,    0,
   -4,    0,    8,  -81,  -81,   -2,    1,    0,    4,    0,
    0,   11,    0,    6,    0,    0,
};
static const short yyrindex[] = {                         0,
    0,    0,    0, -160,    0,    0,    0,    0,    0, -151,
    0,    0,    0,  -94,    0,    0,    0,    0,    0, -139,
    0,    0,  -94,    0,  -94,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -155,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -94,    0,  -94,    0,    0,    0,
    0,    0,    0, -133,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -199,    0,    0,    0, -199,    0, -170,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -94,    0,    0,    0, -243,    0,    0, -207, -111,  -89,
    0,    0,  -75,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -199,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -235,    0,    0,    0,    0, -138,    0,    0,
    0,    0,    0, -199, -199,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
static const short yygindex[] = {                         0,
  -28,    0,  -47,  -22,    0,  257,   -3,  -54,    0,    0,
    0,    0,  181,    0,  291,    0,
};
#define YYTABLESIZE 301
static const short yytable[] = {                         50,
   39,   85,   47,   36,  127,  139,   19,   82,    1,   67,
  117,   64,   65,   66,   72,   73,   35,   69,   70,   71,
   72,   73,   79,   80,   81,   68,  103,  118,   54,  110,
  106,   37,  128,  140,   88,   57,   55,  121,   95,   97,
   98,   99,  100,  101,  102,   54,   40,  104,   58,   44,
   41,  122,    3,   55,   42,    4,   44,   44,   85,   40,
   14,    5,    5,   41,   44,   39,   44,   42,  115,   44,
   44,    9,  134,   13,   15,   23,  123,   94,   39,   52,
   44,   44,   44,   44,   33,   43,   44,   45,  133,   15,
   39,   53,  135,  136,   30,  138,  150,  151,   43,   44,
   45,   53,   53,   53,   53,   53,    6,   30,   53,   53,
   69,   70,   71,   72,   73,   19,   53,   34,   53,   30,
    6,   53,   53,   47,   47,   47,   31,   20,   38,   19,
   47,   47,   53,   53,   53,   53,   54,   63,   47,   31,
   47,   20,   93,   47,   47,   45,   45,   45,   55,   55,
   49,   31,   45,   45,   47,   47,   47,   47,   75,  114,
   45,  131,   45,   21,   22,   45,   45,   43,   43,   43,
   55,   55,   48,   55,   43,   43,   45,   45,   45,   45,
   51,   24,   43,   39,   43,   25,   56,   43,   43,   69,
   70,   71,   72,   73,   61,   39,   62,   26,   43,   43,
   43,   43,   32,   69,   70,   71,   72,   73,  111,   22,
   27,   76,   28,   29,   32,   30,   69,   70,   71,   72,
   73,  107,   69,   70,   71,   72,   73,   69,   70,   71,
   72,   73,   84,  112,  113,  137,    9,   69,   70,   71,
   72,   73,   77,   89,   78,  126,   22,  108,   69,   70,
   71,   72,   73,  119,  144,   83,   91,   90,   92,   84,
  105,  109,  116,    9,  129,  145,   69,   70,   71,   72,
   73,  120,  124,   74,   69,   70,   71,   72,   73,   70,
   71,   72,   73,  132,  125,  141,  143,  146,  147,  142,
   60,  149,  148,  130,  152,  156,   12,  153,    0,  155,
  154,
};
static const short yycheck[] = {                         28,
   23,   56,   25,  269,  269,  269,   10,   55,  287,  266,
  272,   40,   41,   42,  260,  261,   20,  257,  258,  259,
  260,  261,   51,   52,   53,  282,   74,  289,  272,   84,
   78,  297,  297,  297,   57,  267,  272,  277,   67,   68,
   69,   70,   71,   72,   73,  289,  258,   76,  280,  257,
  262,  291,  297,  289,  266,  290,  264,  265,  113,  258,
  267,  293,  293,  262,  272,  265,  274,  266,   91,  277,
  278,  297,  120,  275,  281,  267,  105,  289,  278,  270,
  288,  289,  290,  291,  297,  297,  298,  299,  117,  281,
  290,  282,  121,  122,  265,  124,  144,  145,  297,  298,
  299,  257,  258,  259,  260,  261,  267,  278,  264,  265,
  257,  258,  259,  260,  261,  267,  272,  290,  274,  290,
  281,  277,  278,  257,  258,  259,  265,  267,  297,  281,
  264,  265,  288,  289,  290,  291,  278,  278,  272,  278,
  274,  281,  289,  277,  278,  257,  258,  259,  290,  290,
  266,  290,  264,  265,  288,  289,  290,  291,  278,  278,
  272,  278,  274,  271,  272,  277,  278,  257,  258,  259,
  290,  290,  297,  290,  264,  265,  288,  289,  290,  291,
  266,  263,  272,  278,  274,  267,  266,  277,  278,  257,
  258,  259,  260,  261,  282,  290,  290,  279,  288,  289,
  290,  291,  278,  257,  258,  259,  260,  261,  271,  272,
  292,  270,  294,  295,  290,  297,  257,  258,  259,  260,
  261,  289,  257,  258,  259,  260,  261,  257,  258,  259,
  260,  261,  293,  289,  290,  289,  297,  257,  258,  259,
  260,  261,  286,  290,  274,  271,  272,  288,  257,  258,
  259,  260,  261,  288,  274,  289,  267,  290,  298,  293,
  266,  271,  276,  297,  271,  274,  257,  258,  259,  260,
  261,  265,  270,  264,  257,  258,  259,  260,  261,  258,
  259,  260,  261,  298,  297,  284,  288,  272,  284,  297,
   34,  284,  297,  113,  297,  290,    6,  297,   -1,  289,
  297,
};
#define YYFINAL 2
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 299
#if YYDEBUG
static const char *yyname[] = {

"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"OP2","OP3","OP1b","OP4","OP1a",
"NOT","IF","THEN","ELSE","LPAREN","BEGI","AND","ARRAY","ASSIGN","COLON","COMMA",
"DIV","DO","DOT","DOTDOT","DOWNTO","END","FOR","FORWARD","FUNCTION","LBRAC",
"MOD","OF","OR","PARAMSTR","PROGRAM","RBRAC","RPAREN","SEMICOLON","TO","VAL",
"VAR","WHILE","WRITELN","RESERVED","ID","DIGSEQ","REALNUMBER",
};
static const char *yyrule[] = {
"$accept : Start",
"Start : PROGRAM ID SEMICOLON Block DOT",
"Block : VariableDeclarationList RepDeclarationDefinition BEGI StatementList END",
"Block : RepDeclarationDefinition BEGI StatementList END",
"RepDeclarationDefinition : RepDeclarationDefinition FunctionDeclaration",
"RepDeclarationDefinition : RepDeclarationDefinition FunctionDefinition",
"RepDeclarationDefinition :",
"FunctionDefinition : FunctionHeading SEMICOLON FunctionBlock SEMICOLON",
"FunctionDeclaration : FunctionHeading SEMICOLON FORWARD SEMICOLON",
"FunctionHeading : FUNCTION ID LPAREN RPAREN COLON ID",
"FunctionHeading : FUNCTION ID LPAREN RepCommaFormalArgs RPAREN COLON ID",
"RepCommaFormalArgs : FormalArgs",
"RepCommaFormalArgs : RepCommaFormalArgs SEMICOLON FormalArgs",
"FormalArgs : IDList COLON ID",
"FormalArgs : VAR IDList COLON ID",
"FormalArgs : IDList COLON ARRAY OF ID",
"FormalArgs : VAR IDList COLON ARRAY OF ID",
"FunctionBlock : BEGI StatementList END",
"FunctionBlock : VariableDeclarationList BEGI StatementList END",
"VariableDeclarationList : VAR VariableDeclaration",
"VariableDeclarationList : VAR VariableDeclaration RepVariableDeclaration",
"VariableDeclaration : IDList COLON ID SEMICOLON",
"VariableDeclaration : IDList COLON ARRAY LBRAC DIGSEQ DOTDOT DIGSEQ RBRAC OF ID SEMICOLON",
"RepVariableDeclaration : VariableDeclaration",
"RepVariableDeclaration : RepVariableDeclaration VariableDeclaration",
"IDList : ID",
"IDList : IDList COMMA ID",
"StatementList : Statement",
"StatementList : StatementList SEMICOLON Statement",
"Statement : BEGI StatementList END",
"Statement : ID ASSIGN Expression",
"Statement : ID LBRAC Expression RBRAC ASSIGN Expression",
"Statement : IF Expression THEN Statement",
"Statement : IF Expression THEN Statement ELSE Statement",
"Statement : WHILE Expression DO Statement",
"Statement : FOR ID ASSIGN Expression TO Expression DO Statement",
"Statement : FOR ID ASSIGN Expression DOWNTO Expression DO Statement",
"Statement : VAL LPAREN PARAMSTR LPAREN Expression RPAREN COMMA ID RPAREN",
"Statement : WRITELN LPAREN Expression RPAREN",
"Statement :",
"Expression : DIGSEQ",
"Expression : REALNUMBER",
"Expression : Expression OP1a Expression",
"Expression : Expression OP1b Expression",
"Expression : Expression OP2 Expression",
"Expression : Expression OP3 Expression",
"Expression : Expression OP4 Expression",
"Expression : OP3 Expression",
"Expression : NOT Expression",
"Expression : LPAREN Expression RPAREN",
"Expression : ID LBRAC Expression RBRAC",
"Expression : ID LPAREN RPAREN",
"Expression : ID LPAREN RepCommaExpression RPAREN",
"Expression : ID",
"RepCommaExpression : Expression",
"RepCommaExpression : RepCommaExpression COMMA Expression",

};
#endif
/* define the initial stack-sizes */
#ifdef YYSTACKSIZE
#undef YYMAXDEPTH
#define YYMAXDEPTH  YYSTACKSIZE
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH  500
#endif
#endif

#define YYINITSTACKSIZE 500

int      yydebug;
int      yynerrs;

typedef struct {
    unsigned stacksize;
    short    *s_base;
    short    *s_mark;
    short    *s_last;
    YYSTYPE  *l_base;
    YYSTYPE  *l_mark;
} YYSTACKDATA;
int      yyerrflag;
int      yychar;
YYSTYPE  yyval;
YYSTYPE  yylval;

/* variables for the parser stack */
static YYSTACKDATA yystack;
#line 199 "SmallPascalCompiler.y"
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


#line 444 "y.tab.c"

#if YYDEBUG
#include <stdio.h>		/* needed for printf */
#endif

#include <stdlib.h>	/* needed for malloc, etc */
#include <string.h>	/* needed for memset */

/* allocate initial stack or double stack size, up to YYMAXDEPTH */
static int yygrowstack(YYSTACKDATA *data)
{
    int i;
    unsigned newsize;
    short *newss;
    YYSTYPE *newvs;

    if ((newsize = data->stacksize) == 0)
        newsize = YYINITSTACKSIZE;
    else if (newsize >= YYMAXDEPTH)
        return -1;
    else if ((newsize *= 2) > YYMAXDEPTH)
        newsize = YYMAXDEPTH;

    i = data->s_mark - data->s_base;
    newss = (short *)realloc(data->s_base, newsize * sizeof(*newss));
    if (newss == 0)
        return -1;

    data->s_base = newss;
    data->s_mark = newss + i;

    newvs = (YYSTYPE *)realloc(data->l_base, newsize * sizeof(*newvs));
    if (newvs == 0)
        return -1;

    data->l_base = newvs;
    data->l_mark = newvs + i;

    data->stacksize = newsize;
    data->s_last = data->s_base + newsize - 1;
    return 0;
}

#if YYPURE || defined(YY_NO_LEAKS)
static void yyfreestack(YYSTACKDATA *data)
{
    free(data->s_base);
    free(data->l_base);
    memset(data, 0, sizeof(*data));
}
#else
#define yyfreestack(data) /* nothing */
#endif

#define YYABORT  goto yyabort
#define YYREJECT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR  goto yyerrlab

int
YYPARSE_DECL()
{
    int yym, yyn, yystate;
#if YYDEBUG
    const char *yys;

    if ((yys = getenv("YYDEBUG")) != 0)
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = YYEMPTY;
    yystate = 0;

#if YYPURE
    memset(&yystack, 0, sizeof(yystack));
#endif

    if (yystack.s_base == NULL && yygrowstack(&yystack)) goto yyoverflow;
    yystack.s_mark = yystack.s_base;
    yystack.l_mark = yystack.l_base;
    yystate = 0;
    *yystack.s_mark = 0;

yyloop:
    if ((yyn = yydefred[yystate]) != 0) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = YYLEX) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("%sdebug: state %d, reading %d (%s)\n",
                    YYPREFIX, yystate, yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("%sdebug: state %d, shifting to state %d\n",
                    YYPREFIX, yystate, yytable[yyn]);
#endif
        if (yystack.s_mark >= yystack.s_last && yygrowstack(&yystack))
        {
            goto yyoverflow;
        }
        yystate = yytable[yyn];
        *++yystack.s_mark = yytable[yyn];
        *++yystack.l_mark = yylval;
        yychar = YYEMPTY;
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;

    yyerror("syntax error");

    goto yyerrlab;

yyerrlab:
    ++yynerrs;

yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yystack.s_mark]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("%sdebug: state %d, error recovery shifting\
 to state %d\n", YYPREFIX, *yystack.s_mark, yytable[yyn]);
#endif
                if (yystack.s_mark >= yystack.s_last && yygrowstack(&yystack))
                {
                    goto yyoverflow;
                }
                yystate = yytable[yyn];
                *++yystack.s_mark = yytable[yyn];
                *++yystack.l_mark = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("%sdebug: error recovery discarding state %d\n",
                            YYPREFIX, *yystack.s_mark);
#endif
                if (yystack.s_mark <= yystack.s_base) goto yyabort;
                --yystack.s_mark;
                --yystack.l_mark;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("%sdebug: state %d, error recovery discards token %d (%s)\n",
                    YYPREFIX, yystate, yychar, yys);
        }
#endif
        yychar = YYEMPTY;
        goto yyloop;
    }

yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("%sdebug: state %d, reducing by rule %d (%s)\n",
                YYPREFIX, yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    if (yym)
        yyval = yystack.l_mark[1-yym];
    else
        memset(&yyval, 0, sizeof yyval);
    switch (yyn)
    {
case 1:
#line 100 "SmallPascalCompiler.y"
	{yyval.st = yystack.l_mark[-1].bk; yyval.st->id = yystack.l_mark[-3].id; my_program = yyval.st;}
break;
case 2:
#line 103 "SmallPascalCompiler.y"
	{yyval.bk = insert_block1(yystack.l_mark[-4].vdl,yystack.l_mark[-3].rdd,yystack.l_mark[-1].sttml);}
break;
case 3:
#line 104 "SmallPascalCompiler.y"
	{yyval.bk = insert_block2(yystack.l_mark[-3].rdd,yystack.l_mark[-1].sttml);}
break;
case 4:
#line 107 "SmallPascalCompiler.y"
	{yyval.rdd = insert_rep_dec(yystack.l_mark[-1].rdd,yystack.l_mark[0].fdec);}
break;
case 5:
#line 108 "SmallPascalCompiler.y"
	{yyval.rdd = insert_rep_def(yystack.l_mark[-1].rdd,yystack.l_mark[0].fd);}
break;
case 6:
#line 109 "SmallPascalCompiler.y"
	{yyval.rdd = NULL;}
break;
case 7:
#line 112 "SmallPascalCompiler.y"
	{yyval.fd = insert_function_definition(yystack.l_mark[-3].fh,yystack.l_mark[-1].fb);}
break;
case 8:
#line 115 "SmallPascalCompiler.y"
	{yyval.fdec = insert_function_heading3(yystack.l_mark[-3].fh);}
break;
case 9:
#line 119 "SmallPascalCompiler.y"
	{yyval.fh= insert_function_heading(yystack.l_mark[-4].id,yystack.l_mark[0].id);}
break;
case 10:
#line 120 "SmallPascalCompiler.y"
	{yyval.fh= insert_function_heading2(yystack.l_mark[-5].id,yystack.l_mark[-3].rfa,yystack.l_mark[0].id);}
break;
case 11:
#line 123 "SmallPascalCompiler.y"
	{yyval.rfa = insert_rep_formal_args_list(NULL,yystack.l_mark[0].fa);}
break;
case 12:
#line 124 "SmallPascalCompiler.y"
	{yyval.rfa = insert_rep_formal_args_list(yystack.l_mark[-2].rfa,yystack.l_mark[0].fa);}
break;
case 13:
#line 128 "SmallPascalCompiler.y"
	{yyval.fa= insert_formal_args(yystack.l_mark[-2].idl,yystack.l_mark[0].id,1);}
break;
case 14:
#line 129 "SmallPascalCompiler.y"
	{yyval.fa= insert_formal_args(yystack.l_mark[-2].idl,yystack.l_mark[0].id,2);}
break;
case 15:
#line 130 "SmallPascalCompiler.y"
	{yyval.fa= insert_formal_args(yystack.l_mark[-4].idl,yystack.l_mark[0].id,3);}
break;
case 16:
#line 131 "SmallPascalCompiler.y"
	{yyval.fa= insert_formal_args(yystack.l_mark[-4].idl,yystack.l_mark[0].id,4);}
break;
case 17:
#line 135 "SmallPascalCompiler.y"
	{yyval.fb = insert_bstatement_list(yystack.l_mark[-1].sttml);}
break;
case 18:
#line 136 "SmallPascalCompiler.y"
	{yyval.fb = insert_var_bstatement_list(yystack.l_mark[-3].vdl,yystack.l_mark[-1].sttml);}
break;
case 19:
#line 139 "SmallPascalCompiler.y"
	{yyval.vdl = insert_var_dec(yystack.l_mark[0].vd);}
break;
case 20:
#line 140 "SmallPascalCompiler.y"
	{yyval.vdl = insert_var_dec_rep(yystack.l_mark[-1].vd, yystack.l_mark[0].rvd);}
break;
case 21:
#line 143 "SmallPascalCompiler.y"
	{yyval.vd = insert_idl_id(yystack.l_mark[-3].idl,yystack.l_mark[-1].id);}
break;
case 22:
#line 144 "SmallPascalCompiler.y"
	{yyval.vd = insert_idl_id_2(yystack.l_mark[-10].idl,yystack.l_mark[-6].digseq,yystack.l_mark[-4].digseq,yystack.l_mark[-1].id);}
break;
case 23:
#line 147 "SmallPascalCompiler.y"
	{yyval.rvd = insert_rep_var_dec(NULL,yystack.l_mark[0].vd);}
break;
case 24:
#line 148 "SmallPascalCompiler.y"
	{yyval.rvd = insert_rep_var_dec(yystack.l_mark[-1].rvd,yystack.l_mark[0].vd);}
break;
case 25:
#line 151 "SmallPascalCompiler.y"
	{yyval.idl = insert_id_list(NULL,yystack.l_mark[0].id);}
break;
case 26:
#line 152 "SmallPascalCompiler.y"
	{yyval.idl = insert_id_list(yystack.l_mark[-2].idl,yystack.l_mark[0].id);}
break;
case 27:
#line 156 "SmallPascalCompiler.y"
	{yyval.sttml = insert_statement_list(NULL,yystack.l_mark[0].sttm);}
break;
case 28:
#line 157 "SmallPascalCompiler.y"
	{yyval.sttml = insert_statement_list(yystack.l_mark[-2].sttml,yystack.l_mark[0].sttm);}
break;
case 29:
#line 162 "SmallPascalCompiler.y"
	{yyval.sttm = insert_begin_statementlist_end(yystack.l_mark[-1].sttml);}
break;
case 30:
#line 163 "SmallPascalCompiler.y"
	{yyval.sttm = insert_id_assign_expression(yystack.l_mark[-2].id,yystack.l_mark[0].ie);}
break;
case 31:
#line 164 "SmallPascalCompiler.y"
	{yyval.sttm = insert_id_exp_exp(yystack.l_mark[-5].id,yystack.l_mark[-3].ie,yystack.l_mark[0].ie);}
break;
case 32:
#line 165 "SmallPascalCompiler.y"
	{yyval.sttm = insert_exp_stt(yystack.l_mark[-2].ie,yystack.l_mark[0].sttm);}
break;
case 33:
#line 166 "SmallPascalCompiler.y"
	{yyval.sttm = insert_exp_stt_stt(yystack.l_mark[-4].ie,yystack.l_mark[-2].sttm,yystack.l_mark[0].sttm);}
break;
case 34:
#line 167 "SmallPascalCompiler.y"
	{yyval.sttm = insert_wexp_stt(yystack.l_mark[-2].ie,yystack.l_mark[0].sttm);}
break;
case 35:
#line 168 "SmallPascalCompiler.y"
	{yyval.sttm = insert_id_exp_exp_stt(yystack.l_mark[-6].id,yystack.l_mark[-4].ie,yystack.l_mark[-2].ie,yystack.l_mark[0].sttm);}
break;
case 36:
#line 169 "SmallPascalCompiler.y"
	{yyval.sttm = insert_id_exp_dexp_stt(yystack.l_mark[-6].id,yystack.l_mark[-4].ie,yystack.l_mark[-2].ie,yystack.l_mark[0].sttm);}
break;
case 37:
#line 170 "SmallPascalCompiler.y"
	{yyval.sttm = insert_tokens_exp_id(yystack.l_mark[-4].ie,yystack.l_mark[-1].id);}
break;
case 38:
#line 171 "SmallPascalCompiler.y"
	{yyval.sttm = insert_writeln_exp(yystack.l_mark[-1].ie);}
break;
case 39:
#line 172 "SmallPascalCompiler.y"
	{yyval.sttm = NULL;}
break;
case 40:
#line 177 "SmallPascalCompiler.y"
	{yyval.ie = insert_DIGSEQ(yystack.l_mark[0].digseq);}
break;
case 41:
#line 178 "SmallPascalCompiler.y"
	{yyval.ie = insert_REALNUMBER(yystack.l_mark[0].realnumber);}
break;
case 42:
#line 179 "SmallPascalCompiler.y"
	{yyval.ie = insert_OP1(yystack.l_mark[-2].ie, yystack.l_mark[-1].op1a, yystack.l_mark[0].ie);}
break;
case 43:
#line 180 "SmallPascalCompiler.y"
	{yyval.ie = insert_OP1(yystack.l_mark[-2].ie, yystack.l_mark[-1].op1b, yystack.l_mark[0].ie);}
break;
case 44:
#line 181 "SmallPascalCompiler.y"
	{yyval.ie = insert_OP2(yystack.l_mark[-2].ie, yystack.l_mark[-1].op2, yystack.l_mark[0].ie);}
break;
case 45:
#line 182 "SmallPascalCompiler.y"
	{yyval.ie = insert_OP3(yystack.l_mark[-2].ie, yystack.l_mark[-1].op3, yystack.l_mark[0].ie);}
break;
case 46:
#line 183 "SmallPascalCompiler.y"
	{yyval.ie = insert_OP4(yystack.l_mark[-2].ie, yystack.l_mark[-1].op4, yystack.l_mark[0].ie);}
break;
case 47:
#line 184 "SmallPascalCompiler.y"
	{yyval.ie = insert_OP3_expression(yystack.l_mark[-1].op3,yystack.l_mark[0].ie);}
break;
case 48:
#line 185 "SmallPascalCompiler.y"
	{yyval.ie = insert_NOT_expression(yystack.l_mark[0].ie);}
break;
case 49:
#line 186 "SmallPascalCompiler.y"
	{yyval.ie = yystack.l_mark[-1].ie;}
break;
case 50:
#line 187 "SmallPascalCompiler.y"
	{yyval.ie = insert_id_lbrac_expression_rbrac(yystack.l_mark[-3].id,yystack.l_mark[-1].ie);}
break;
case 51:
#line 188 "SmallPascalCompiler.y"
	{yyval.ie = insert_id_lparen_rep_expression_rparen(yystack.l_mark[-2].id,NULL);}
break;
case 52:
#line 189 "SmallPascalCompiler.y"
	{yyval.ie = insert_id_lparen_rep_expression_rparen(yystack.l_mark[-3].id,yystack.l_mark[-1].re);}
break;
case 53:
#line 190 "SmallPascalCompiler.y"
	{yyval.ie = insert_id(yystack.l_mark[0].id);}
break;
case 54:
#line 193 "SmallPascalCompiler.y"
	{yyval.re = insert_rep_expression(NULL,yystack.l_mark[0].ie);}
break;
case 55:
#line 194 "SmallPascalCompiler.y"
	{yyval.re = insert_rep_expression(yystack.l_mark[-2].re,yystack.l_mark[0].ie);}
break;
#line 870 "y.tab.c"
    }
    yystack.s_mark -= yym;
    yystate = *yystack.s_mark;
    yystack.l_mark -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("%sdebug: after reduction, shifting from state 0 to\
 state %d\n", YYPREFIX, YYFINAL);
#endif
        yystate = YYFINAL;
        *++yystack.s_mark = YYFINAL;
        *++yystack.l_mark = yyval;
        if (yychar < 0)
        {
            if ((yychar = YYLEX) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("%sdebug: state %d, reading %d (%s)\n",
                        YYPREFIX, YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("%sdebug: after reduction, shifting from state %d \
to state %d\n", YYPREFIX, *yystack.s_mark, yystate);
#endif
    if (yystack.s_mark >= yystack.s_last && yygrowstack(&yystack))
    {
        goto yyoverflow;
    }
    *++yystack.s_mark = (short) yystate;
    *++yystack.l_mark = yyval;
    goto yyloop;

yyoverflow:
    yyerror("yacc stack overflow");

yyabort:
    yyfreestack(&yystack);
    return (1);

yyaccept:
    yyfreestack(&yystack);
    return (0);
}
