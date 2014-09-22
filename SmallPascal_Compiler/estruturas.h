
/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#ifndef _ESTRUTURAS_
#define _ESTRUTURAS_

int line;

typedef struct _a3 is_expression;
typedef struct _a11 is_statement;
typedef struct _a13 is_function_block;
typedef struct _a15 is_var_dec;

typedef enum{node,flist} disc_tipos;

/*is_rep_expression*/
typedef struct _a10 {
	is_expression* exp;
	struct _a10* next;
}is_rep_expression;


/*is_expression*/

typedef enum {d_digseq,d_realnumber,d_OP1and,d_OP1or,d_OP21,d_OP22,d_OP23,d_OP24,d_OP25,d_OP26,d_OP3minus,d_OP3plus,d_OP4Mult,d_OP4RealDiv,d_OP4Div,d_OP4Mod, d_OP3Plus_exp,d_OP3Minus_exp,d_NOT_exp, d_lparen_expression_rparen, d_id_lbrac_expression_rbrac,d_id_lparen_rep_expression_rparen,d_id} disc_expression;

struct _a3{
	char* id;
 	disc_expression disc_d;
 	is_expression* exp1;
 	char* symbol;
 	is_expression* exp2;
 	union{
 		int digseq;
 		double realnumber;
 		is_rep_expression* aux;
 	}dataExpression;
};




/*is_statement_list*/
typedef struct _a12 {
	disc_tipos disc_tipo;
	is_statement* stt;
	struct _a12* next;
}is_statement_list;


/*is_statement*/

typedef enum{ d_begin_statementlist_end,d_id_assign_expression,d_id_exp_exp,d_exp_stt, d_exp_stt_stt,d_wexp_stt, d_id_exp_exp_stt , d_id_exp_dexp_stt, d_tokens_exp, d_writeln_exp, d_nothing} disc_statement;
struct _a11{
	disc_statement disc_d;
	char* id;
	is_expression* exp1;
	is_expression* exp2;
	is_statement* stt;
	is_statement* stt2;
	union{
		is_statement_list* aux1;
	}dataStatement;

};

/*is_id_list*/
typedef struct _a1 {
	char* id;
	is_expression* exp;
	struct _a1* next;
}is_id_list;

/*is_variable_declaration*/
typedef enum{ d_tipox1,d_tipox2} disc_var_dec;
struct _a15{
	disc_var_dec disc_d;
	int dig1;
	int dig2;
	is_id_list* idl;
	char * id;
	
};


/*is_rep_var_dec*/

typedef struct _a20 {
	is_var_dec* vd;
	struct _a20* next;
}is_rep_var_dec;

/*is_var_declaration_list*/
typedef enum{ d_tipo1,d_tipo2} disc_var_dec_list;
typedef struct _a14{

	disc_var_dec_list disc_d;
	is_var_dec* dec;
	is_rep_var_dec* repdec;
}is_var_dec_list;


/*is_function_block*/
typedef enum{d_bstatement_list, d_var_bstatement_list} disc_function_block;
struct _a13{
	disc_function_block disc_d;
	char * id;
	is_var_dec_list* varl;
	is_statement_list* sttl;
};

/*is_formal_args*/
typedef enum{tipo1,tipo2,tipo3,tipo4} disc_formal_args;
typedef struct _a19{
	is_id_list* idl;
	char* id;
	disc_formal_args disc_d;
}is_formal_args;


/*is_rep_formal_args*/

typedef struct _a23{
	is_formal_args* fa;
	struct _a23* next;
}is_rep_formal_args;


/*is_function_heading*/
typedef enum{t1,t2} disc_function_heading;
typedef struct _a21{
	char* id1;
	char* id2;
	is_rep_formal_args* repf;
	
	disc_function_heading disc_d;
}is_function_heading;


/*is_function_definition*/

typedef struct _a22{
	is_function_heading* fh;
	is_function_block* fb;
}is_function_definition;


/*is_function_declaration*/
typedef struct _a25{
	is_function_heading* fh;
}is_function_declaration;


/*is_rep_dec_def*/
typedef enum{dec,def,noth} disc_rep_dec_def;


typedef struct _a24{
	disc_rep_dec_def disc_d;
	disc_tipos disc_tipo;

	is_function_definition* def;
	is_function_declaration* dec;
	struct _a24* next;
}is_rep_dec_def;

/*is_block*/

typedef enum{bk1,bk2}disc_block;
typedef struct _a26{
	disc_block disc_d;
	char *id;
	is_var_dec_list* vardecl;
	is_rep_dec_def* repdecdef;
	is_statement_list* sttl;
}is_block;


#endif


