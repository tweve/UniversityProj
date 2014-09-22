
/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#include "tabelaSimbolos.h"
#include "semantica.h"

/*
extern prog_env* program_environment;
table_element* symtab = program_environment->global;
*/
extern int erros;
int offset = 0;



element* insert_exp(element* newSymbol, element* lista){
	element* aux;
	element* previous;
	newSymbol->next=NULL;	
	if(lista)	//Se table ja tem elementos
	{	//Procura cauda da lista e verifica se simbolo ja existe (NOTA: assume-se uma tabela de simbolos globais!)
		for(aux=lista; aux; previous=aux, aux=aux->next);
		
		previous->next=newSymbol;	//adiciona ao final da lista
	}
	else	//symtab tem um elemento -> o novo simbolo
		lista=newSymbol;		
	
	return lista; 
}

//Insere um novo identificador na cauda de uma lista ligada de simbolo
table_element *insert_el(char *str, basic_type t ,int passed_by_reference, table_element* symtab)
{	
	int tmnh = strlen(str);
	char * newString = malloc(tmnh* sizeof (char));
	int pos = 0;

	for(;str[pos];pos++)
		newString[pos] = tolower(str[pos]);
	str = newString;
	
	table_element *newSymbol=(table_element*) malloc(sizeof(table_element));
	table_element *aux;
	table_element *previous;

	strcpy(newSymbol->name, str);
	newSymbol->passed_by_reference = passed_by_reference;
	newSymbol->type=t;
	newSymbol->offset = offset;
	offset++;
	newSymbol->next=NULL;	

/*
	if (lista_global)
		for(aux=lista_global; aux; aux=aux->next)
			if(aux->type == function && strcasecmp(aux->name,str) == 0){
				printf("Symbol %s already defined\n", aux->name);
				exit(0);
			}
*/	

	if(symtab)	//Se table ja tem elementos
	{	//Procura cauda da lista e verifica se simbolo ja existe (NOTA: assume-se uma tabela de simbolos globais!)
		for(aux=symtab; aux; previous=aux, aux=aux->next)
			if(strcasecmp(aux->name, str)==0){
				printf("Symbol %s already defined\n", aux->name);
				exit(0);
			}
		
		previous->next=newSymbol;	//adiciona ao final da lista
	}
	else	//symtab tem um elemento -> o novo simbolo
		symtab=newSymbol;		
	
	return symtab; 
}

//Insere um novo identificador na cauda de uma lista ligada de simbolo
environment_list* insert_func(environment_list* funcao,  environment_list* symtab)
{	
	environment_list* newSymbol=funcao;
	environment_list* aux;
	environment_list* previous;

	newSymbol->next=NULL;	

	if(symtab)	//Se table ja tem elementos
	{	//Procura cauda da lista e verifica se simbolo ja existe (NOTA: assume-se uma tabela de simbolos globais!)
		for(aux=symtab; aux; previous=aux, aux=aux->next)
			if(strcasecmp(aux->name, funcao->name)==0){
				// ja existe uma funcao com este nome
				{
				
				
				}
				
				
				
				
				
				
				
				
				return symtab;
			}
		
		previous->next=newSymbol;	//adiciona ao final da lista
	}
	else	//symtab tem um elemento -> o novo simbolo
		symtab=newSymbol;		
		
	lista_global = insert_el(funcao->name, function, 0, lista_global);
	
	return symtab; 
}



void show_table(){
	/*table_element *aux;
	printf("===== Global Symbol Table =====\n");
	
	for(aux=pe->global; aux; aux=aux->next){
	
		switch(aux->type)
		{
			case integer:
				printf("%s\tinteger\n", aux->name);
				break;
			case real:
				printf("%s\treal\n", aux->name);
				break;
			case boolean:
				printf("%s\tboolean\n", aux->name);
				break;
			case array_integer:
				printf("%s\tarray of integer\n", aux->name);
				break;
			case array_real:
				printf("%s\tarray of real\n", aux->name);
				break;
			case array_boolean:
				printf("%s\tarray of boolean\n", aux->name);
				break;
			case reserved_id:
				printf("%s\treserved id\n", aux->name);
				break;
			case program:
				printf("%s\tprogram\n", aux->name);
				break;
			case function:
				printf("%s\tfunction\n", aux->name);
				break;
			
		}
	}
	environment_list* funcao;

	

	for(funcao=pe->funcs; funcao!= NULL; funcao=funcao->next){
		show_environment(funcao);
	}
*/


	printf("===== Global Symbol Table =====\n");
	table_element *aux;
	
	for(aux=lista_global; aux; aux=aux->next){
	
		switch(aux->type)
		{
			case integer:
				printf("%s\tinteger\n", aux->name);
				break;
			case real:
				printf("%s\treal\n", aux->name);
				break;
			case boolean:
				printf("%s\tboolean\n", aux->name);
				break;
			case array_integer:
				printf("%s\tarray of integer\n", aux->name);
				break;
			case array_real:
				printf("%s\tarray of real\n", aux->name);
				break;
			case array_boolean:
				printf("%s\tarray of boolean\n", aux->name);
				break;
			case reserved_id:
				printf("%s\treserved id\n", aux->name);
				break;
			case program:
				printf("%s\tprogram\n", aux->name);
				break;
			case function:
				printf("%s\tfunction\n", aux->name);
				break;
			
		}
	}
	
	environment_list* funcao;
	for(funcao=lista_funcoes; funcao; funcao=funcao->next){
		show_environment(funcao);
	}









}

void show_environment(environment_list* funcao){

	table_element *aux;
	
	printf("===== Function %s Symbol Table =====\n", funcao->name);

	switch(funcao->tipo_retorno)
		{
			case integer:
				printf("%s\tinteger\n", funcao->name);
				break;
			case real:
				printf("%s\treal\n", funcao->name);
				break;
			case boolean:
				printf("%s\tboolean\n", funcao->name);
				break;
			default:
				break;
		}
	for(aux=funcao->params; aux; aux=aux->next)
	{
		switch(aux->type)
		{
			case integer:
				printf("%s\tinteger\tparam\n", aux->name);
				break;
			case real:
				printf("%s\treal\tparam\n", aux->name);
				break;
			case boolean:
				printf("%s\tboolean\tparam\n", aux->name);
				break;
			case array_integer:
				printf("%s\tarray of integer\tparam\n", aux->name);
				break;
			case array_real:
				printf("%s\tarray of real\tparam\n", aux->name);
				break;
			case array_boolean:
				printf("%s\tarray of boolean\tparam\n", aux->name);
				break;
			default:
				break;
		}
	}
	for(aux=funcao->locals; aux; aux=aux->next)
	{
		switch(aux->type)
		{
			case integer:
				printf("%s\tinteger\n", aux->name);
				break;
			case real:
				printf("%s\treal\n", aux->name);
				break;
			case boolean:
				printf("%s\tboolean\n", aux->name);
				break;
			case array_integer:
				printf("%s\tarray of integer\n", aux->name);
				break;
			case array_real:
				printf("%s\tarray of real\n", aux->name);
				break;
			case array_boolean:
				printf("%s\tarray of boolean\n", aux->name);
				break;
			default:
				break;
		}
	}


}


//Procura um identificador, devolve 0 caso nao exista
table_element *search_el(char *str, table_element* symtab)
{
	table_element *aux;

	for(aux=symtab; aux; aux=aux->next)
		if(strcasecmp(aux->name, str)==0)
			return aux;

	return NULL;
}

//Procura um identificador, devolve 0 caso nao exista
table_element *search_el_global(char *str, table_element* symtab)
{
	table_element *aux;

	for(aux=symtab; aux; aux=aux->next)
		if(strcasecmp(aux->name, str)==0)
			return aux;

	return NULL;
}

//Procura um ambiente, devolve 0 caso nao exista
int search_funcao(char *str, environment_list* funcs)
{
	environment_list *aux;

	for(aux=funcs; aux; aux=aux->next)
		if(strcasecmp(aux->name, str)==0){
			return 1;
		}
		
	return 0;
}

//Procura um ambiente, devolve 0 caso nao exista
environment_list* procura_funcao(char *str)
{
	environment_list *aux;

	for(aux=lista_funcoes; aux; aux=aux->next){
		if(strcasecmp(aux->name, str)==0){
			return aux;
		}
	}
		
		
	return NULL;
}

element* procura_variavel(char* name,element* list){
	element *aux;

	for(aux=list; aux; aux=aux->next){
		if(strcasecmp(aux->name, name)==0){
			return aux;
		}
	}
	return NULL;
}


