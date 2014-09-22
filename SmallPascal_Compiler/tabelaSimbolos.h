
/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#ifndef _TABELASIMBOLOS_
#define _TABELASIMBOLOS_

#include<malloc.h>
#include<string.h>
#include<stdio.h>




typedef enum {array_integer,array_boolean, array_real, function, boolean, integer, program, real, reserved_id} basic_type;

typedef struct _t1{
	
	char name[1024];
	basic_type type;
	int passed_by_reference;
	int inicio;
	int fim;
	int offset;
	struct _t1 *next;
	
} table_element;


typedef struct _t2{
	char name[1024];
	int nparams;
	basic_type tipo_retorno;
	int definido;
	struct _t2 *next;
	table_element *locals;
	table_element *params;
}environment_list;


table_element *insert_el(char *str, basic_type t, int passed_by_reference, table_element* symtab);
void show_table();
table_element *search_el(char *str, table_element* symtab);
int search_ambiente(char *str, environment_list* funcs);
table_element *search_el_global(char *str, table_element* symtab);
environment_list* procura_funcao(char *str);
environment_list* insert_func(environment_list* funcao,  environment_list* symtab);
int search_funcao(char *str, environment_list* funcs);
void show_environment(environment_list* funcao);


#endif

