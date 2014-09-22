
/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#include "estruturas.h"

void upper(char * palavra);

is_expression* insert_DIGSEQ(int);
is_expression* insert_REALNUMBER(double);

is_expression* insert_OP1(is_expression*, char*, is_expression*);
is_expression* insert_OP2(is_expression*, char*, is_expression*);
is_expression* insert_OP3(is_expression*, char*, is_expression*);
is_expression* insert_OP4(is_expression*, char*, is_expression*);
is_expression* insert_OP3_expression(char*, is_expression*);
is_expression* insert_NOT_expression(is_expression*);
is_expression* insert_lparen_expression_rparen(is_expression*);
is_expression* insert_id_lbrac_expression_rbrac(char*,is_expression*);
is_expression* insert_id_lparen_rep_expression_rparen(char*, is_rep_expression*);
is_expression* insert_id(char*);


is_rep_expression* insert_rep_expression(is_rep_expression* list,is_expression* exp);

is_statement* insert_begin_statementlist_end(is_statement_list*);
is_statement* insert_id_assign_expression(char*,is_expression*);
is_statement* insert_id_exp_exp(char*,is_expression*,is_expression*);
is_statement* insert_exp_stt(is_expression*,is_statement*);
is_statement* insert_exp_stt_stt(is_expression*,is_statement*,is_statement*);
is_statement* insert_wexp_stt(is_expression*,is_statement*);
is_statement* insert_id_exp_exp_stt(char*,is_expression*,is_expression*,is_statement*);
is_statement* insert_id_exp_dexp_stt(char*,is_expression*,is_expression*,is_statement*);
is_statement* insert_tokens_exp_id(is_expression*, char*);
is_statement* insert_writeln_exp(is_expression*);
is_statement* insert_nothing();

is_statement_list* insert_statement_list(is_statement_list*, is_statement*);

is_function_block* insert_bstatement_list(is_statement_list*);

is_function_block* insert_var_bstatement_list(is_var_dec_list*,is_statement_list*);

is_var_dec_list* insert_var_dec(is_var_dec*);
is_var_dec_list* insert_var_dec_rep(is_var_dec*, is_rep_var_dec*);
is_var_dec* insert_idl_id(is_id_list*, char*);
is_var_dec* insert_idl_id_2(is_id_list*,int,int, char*);

is_id_list* insert_id_list(is_id_list*, char*);

is_rep_var_dec* insert_rep_var_dec(is_rep_var_dec*, is_var_dec*);

is_function_definition* insert_function_definition(is_function_heading*, is_function_block*);

is_function_heading* insert_function_heading(char*, char*);

is_function_heading* insert_function_heading2(char*,is_rep_formal_args*,char*);

is_rep_formal_args* insert_rep_formal_args_list(is_rep_formal_args*, is_formal_args*);

is_formal_args* insert_formal_args(is_id_list*, char*, int);



is_function_declaration* insert_function_heading3(is_function_heading*);


is_block* insert_block1(is_var_dec_list*, is_rep_dec_def*, is_statement_list*);
is_block* insert_block2(is_rep_dec_def*, is_statement_list*);

is_rep_dec_def* insert_rep_dec(is_rep_dec_def*, is_function_declaration*);
is_rep_dec_def* insert_rep_def(is_rep_dec_def*, is_function_definition*);
is_rep_dec_def* insert_nothing2();


