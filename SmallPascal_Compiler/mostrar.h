/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/
#ifndef _MOSTRAR_
#define _MOSTRAR_

#include "estruturas.h"
#include <stdlib.h>
#include <stdio.h>




void show_program(is_block*);

void show_expression(is_expression*);
void show_rep_expression(is_rep_expression* rep_ex);

void show_statement_list(is_statement_list*);
void show_statement(is_statement*);

void show_function_block(is_function_block* fb);

void show_var_dec_list(is_var_dec_list*);

void show_variable_declaration(is_var_dec*);

void show_id_list(is_id_list*);

void show_rep_var_dec(is_rep_var_dec*);

void show_function_definition(is_function_definition*);

void show_function_heading(is_function_heading*);


void show_rep_formal_args(is_rep_formal_args*);

void show_formal_args(is_formal_args*);

void show_function_declaration(is_function_declaration*);


void show_block(is_block*);

void show_rep_dec_def(is_rep_dec_def*);

void show(char* s);

#endif

