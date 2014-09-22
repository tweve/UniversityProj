#include "estruturas.h"
#include "tabelaSimbolos.h"
#include "semantica.h"

#include <stdio.h>
#include <stdlib.h>


void translate(is_block* iel);
void translate_header();
void translate_footer();


void translate_block(is_block* s);
void translate_var_dec_list(is_var_dec_list* varl, char* nome_funcao, int tipo);
void translate_variable_declaration(is_var_dec* vd, char* nome_funcao, int tipo);
void translate_id_list(is_id_list* idl, char* idc, tipo_dados tipo, int scope, char* nome_funcao);
void translate_rep_var_dec(is_rep_var_dec* repd,char* nome_funcao, int tipo);
void translate_rep_dec_def(is_rep_dec_def* repd);

void translate_statement_list(is_statement_list* stlist, char* nome_funcao);
void translate_statement(is_statement* is, char* nome_funcao);
void translate_statement_list(is_statement_list* stlist, char* nome_funcao);
element* translate_expression(is_expression* ie, char* nome_funcao);
