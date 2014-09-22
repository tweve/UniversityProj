
/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#ifndef _SEMANTICA_
#define _SEMANTICA_

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "estruturas.h"
#include "mostrar.h"
#include "tabelaSimbolos.h"
#include <ctype.h>

typedef enum{contar,verificar} disc_opcao;



table_element* lista_global;
environment_list *lista_funcoes;

typedef enum {variavel, array}tipo_dados;


typedef struct _t3{
	char * name;
	char exp[1024]; 
	basic_type type;
	union{
		int integer;
		double real;
		int boolean;
		int *arrayInt;	//array de boolean stored em ints
		double *arrayReal;
	} valor;
	int inicio;
	int fim;
	struct _t3* next;
} element;



element* insert_exp(element* newSymbol, element* lista);
void symbol_already_defined(table_element* newel, char * id);

void verifica_program(is_block* iel);
void verifica_block(is_block* s);
void verifica_id_list(is_id_list* idl, char* idc, tipo_dados tipo, int scope, char* nome_funcao);
void verifica_rep_var_dec(is_rep_var_dec* repd,char* nome_funcao, int tipo);
void verifica_rep_dec_def(is_rep_dec_def* repd);
void verifica_function_declaration(is_function_declaration* fd);
void verifica_variable_declaration(is_var_dec* vd, char* nome_funcao, int tipo);
char* verifica_function_heading(is_function_heading* fh, int definido);
void verifica_rep_formal_args(is_rep_formal_args* repd, char* nome_funcao);
void verifica_formal_args(is_formal_args* vd,char* nome_funcao);
void verifica_function_definition(is_function_definition* fd);

void verifica_function_block(is_function_block* fb, char* nome_funcao);
void verifica_var_dec_list(is_var_dec_list* varl, char* nome_funcao, int tipo);


void corresponde_argumentos_correctos(is_rep_formal_args* repd ,char* nome_funcao);
void corresponde_argumentos(is_formal_args* vd,char* nome_funcao, disc_opcao opcao);
void corresponde_id_list(is_id_list* idl, char* idc, int passed_by_ref, tipo_dados tipo , char* nome_funcao, disc_opcao opcao);

element* verifica_expression(is_expression* ie, char* nome_funcao);
element* verifica_rep_expression(char * nome_funcao_destino,is_rep_expression* rep_ex, char* nome_funcao_onde_esta);



void verifica_statement_list(is_statement_list* stlist, char* nome_funcao);
void verifica_statement(is_statement* is, char* nome_funcao);



int verifica_se_todas_funcoes_estao_definidas();
char* converte(basic_type tipo);
char* converte_com_array_of(basic_type tipo);

element* procura_variavel(char* name,element* list);


#endif
