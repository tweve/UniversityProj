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
extern YYSTYPE yylval;
