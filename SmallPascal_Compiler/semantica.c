
/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#include "semantica.h"



extern int erros;
extern int interpretador;
extern char** argvalue;

int numero_args = 0;
void imprime_simbolos();

int indiceInicio = 0;	// inicio do array
int indiceFim = 0;	// fim do array
element* valores = NULL;


void verifica_program(is_block* iel)
{
	
	lista_global = insert_el("integer", reserved_id, 0, lista_global);
	lista_global = insert_el("real", reserved_id, 0,  lista_global);
	lista_global = insert_el("boolean", reserved_id, 0,  lista_global);
	lista_global = insert_el("true", reserved_id, 0, lista_global);
	lista_global = insert_el("false", reserved_id, 0,  lista_global);
	
	
	verifica_block(iel);

}

void verifica_block(is_block* s){

	lista_global = insert_el("paramcount", integer, 0, lista_global);
	lista_global = insert_el(s->id, program, 0, lista_global);

	switch(s->disc_d)
    	{
    		case bk1:
    			verifica_var_dec_list(s->vardecl,NULL,0);
    			verifica_rep_dec_def(s->repdecdef);
    			verifica_statement_list(s->sttl,NULL);
    			break;
    		case bk2:
    			verifica_rep_dec_def(s->repdecdef);
    			verifica_statement_list(s->sttl,NULL);
    			break;
    	}
}


void verifica_var_dec_list(is_var_dec_list* varl, char* nome_funcao, int tipo){					/*tipo 0 - global/1 -parametro/2 - var local*/
	if (varl!= NULL){
		
		
				if (varl->disc_d == d_tipo1)	
				{	
					verifica_variable_declaration(varl->dec,nome_funcao,tipo);
				}
				else
				{
					verifica_variable_declaration(varl->dec,nome_funcao,tipo);
					verifica_rep_var_dec(varl->repdec,nome_funcao,tipo);
				}	
		
	
	}
	
	
	
}

void verifica_variable_declaration(is_var_dec* vd, char* nome_funcao, int tipo){
	
		if (nome_funcao == NULL)
		{
			switch(vd->disc_d)
			{
					case d_tipox1:
						verifica_id_list(vd->idl, vd->id, variavel, 0,nome_funcao);					/*0 indica que provem de uma declist global*/
						break;
					case d_tipox2:
						if (vd->dig1 > vd->dig2){	
							printf("First index cannot be greater than last index\n");
							verifica_id_list(vd->idl, vd->id, array,0,nome_funcao);
							exit(0);
						}
						else
							indiceInicio = vd->dig1;
							indiceFim = vd->dig2;
							verifica_id_list(vd->idl, vd->id, array, 0,nome_funcao);					/*0 indica que provem de uma declist global*/
			
						break;
			}
		}
		else{
				switch(vd->disc_d)
				{
					case d_tipox1:
						verifica_id_list(vd->idl, vd->id, variavel, tipo,nome_funcao);					/*1 indica que provem de uma varlist local*/
					break;
					case d_tipox2:
						if (vd->dig1 > vd->dig2){	
							printf("First index cannot be greater than last index\n");
							verifica_id_list(vd->idl, vd->id, array,tipo,nome_funcao);
							exit(0);
						}
					else
						verifica_id_list(vd->idl, vd->id, array, tipo,nome_funcao);					/*1 indica que provem de uma declist local*/
			
					break;
			}
		}
	
	

}

void verifica_id_list(is_id_list* idl, char* idc, tipo_dados tipo, int scope, char* nome_funcao){

	is_id_list* aux;
	if (scope == 0){
		for(aux=idl; aux!=NULL; aux=aux->next)
		{
			
			if (tipo == variavel){
				if (strcasecmp(idc,"integer") == 0){
					lista_global = insert_el(aux->id, integer, 0, lista_global);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = integer;
					novo->valor.integer = 0;
					valores = insert_exp(novo,valores);
				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					lista_global = insert_el(aux->id, real, 0, lista_global);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = real;
					novo->valor.real = 0;
					valores = insert_exp(novo,valores);
					
				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					lista_global = insert_el(aux->id, boolean, 0, lista_global);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = boolean;
					novo->valor.boolean = 0;
					valores = insert_exp(novo,valores);
				}
				else {	
					printf("Unknown type %s\n", idc);
					exit(0);	
				}
			}
			else if (tipo == array){
				if (strcasecmp(idc,"integer") == 0){	
					lista_global = insert_el(aux->id, array_integer, 0, lista_global);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_integer;
					novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);

				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					lista_global = insert_el(aux->id, array_real, 0, lista_global);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_real;
					novo->valor.arrayReal = calloc((indiceFim-indiceInicio), sizeof(double));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);

				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					lista_global = insert_el(aux->id, array_boolean, 0, lista_global);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_boolean;
					novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);

				}
				else {	
					printf("Unknown type %s\n", idc);
					exit(0);	
				}	
			}	
		}
	}
	if (scope == 1){
	

		for(aux=idl; aux!=NULL; aux=aux->next)
		{
			environment_list* funcao;
			funcao = procura_funcao(nome_funcao);
		
			if (tipo == variavel){
				if (strcasecmp(idc,"integer") == 0){
					funcao->params = insert_el(aux->id, integer, 0, funcao->params);
					
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = integer;
					novo->valor.integer = 0;
					valores = insert_exp(novo,valores);
					
					funcao->nparams++;
				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					
					 funcao->params = insert_el(aux->id, real, 0, funcao->params);
					 
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = real;
					novo->valor.real = 0;
					valores = insert_exp(novo,valores);
					 funcao->nparams++;
					
				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					 funcao->params =  insert_el(aux->id, boolean, 0, funcao->params);
					 		element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = boolean;
					novo->valor.boolean = 0;
					valores = insert_exp(novo,valores);
					 funcao->nparams++;
				}
				else {	
					printf("Unknown type %s\n", idc);
					exit(0);	
				}
			}
			else if (tipo == array){
				if (strcasecmp(idc,"integer") == 0){	
					funcao->params =  insert_el(aux->id, array_integer, 1, funcao->params);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_integer;
					novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);
					funcao->nparams++;

				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					funcao->params =  insert_el(aux->id, array_real, 1, funcao->params);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_real;
					novo->valor.arrayReal = calloc((indiceFim-indiceInicio), sizeof(double));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);
					funcao->nparams++;

				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					funcao->params = insert_el(aux->id, array_boolean, 1, funcao->params);
						element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_boolean;
					novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);
					funcao->nparams++;

				}
				else {	
					printf("Unknown type %s\n", idc);
					exit(0);	
				}	
			}	
		}
	}
	
	else if (scope == 2){
		for(aux=idl; aux!=NULL; aux=aux->next)
		{	
			environment_list* funcao = procura_funcao(nome_funcao);
			table_element* temp;
			for (temp = funcao->params; temp; temp = temp->next){
				if (strcasecmp(aux->id, temp->name) == 0){
					printf("Symbol %s already defined\n", aux->id);
					exit(0);			
				}
			}			

			if (tipo == variavel){
				if (strcasecmp(idc,"integer") == 0){
					funcao->locals = insert_el(aux->id, integer, 0, funcao->locals);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = integer;
					novo->valor.integer = 0;
					valores = insert_exp(novo,valores);
				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					 funcao->locals = insert_el(aux->id, real, 0, funcao->locals);
					 lista_global = insert_el(aux->id, real, 0, lista_global);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = real;
					novo->valor.real = 0;
					valores = insert_exp(novo,valores);
					
				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					 funcao->locals =  insert_el(aux->id, boolean, 0, funcao->locals);
					 		element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = boolean;
					novo->valor.boolean = 0;
					valores = insert_exp(novo,valores);
				}
				else {	
					printf("Unknown type %s\n", idc);
					exit(0);	
				}
			}
			else if (tipo == array){
				if (strcasecmp(idc,"integer") == 0){	
					funcao->locals =  insert_el(aux->id, array_integer, 0, funcao->locals);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_integer;
					novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);

				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					funcao->locals =  insert_el(aux->id, array_real, 0, funcao->locals);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_real;
					novo->valor.arrayReal = calloc((indiceFim-indiceInicio), sizeof(double));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);

				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					funcao->locals = insert_el(aux->id, array_boolean, 0, funcao->locals);
					element* novo = (element*) malloc(sizeof(element));
					novo->name = aux->id;
					novo->type = array_boolean;
					novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					novo->inicio = indiceInicio;
					novo->fim = indiceFim;
					valores = insert_exp(novo,valores);

				}
				else {	
					printf("Unknown type %s\n", idc);
					exit(0);	
				}	
			}	
		}
	}
}


void verifica_rep_var_dec(is_rep_var_dec* repd,char* nome_funcao, int tipo){
	is_rep_var_dec* aux;

	for(aux=repd; aux!=NULL; aux=aux->next)
	{
			verifica_variable_declaration(aux->vd,nome_funcao,tipo);
	}
}



void verifica_rep_dec_def(is_rep_dec_def* repd){
	if (repd == NULL)
	{	
	}	
	else if (repd->disc_tipo == flist){
		is_rep_dec_def* aux;
		for(aux=repd; aux!=NULL; aux=aux->next){	
			if(aux->disc_d == dec)
				verifica_function_declaration(aux->dec);
			else if (aux->disc_d == def){
				verifica_function_definition(aux->def);
			}
		}
	}
	else{
		if(repd->disc_d == dec){
			verifica_function_declaration(repd->dec);
		}
		else if (repd->disc_d == def){
			verifica_function_definition(repd->def);
		}
			
	}
	
}


void verifica_function_declaration(is_function_declaration* fd){

	verifica_function_heading(fd->fh, 0);
}

char* verifica_function_heading(is_function_heading* fh, int definido){
	
	environment_list * apontador_funcao;
	
	
		apontador_funcao = malloc(sizeof(environment_list));
		apontador_funcao->next = NULL;
		
		switch(fh->disc_d)
	    	{
	    		case t1:
				apontador_funcao->definido = definido;

				int tmnh = strlen(fh->id1);
				char * newString = malloc(tmnh* sizeof (char));
				int pos = 0;
				for(;fh->id1[pos];pos++)
					newString[pos] = tolower(fh->id1[pos]);
				fh->id1 = newString;
				strcpy(apontador_funcao->name, fh->id1);


		
				if (strcasecmp(fh->id2,"integer") == 0){	
					apontador_funcao->tipo_retorno = integer;
				}
				else if (strcasecmp(fh->id2,"real") == 0){	
					apontador_funcao->tipo_retorno = real;
				}
				else if (strcasecmp(fh->id2,"boolean") == 0){	
					apontador_funcao->tipo_retorno = boolean;
				}
				else {	
					printf("Unknown type %s\n", fh->id2);
					exit(0);	
				}	
	

				int estanalista = search_funcao(fh->id1, lista_funcoes);
				
				lista_funcoes = insert_func(apontador_funcao, lista_funcoes);
				
				if (estanalista)
				{				
					environment_list* func = procura_funcao(fh->id1);
					
					if (strcasecmp(fh->id2,"integer") == 0){
						
						if (strcasecmp(fh->id2,converte(func->tipo_retorno)) != 0){	
							printf("Incompatible return type in function definition %s (got %s, required %s)\n",fh->id1, fh->id2, converte(func->tipo_retorno));
							exit(0);
						}
					}
					else if (strcasecmp(fh->id2,"real") == 0)	
					{	
					
						 
						if (strcasecmp(fh->id2,converte(func->tipo_retorno)) != 0){	
							printf("Incompatible return type in function definition %s (got %s, required %s)\n", fh->id1, fh->id2, converte(func->tipo_retorno) );
							exit(0);
						}
					
					}
					else if (strcasecmp(fh->id2,"boolean") == 0)	
					{	
					
						if (strcasecmp(fh->id2,converte(func->tipo_retorno)) != 0){	
							printf("Incompatible return type in function definition %s (got %s, required %s)\n", fh->id1, fh->id2, converte(func->tipo_retorno) );
							exit(0);
						}
					}
					else {	
						printf("Unknown type %s\n", fh->id2);
						exit(0);	
					}
					if (definido == 1 && func->definido == 0)
						func->definido = definido;
				
				
				
				}				
				return fh->id1;
				
	   		case t2:
				apontador_funcao->definido = definido;

				tmnh = strlen(fh->id1);
				newString = malloc(tmnh* sizeof (char));
				pos = 0;
				
				for(;fh->id1[pos];pos++)
					newString[pos] = tolower(fh->id1[pos]);
				fh->id1 = newString;
				strcpy(apontador_funcao->name, fh->id1);

		
				if (strcasecmp(fh->id2,"integer") == 0){	
					apontador_funcao->tipo_retorno = integer;
				}
				else if (strcasecmp(fh->id2,"real") == 0){	
					apontador_funcao->tipo_retorno = real;
				}
				else if (strcasecmp(fh->id2,"boolean") == 0){	
					apontador_funcao->tipo_retorno = boolean;
				}
				else {	
					printf("Unknown type %s\n", fh->id2);
					exit(0);	
				}	
				
			
				
				estanalista = search_funcao(fh->id1, lista_funcoes);
				
				lista_funcoes = insert_func(apontador_funcao, lista_funcoes);
				
				if (!estanalista)
					verifica_rep_formal_args(fh->repf,fh->id1);
				else{
					corresponde_argumentos_correctos(fh->repf,fh->id1);
					environment_list* func = procura_funcao(fh->id1);
					
					if (strcasecmp(fh->id2,"integer") == 0){
						
						if (strcasecmp(fh->id2,converte(func->tipo_retorno)) != 0){	
							printf("Incompatible return type in function definition %s (got %s, required %s)\n",fh->id1, fh->id2, converte(func->tipo_retorno));
							exit(0);
						}
					}
					else if (strcasecmp(fh->id2,"real") == 0)	
					{	
					
						if (strcasecmp(fh->id2,converte(func->tipo_retorno)) != 0){	
							printf("Incompatible return type in function definition %s (got %s, required %s)\n", fh->id1, fh->id2, converte(func->tipo_retorno) );
							exit(0);
						}
					
					}
					else if (strcasecmp(fh->id2,"boolean") == 0)	
					{	
					
						if (strcasecmp(fh->id2,converte(func->tipo_retorno)) != 0){	
							printf("Incompatible return type in function definition %s (got %s, required %s)\n", fh->id1, fh->id2, converte(func->tipo_retorno) );
							exit(0);
						}
					}
					else {	
						printf("Unknown type %s\n", fh->id2);
						exit(0);	
					}

					if (definido == 1 && func->definido == 0)
						func->definido = definido;

				}

				return fh->id1;
		}
	return NULL;

}

void verifica_rep_formal_args(is_rep_formal_args* repd, char* nome_funcao){

	is_rep_formal_args*  aux;

	for(aux=repd; aux!=NULL; aux=aux->next)
	{
		verifica_formal_args(aux->fa, nome_funcao);
	}

}

void verifica_formal_args(is_formal_args* vd, char* nome_funcao){

	
	switch(vd->disc_d){	
    	case tipo1:
			verifica_id_list(vd->idl, vd->id, variavel, 1, nome_funcao);						/*1 indica que a variavel pertence a uma funcao*/
			break;
		case tipo2:
			printf("Non-array parameters must be passed by value\n");
			exit(0);				
		case tipo3:
			printf("Array parameters must be passed by reference\n");
			exit(0);
		case tipo4:
			verifica_id_list(vd->idl, vd->id, array, 1, nome_funcao);							/*1 indica que a variavel pertence a uma funcao*/
			break;
	}

}

void verifica_function_definition(is_function_definition* fd){

	char* nome_funcao = verifica_function_heading(fd->fh, 1);
	verifica_function_block(fd->fb, nome_funcao);
	
}

void verifica_function_block(is_function_block* fb, char* nome_funcao){

	if (fb != NULL){
		switch(fb->disc_d)
	    	{
	    	case d_bstatement_list:
				verifica_statement_list(fb->sttl, nome_funcao);
				break;
			case d_var_bstatement_list:
				verifica_var_dec_list(fb->varl, nome_funcao, 2);
				verifica_statement_list(fb->sttl, nome_funcao);
				break;
		}
	}
}




void verifica_statement_list(is_statement_list* stlist, char* nome_funcao){
	

	if (stlist == NULL){
	}
	else if(stlist->disc_tipo == flist){
		is_statement_list* aux;

		int i = 0;
		for(aux=stlist; aux!=NULL; aux=aux->next)
		{	
			verifica_statement(aux->stt, nome_funcao);
			i++;
		}
	}
	else{
		verifica_statement(stlist->stt, nome_funcao);
	}
	
		
}
void verifica_statement(is_statement* is, char* nome_funcao){

	element* exp1;
	element* exp2;

	environment_list * funcao;
	table_element* temp;
	
	switch(is->disc_d)
    	{
		case d_begin_statementlist_end:
			verifica_statement_list(is->dataStatement.aux1,nome_funcao);
			break;
		
		case d_id_assign_expression:
		
			/*ASSIGN*/
			exp1 = verifica_expression((is_expression*)is->exp2,nome_funcao);
			if (nome_funcao == NULL){			
				temp = search_el(is->id, lista_global);
				if (temp == NULL){
					printf("Symbol %s not defined\n", is->id);
					exit(0);
				}
			}
			
			else if(nome_funcao != NULL){	
				funcao = procura_funcao(nome_funcao);
				temp = search_el(is->id, funcao->locals);
			
				if (temp== NULL){
					
					temp = search_el (is->id, funcao->params);
					if (temp == NULL){
						temp = search_el (is->id, lista_global);
						if (temp == NULL){
							printf("Symbol %s not defined\n", is->id);
							exit(0);
						}
					}
				}
			}
			if (temp->type == function){	
						
				if ((nome_funcao != NULL) && !(strcasecmp(nome_funcao,temp->name ) == 0)){
					printf("Cannot assign to type function\n");
					exit(0);			
				}
			}
			if (temp->type == integer){
				if (exp1->type != integer){
					printf("Incompatible type (got %s, required integer)\n", converte(exp1->type));
					exit(0);
				}
				else{
					if (interpretador){
						element* var = procura_variavel(is->id,valores);
						var->valor.integer = exp1->valor.integer;
					}
				}
			}
			else if (temp->type == real){
				if (exp1->type == integer){
					if (interpretador){
					element* var = procura_variavel(is->id,valores);
					var->valor.real = exp1->valor.integer;
					}
				}
				else if (exp1->type == real){
				if (interpretador){
					element* var = procura_variavel(is->id,valores);
					var->valor.real = exp1->valor.real;
					}
				}
				else{
					printf("Incompatible type (got %s, required real)\n", converte(exp1->type));
					exit(0);
				}
				
			}
			else if (temp->type == boolean){
				if (exp1->type != boolean){
					printf("Incompatible type (got %s, required boolean)\n", converte(exp1->type));
					exit(0);
				}
				else{
				if (interpretador){
					element* var = procura_variavel(is->id,valores);
					var->valor.boolean = exp1->valor.boolean;
					}
				}
			}
			else if (temp->type == function){
			
				if (nome_funcao == NULL){
					printf("Cannot assign to type function\n");
					exit(0);
				}
				else if ( strcasecmp(nome_funcao, funcao->name)==0){
					if ( exp1->type == funcao->tipo_retorno ){
					
					}
					else{
						printf("Incompatible type (got %s, required %s)\n", converte(exp1->type),converte(funcao->tipo_retorno));
						exit(0);
					}
				}
				else{
					printf("Cannot assign to type function\n");
					exit(0);				
				}
				
				
			}
			
			else{
				printf("Cannot assign to type %s\n",converte(temp->type));
				exit(0);
			}		
			break;

		case d_id_exp_exp:
			/*ASSIGN de array*/
			exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
			exp2 = verifica_expression((is_expression*)is->exp2,nome_funcao);
			if (nome_funcao == NULL){			
				temp = search_el(is->id, lista_global);
				if (temp == NULL){
					printf("Symbol %s not defined\n", is->id);
					exit(0);
				}
			}
			
			else if(nome_funcao != NULL){			
				funcao = procura_funcao(nome_funcao);
				temp = search_el(is->id, funcao->locals);
			
				if (temp== NULL){
					temp = search_el (is->id, funcao->params);
					if (temp == NULL){
						temp = search_el (is->id, lista_global);
						if (temp == NULL){
							printf("Symbol %s not defined\n", is->id);
							exit(0);
						}
					}
				}
			}
			/*
			verifica se estamos a aceder a um indice do array
			*/

			if (exp1->type != integer){
					printf("Incompatible type (got %s, required integer)\n", converte(exp1->type));
					exit(0);			
			}

			/*verifica a expressao a atribuir*/
			if (temp->type == array_integer){
				if (exp2->type != integer){
					printf("Incompatible type (got %s, required integer)\n", converte(exp2->type));
					exit(0);
				}
				else{
				if (interpretador){
					element* var = procura_variavel(is->id,valores);
					var->valor.arrayInt[exp1->valor.integer - var->inicio] = exp2->valor.integer;
					}
				}
			}
			else if (temp->type == array_real){
				if (exp2->type == real || exp2->type == integer){
					if (interpretador){
						element* var = procura_variavel(is->id,valores);
						var->valor.arrayReal[exp1->valor.integer - var->inicio] = exp2->valor.real;
					}
				}
				else{
					printf("Incompatible type (got %s, required real)\n", converte(exp2->type));
					exit(0);
				}

			}
			else if (temp->type == array_boolean){
				if (exp2->type != boolean){
					printf("Incompatible type (got %s, required boolean)\n", converte(exp2->type));
					exit(0);
				}
				else{
				if (interpretador){
					element* var = procura_variavel(is->id,valores);
					var->valor.arrayInt[exp1->valor.integer - var->inicio] = exp2->valor.boolean;
					}
				}
			}	
			else{
				printf("Incompatible type (got %s, required array)\n", converte(temp->type));
				exit(0);
			}
			
			break;
			
		case d_exp_stt:
			
			exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
			
			if (exp1->type != boolean){
				printf("Incompatible type (got %s, required boolean)\n", converte(exp1->type));
				exit(0);
			}
			if (interpretador){
				if (exp1->valor.boolean){
					if (is->stt!= NULL)	verifica_statement(is->stt,nome_funcao);
				}
			}
			else {
				if (is->stt!= NULL)	verifica_statement(is->stt,nome_funcao);
			}
			break;
			
		case d_exp_stt_stt:
			exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
			if (exp1->type != boolean){
				printf("Incompatible type (got %s, required boolean)\n", converte(exp1->type));
				exit(0);
			
			}
			if (interpretador){
				if (exp1->valor.boolean){
					if (is->stt!= NULL)	verifica_statement(is->stt,nome_funcao);
				}
				else{
					if (is->stt2!= NULL)	verifica_statement(is->stt2,nome_funcao);
				}
			}
			else {
				if (is->stt!= NULL)	verifica_statement(is->stt,nome_funcao);
				if (is->stt2!= NULL)	verifica_statement(is->stt2,nome_funcao);
			}
			break;
		case d_wexp_stt:
			exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
			if (exp1->type != boolean){
				printf("Incompatible type (got %s, required boolean)\n", converte(exp1->type));
				exit(0);
			}
			if (is->stt!= NULL)			
						verifica_statement(is->stt,nome_funcao);
			
			if (interpretador){
				while(1){
				exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
				
					if (exp1->valor.boolean	){		
				
						if (is->stt!= NULL)			
							verifica_statement(is->stt,nome_funcao);
					}
					else break;
				}
			}
			break;

		case d_id_exp_exp_stt:

			exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
			exp2 = verifica_expression((is_expression*)is->exp2,nome_funcao);
			if (exp1->type != integer){
				printf("Incompatible type (got %s, required integer)\n", converte(exp1->type));
				exit(0);
			}
			if (exp2->type != integer){
				printf("Incompatible type (got %s, required integer)\n", converte(exp2->type));
				exit(0);
			}
			if (interpretador){

				element* var = procura_variavel( is->id,valores);
				var->valor.integer = exp1->valor.integer;

				for (;var->valor.integer <= exp2->valor.integer; var->valor.integer++){
					if (is->stt!= NULL)verifica_statement(is->stt,nome_funcao);
				}
			}
			else{  
				if (is->stt!= NULL)verifica_statement(is->stt,nome_funcao);
			}
			break;
		case d_id_exp_dexp_stt:
			exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
			exp2 = verifica_expression((is_expression*)is->exp2,nome_funcao);
			if (exp1->type != integer){
				printf("Incompatible type (got %s, required integer)\n", converte(exp1->type));
				exit(0);
			}
			if (exp2->type != integer){
				printf("Incompatible type (got %s, required integer)\n", converte(exp2->type));
				exit(0);
			}
			
			if (interpretador){
				element* var = procura_variavel( is->id,valores);
				var->valor.integer = exp1->valor.integer;
		
				for (;var->valor.integer >= exp2->valor.integer; var->valor.integer--){
					if (is->stt!= NULL)verifica_statement(is->stt,nome_funcao);
				}
			}
			else{  
				if (is->stt!= NULL)verifica_statement(is->stt,nome_funcao);
			}

			break;

		case d_tokens_exp:

			exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
			if (exp1->type != integer){
				printf("Incompatible type (got %s, required integer)\n", converte(exp1->type));
				exit(0);
			}
			
			//verificar se o id existe na tabela de simbolos como sendo um inteiro
			if (nome_funcao == NULL){			
				temp = search_el(is->id, lista_global);
				if (temp == NULL){
					printf("Symbol %s not defined\n", is->id);
					exit(0);
				}
			}
			
			else if(nome_funcao != NULL){			
				funcao = procura_funcao(nome_funcao);
				temp = search_el(is->id, funcao->locals);
			
				if (temp== NULL){
					temp = search_el (is->id, funcao->params);
					if (temp == NULL){
						temp = search_el (is->id, lista_global);
						if (temp == NULL){
							printf("Symbol %s not defined\n", is->id);
							exit(0);
						}
					}
				}
			}
		
			if (temp->type == integer){
			
				if (interpretador){
					element* var = procura_variavel(is->id,valores);
					var->valor.integer = 42;
					sscanf(argvalue[exp1->valor.integer],"%d",&var->valor.integer);
				}

			}
			else if(temp->type == real){
				if (interpretador){
					element* var = procura_variavel(is->id,valores);
					var->valor.real = 42.0;
					sscanf(argvalue[exp1->valor.integer],"%lf",&var->valor.real);
				}
			}
			else{
					printf("Incompatible type (got %s, required integer or real)\n", converte(temp->type));
					exit(0);
			}

			break;
		case d_writeln_exp:
			
			exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
			if (exp1->type == integer){
				if (interpretador)
					printf("%d\n",exp1->valor.integer);
			}
			else if (exp1->type == real){
				if (interpretador)
					printf("%lf\n",exp1->valor.real);
			}
			else if(exp1->type == boolean){
				if (interpretador){
					if(exp1->valor.boolean == 1)
						printf("true\n");
					else
						printf("false\n");
				}
			
			}
			else{
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);	
			}
			//if (temp->type == integer) printf("%d\n",temp->valor.digseq);
			//else if (temp->type == real) printf("%f\n",temp->valor.real);
			//else if (temp->type == boolean && temp->valor.boolean == 1) printf("true\n");
			//else if (temp->type == boolean && temp->valor.boolean == 0) printf("false\n");			
			break;
		default:
			break;
   	}
}






element* verifica_expression(is_expression* ie, char* nome_funcao){

	element* aux = (element*) malloc (sizeof(element));
	element* temp2;
	element* exp1;
	element* exp2;
	table_element* apontador_parametros;
	environment_list* func;
	
	
	environment_list * funcao;
	table_element* temp;
	int encontrado = 0;
	

	switch(ie->disc_d)
    	{
    	case d_digseq:
			aux->type = integer;
			if (interpretador) aux->valor.integer = ie->dataExpression.digseq;
			return aux;
		case d_realnumber:
			aux->type = real;
			if (interpretador) aux->valor.real = ie->dataExpression.realnumber;
			return aux;
		case d_OP1and:

			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (exp1->valor.integer && exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				printf("Operator and cannot be applied to types integer, real\n");
				exit(0);
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator and cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				printf("Operator and cannot be applied to types real, integer\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == real){
				printf("Operator and cannot be applied to types real, real\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator and cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator and cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator and cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.boolean && exp2->valor.boolean);
				return aux;
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}	
			else{
				printf("Operator and cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP1or:
	
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (exp1->valor.integer || exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				printf("Operator or cannot be applied to types integer, real\n");
				exit(0);
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator or cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				printf("Operator or cannot be applied to types real, integer\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == real){
				printf("Operator or cannot be applied to types real, real\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator or cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator or cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator or cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.boolean || exp2->valor.boolean);
				return aux;
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator or cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP21:

			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer < exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer < exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator < cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real < exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real < exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator < cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator < cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator < cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.boolean < exp2->valor.boolean);
				return aux;
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator < cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP22:
			
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer > exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer > exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator > cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real > exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real > exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator > cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator > cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator > cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.boolean > exp2->valor.boolean);
				return aux;
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator > cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP23:
			
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer == exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer == exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator = cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real == exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real == exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator = cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator = cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator = cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.boolean == exp2->valor.boolean);
				return aux;
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator = cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP24:
				
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer != exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer != exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator <> cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real != exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real != exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator <> cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator <> cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator <> cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.boolean != exp2->valor.boolean);
				return aux;
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator <> cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP25:
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer <= exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer <= exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator <= cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real <= exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real <= exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator <= cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator <= cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator <= cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.boolean <= exp2->valor.boolean);
				return aux;
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator <= cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP26:
	
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer >= exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.integer >= exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator >= cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real >= exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.real >= exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator >= cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator >= cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator >= cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = (exp1->valor.boolean >= exp2->valor.boolean);
				return aux;
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator >= cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP3plus:	
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (exp1->valor.integer + exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.integer + exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator + cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.real + exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.real + exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator + cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator + cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator + cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				printf("Operator + cannot be applied to types boolean, boolean\n");
				exit(0);
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator + cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP3minus:
			
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (exp1->valor.integer - exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.integer - exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator - cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.real - exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.real - exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator - cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator - cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator - cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				printf("Operator - cannot be applied to types boolean, boolean\n");
				exit(0);
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator - cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP4Mult:
		
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (exp1->valor.integer * exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.integer * exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator * cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.real * exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.real * exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator * cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator * cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator * cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				printf("Operator * cannot be applied to types boolean, boolean\n");
				exit(0);
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator * cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP4RealDiv:
	
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.integer / (double)exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.integer / exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator / cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.real / exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp1->valor.real / exp2->valor.real);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator / cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator / cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator / cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				printf("Operator / cannot be applied to types boolean, boolean\n");
				exit(0);
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator / cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP4Div:

			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (exp1->valor.integer / exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
			printf("Operator div cannot be applied to types integer, real\n");
				exit(0);
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator div cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				printf("Operator div cannot be applied to types real, integer\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == real){
				printf("Operator div cannot be applied to types real, real\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator div cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator div cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator div cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				printf("Operator div cannot be applied to types boolean, boolean\n");
				exit(0);
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator div cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP4Mod:
	
			exp1 = verifica_expression(ie->exp1,nome_funcao);
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (exp1->valor.integer % exp2->valor.integer);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
			printf("Operator mod cannot be applied to types integer, real\n");
				exit(0);
			}
			else if  (exp1->type == integer && exp2->type == boolean){
				printf("Operator mod cannot be applied to types integer, boolean\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == integer){
				printf("Operator mod cannot be applied to types real, integer\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == real){
				printf("Operator mod cannot be applied to types real, real\n");
				exit(0);
			}
			else if  (exp1->type == real && exp2->type == boolean){
				printf("Operator mod cannot be applied to types real, boolean\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == integer){
				printf("Operator mod cannot be applied to types boolean, integer\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == real){
				printf("Operator mod cannot be applied to types boolean, real\n");
				exit(0);
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				printf("Operator mod cannot be applied to types boolean, boolean\n");
				exit(0);
			}
			else if (exp1->type == array_integer || exp1->type == array_real || exp1->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp1->type));
				exit(0);
			}
			else if (exp2->type == array_integer || exp2->type == array_real || exp2->type == array_boolean){
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(exp2->type));
				exit(0);	
			}
			else{
				printf("Operator mod cannot be applied to types %s, %s\n", converte(exp1->type),converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP3Plus_exp:
	
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (exp2->valor.integer);
				return aux;
			}
			else if  (exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (exp2->valor.real);
				return aux;
			}
			else if  (exp2->type == boolean){
				printf("Operator + cannot be applied to type boolean\n");
				exit(0);
			}
			else{
				printf("Operator + cannot be applied to type %s\n",converte(exp2->type) );
				exit(0);
			}
			break;
		case d_OP3Minus_exp:
	
			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp2->type == integer){
				aux->type = integer;
				if (interpretador) aux->valor.integer = (-exp2->valor.integer);
				return aux;
			}
			else if  (exp2->type == real){
				aux->type = real;
				if (interpretador) aux->valor.real = (-exp2->valor.real);
				return aux;
			}
			else if  (exp2->type == boolean){
				printf("Operator - cannot be applied to type boolean\n");
				exit(0);
			}
			else{
				printf("Operator - cannot be applied to type %s\n",converte(exp2->type) );
				exit(0);
			}
			break;
		case d_NOT_exp:

			exp2 = verifica_expression(ie->exp2,nome_funcao);
			
			if (exp2->type == integer){
				printf("Operator not cannot be applied to type integer\n");
				exit(0);
			}
			else if  (exp2->type == real){
				printf("Operator not cannot be applied to type real\n");
				exit(0);
			}
			else if  (exp2->type == boolean){
				aux->type = boolean;
				if (interpretador){
					if (exp2->valor.boolean == 1){
						aux->valor.boolean = 0;
					}
					else
						aux->valor.boolean = 1;}
					return aux;
			}
			else{
				printf("Operator not cannot be applied to type %s\n",converte(exp2->type) );
				exit(0);
			}
			break;
		case d_id_lbrac_expression_rbrac:

			if(nome_funcao != NULL){			
				funcao = procura_funcao(nome_funcao);
				temp = search_el(ie->id, funcao->locals);
			
				if (temp == NULL){
					temp = search_el (ie->id, funcao->params);
					if (temp == NULL){
						temp = search_el (ie->id, lista_global);
						if (temp == NULL){
							printf("Symbol %s not defined\n", ie->id);
							exit(0);
						}
						else{
							encontrado = 1;
						}
					}
					else{
						encontrado = 1;
					}
				}
				else{
					encontrado = 1;
				}
			}
			else {
				temp = search_el (ie->id, lista_global);
				if (temp == NULL){
					printf("Symbol %s not defined\n", ie->id);
					exit(0);
				}
				else{
					encontrado = 1;
				}
			}


			if (encontrado == 1){
				if( temp-> type == array_integer || temp-> type == array_boolean || temp-> type == array_real)
				{
					exp2 = verifica_expression(ie->exp2,nome_funcao);
					if (exp2->type == boolean){
						printf("Incompatible type (got boolean, required integer)\n");
						exit(0);
					}
					else if (exp2->type == real){
						printf("Incompatible type (got real, required integer)\n");
						exit(0);					
					}
					else if (exp2->type == integer){
						if (temp-> type == array_integer){
							element* var = procura_variavel(ie->id,valores);
							aux->type = integer;
							if (interpretador) aux->valor.integer = var->valor.arrayInt[  exp2->valor.integer - var->inicio];
							return aux;
						}
						else if (temp-> type == array_boolean){
							element* var = procura_variavel(ie->id,valores);
							aux->type = boolean;
							if (interpretador) aux->valor.boolean = var->valor.arrayInt[  exp2->valor.integer - var->inicio];
							return aux;
						}
						else if (temp-> type == array_real){
							element* var = procura_variavel(ie->id,valores);
							aux->type = real;
							if (interpretador) aux->valor.real = var->valor.arrayReal[  exp2->valor.integer - var->inicio];
							return aux;
						}
						else{
							//ALTERADO por causa SEGMENTATION FAULT
							printf("Incompatible type (got %s, required array)\n", converte(temp->type));
							exit(0);
						}
					}
					else{
						printf("Incompatible type (got %s, required integer)\n", converte(exp2->type));
						exit(0);
					}
				}
				else{
					printf("Incompatible type (got %s, required array)\n", converte(temp->type));
					exit(0);
				}

			}
			
			break;

		case  d_id_lparen_rep_expression_rparen:
			
			temp = search_el(ie->id, lista_global);
			if (temp == NULL){
				printf("Symbol %s not defined\n", ie->id);
				exit(0);			
			}
			else if (temp->type == function){
				
				func = procura_funcao(ie->id);
				temp2 = verifica_rep_expression(ie->id, ie->dataExpression.aux, nome_funcao);	
				apontador_parametros = func->params;
				int narg = 0;
				for (; temp2!= NULL;temp2= temp2->next){
					narg++;
					if(temp2->type == apontador_parametros->type || (temp2->type == integer && apontador_parametros->type == real)  ){
					}
					else{
						printf("Incompatible type of parameter %d in call to function %s (got %s, required %s)\n", narg,temp->name,converte_com_array_of(temp2->type),converte_com_array_of(apontador_parametros->type));
						exit(0);
					}
					apontador_parametros = apontador_parametros->next;
				}
				
				aux->type = func->tipo_retorno;
				//aux->valor.integer = 0;
				return aux;
				
			}
			else{
				printf("Cannot call %s\n", temp->name);
				exit(0);
			}

			break;
		case d_id:
		
			if (nome_funcao == NULL){	
				temp = search_el(ie->id, lista_global);
				if (temp == NULL){
					printf("Symbol %s not defined\n", ie->id);
					exit(0);
				}
			}
			
			else if(nome_funcao != NULL){			
				funcao = procura_funcao(nome_funcao);
				temp = search_el(ie->id, funcao->locals);
			
				if (temp== NULL){
					temp = search_el (ie->id, funcao->params);
					if (temp == NULL){
						temp = search_el (ie->id, lista_global);
						if (temp == NULL){
							printf("Symbol %s not defined\n", ie->id);
							exit(0);
						}
					}
				}
			}
			if (temp->type == boolean){
				element* var = procura_variavel(ie->id,valores);
				aux->type = var->type;
				if (interpretador) aux->valor.boolean = var->valor.boolean;
				return aux;
			}
			else if (temp->type == reserved_id && strcasecmp(ie->id,"true")==0){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = 1;
				return aux;
			}
			else if (temp->type == reserved_id && strcasecmp(ie->id,"false")==0){
				aux->type = boolean;
				if (interpretador) aux->valor.boolean = 0;
				return aux;
			}
			else if (temp->type == integer){

				if (strcasecmp(ie->id,"paramcount")==0){
					aux->type = integer;
					return aux;
				}
				else{
					element* var = procura_variavel(ie->id,valores);
					aux->type = var->type;
					if (interpretador) aux->valor.integer = var->valor.integer;
					return aux;
				}
			}
			else if (temp->type == real){
				element* var = procura_variavel(ie->id,valores);
				aux->type = var->type;
				if (interpretador) aux->valor.real = var->valor.real;
				return aux;
			}

			else if (temp->type == array_integer){
				element* var = procura_variavel(ie->id,valores);
				aux->type = var->type;
				if (interpretador) aux->valor.arrayInt = var->valor.arrayInt;
				return aux;
			}
			else if (temp->type == array_real){
				element* var = procura_variavel(ie->id,valores);
				aux->type = var->type;
				if (interpretador) aux->valor.arrayReal = var->valor.arrayReal;
				return aux;
			}
			else if (temp->type == array_boolean){
				element* var = procura_variavel(ie->id,valores);
				aux->type = var->type;
				if (interpretador) aux->valor.arrayInt = var->valor.arrayInt;
				return aux;
			}
			else{
				printf("Incompatible type (got %s, required boolean or integer or real)\n", converte(temp->type));
				exit(0);	
			}
			break;
		default:
			break;
   	}
	return NULL;
}


element* verifica_rep_expression(char * nome_funcao_destino,is_rep_expression* rep_ex, char* nome_funcao_onde_esta){
	
	environment_list* funcao = procura_funcao(nome_funcao_destino);
	element* chamada = NULL;
	element* tipo_dados;
	is_rep_expression* aux;

	int numero_args = 0;
	for(aux=rep_ex; aux!=NULL; aux=aux->next)
	{
		numero_args++; 
	}
	
	if (funcao->nparams != numero_args){
		printf("Incompatible number of parameters in call to function %s (got %d, required %d)\n", nome_funcao_destino, numero_args, funcao->nparams);
		exit(0);
	}
	else{
		
		for(aux=rep_ex; aux!=NULL; aux=aux->next){
			tipo_dados = verifica_expression(aux->exp, nome_funcao_onde_esta);
			chamada = insert_exp( tipo_dados , chamada);	

		}
	
	}	
	return chamada;
	
}



void corresponde_argumentos_correctos(is_rep_formal_args* repd ,char* nome_funcao){

	is_rep_formal_args*  aux;
	
	numero_args = 0;
		
	for(aux=repd; aux!=NULL; aux=aux->next)
		corresponde_argumentos(aux->fa, nome_funcao, contar);
	
	environment_list * funcao = procura_funcao(nome_funcao);
	
	
	if (numero_args == funcao->nparams){
		numero_args = 0;
		for(aux=repd; aux!=NULL; aux=aux->next)
			corresponde_argumentos(aux->fa, nome_funcao, verificar);
	}
	else{
		printf("Incompatible number of formal arguments in function definition %s (got %d, required %d)\n", nome_funcao, numero_args, funcao->nparams);
		exit(0);
	}
}

void corresponde_argumentos(is_formal_args* vd, char* nome_funcao, disc_opcao opcao){
	switch(vd->disc_d)
    	{	
    		
    	case tipo1:
			corresponde_id_list(vd->idl, vd->id,0 ,variavel, nome_funcao, opcao);					
			break;
		case tipo2:
			corresponde_id_list(vd->idl, vd->id,1 ,variavel, nome_funcao, opcao);					
			break;
		case tipo3:
			corresponde_id_list(vd->idl, vd->id,0, array, nome_funcao, opcao);						
			break;
		case tipo4:
			corresponde_id_list(vd->idl, vd->id,1, array, nome_funcao, opcao);						
			break;
		}
		

}

void corresponde_id_list(is_id_list* idl, char* idc, int passed_by_ref, tipo_dados tipo , char* nome_funcao, disc_opcao opcao){
	if (opcao == contar){
		is_id_list* aux;
	
		for(aux=idl; aux!=NULL; aux=aux->next){
			numero_args++;
		}
	}
	else if (opcao == verificar){
		environment_list * funcao = procura_funcao(nome_funcao);
		is_id_list* a_verificar = idl;
		table_element* inseridos = funcao->params; 
		
		/*coloca o ponteiro no argumento inserido coorespondente a quantidade de argumentos ja lidos, funciona no caso de ter varios tipos de argumentos
		nesse caso tem varias id lista e n começa sempre de inicio tem de ser colocado no sitio que cooresponde ao argumento actual*/
		
		int k = 0;
		for(;k<numero_args;k++) inseridos = inseridos->next;
	
		
		while(a_verificar!=NULL && inseridos != NULL){

					
		
					if (strcasecmp(a_verificar->id,inseridos->name) != 0){					// se n é consistente
						printf("Incompatible name in formal arg %d in function definition %s (got %s, required %s)\n", numero_args+1, nome_funcao, a_verificar->id, inseridos->name);
						exit(0);
					}	
					else if (tipo == variavel){
						if (inseridos->passed_by_reference != passed_by_ref){
							printf("Incompatible passing of formal arg %d in function definition %s\n", numero_args+1, nome_funcao);
							exit(0);
						}
						if (strcasecmp(idc,"integer") == 0){	
							if (inseridos->type != integer){
								printf("Incompatible type in formal arg %d in function definition %s (got %s, required %s)\n", numero_args+1, nome_funcao, "integer", converte_com_array_of(inseridos->type));
								exit(0);
							}
						}
						else if (strcasecmp(idc,"real") == 0)	
						{	
							if (inseridos->type != real){
								printf("Incompatible type in formal arg %d in function definition %s (got %s, required %s)\n", numero_args+1, nome_funcao, "real", converte_com_array_of(inseridos->type));
								exit(0);
							}
						}
						else if (strcasecmp(idc,"BOOLEAN") == 0)	
						{	
								if (inseridos->type != boolean){
									printf("Incompatible type in formal arg %d in function definition %s (got %s, required %s)\n", numero_args+1, nome_funcao, "boolean", converte_com_array_of(inseridos->type));
									exit(0);	
								}
						}
						else {	
							printf("Unknown type %s\n", idc);
							exit(0);	
						}	
					}
					else if (tipo == array){
						if (inseridos->passed_by_reference != passed_by_ref){
							printf("Incompatible passing of formal arg %d in function definition %s\n", numero_args+1, nome_funcao);
							exit(0);
						}
							
						if (strcasecmp(idc,"INTEGER") == 0){	
							if (inseridos->type != array_integer){
								printf("Incompatible type in formal arg %d in function definition %s (got %s, required %s)\n", numero_args+1, nome_funcao, "array", converte_com_array_of(inseridos->type));
								exit(0);	
							}
						}
						else if (strcasecmp(idc,"REAL") == 0)	
						{	
								if (inseridos->type != array_real){
									printf("Incompatible type in formal arg %d in function definition %s (got %s, required %s)\n", numero_args+1, nome_funcao, "array", converte_com_array_of(inseridos->type));
									exit(0);	
								}
						}
						else if (strcasecmp(idc,"BOOLEAN") == 0)	
						{	
								if (inseridos->type != array_boolean){
									printf("Incompatible type in formal arg %d in function definition %s (got %s, required %s)\n", numero_args+1, nome_funcao, "array", converte_com_array_of(inseridos->type));
									exit(0);	
								}
						}
						else {	
							printf("Unknown type %s\n", idc);
							exit(0);	
						}	
					}
	
			
			numero_args++;
					
			inseridos = inseridos->next;
			a_verificar = a_verificar->next;
		}
	}
}












int verifica_se_todas_funcoes_estao_definidas(){
	
	environment_list *aux;

	for(aux=lista_funcoes; aux; aux=aux->next){
		if (aux->definido == 0){
			printf("Function %s declared but not defined\n", aux->name);
			exit(0);
		}
	}
	return 0;
}

char* converte(basic_type tipo){
	char *msg = (char *) malloc(32 * sizeof(char));  	
	
	if(tipo == integer){
		strcpy(msg,"integer");
	}
	else if(tipo == boolean){
		strcpy(msg,"boolean");
	}
	else if(tipo == real){
		strcpy(msg,"real");
	}
	else if(tipo == array_integer){
		strcpy(msg,"array");
	}
	else if(tipo == array_boolean){
		strcpy(msg,"array");
	}
	else if(tipo == array_real){
		strcpy(msg,"array");
	}
	else if (tipo == function){
		strcpy(msg,"function");
	}
	else if (tipo == program){
		strcpy(msg,"program");
	}
	else if (tipo == reserved_id){
		strcpy(msg,"reserved id");
	}
	
	return msg;
}


char* converte_com_array_of(basic_type tipo){
	char *msg = (char *) malloc(32 * sizeof(char));  	
	
	if(tipo == integer){
		strcpy(msg,"integer");
	}
	else if(tipo == boolean){
		strcpy(msg,"boolean");
	}
	else if(tipo == real){
		strcpy(msg,"real");
	}
	else if(tipo == array_integer){
		strcpy(msg,"array of integer");
	}
	else if(tipo == array_boolean){
		strcpy(msg,"array of boolean");
	}
	else if(tipo == array_real){
		strcpy(msg,"array of real");
	}
	else if (tipo == function){
		strcpy(msg,"function");
	}
	else if (tipo == program){
		strcpy(msg,"program");
	}
	else if (tipo == reserved_id){
		strcpy(msg,"reserved id");
	}
	
	return msg;
}


void imprime_simbolos(){
	element *aux;
	int i = 0;
	
	for(aux=valores; aux; aux=aux->next){
		if (aux->type == integer){
			printf("nome: %s tipo: integer valor: %d\n", aux->name, aux->valor.integer);
		}
		else if (aux->type == real){
			printf("nome: %s tipo: real valor: %lf\n", aux->name, aux->valor.real);
		}
		else if(aux->type == boolean){
			printf("nome: %s tipo: boolean valor: %d\n", aux->name, aux->valor.integer);
		}
		else if(aux->type == array_integer){
			printf("nome: %s tipo: array of integer\t[%d..%d]\t", aux->name, aux->inicio, aux->fim);
			for(i = aux->inicio; i<=aux->fim; i++){
				printf("%d ", aux->valor.arrayInt[i-aux->inicio] );
			}
			puts("");
		}
		else if(aux->type == array_real){
			printf("nome: %s tipo: array of real\t[%d..%d]\t", aux->name, aux->inicio, aux->fim);
			for(i = aux->inicio; i<=aux->fim; i++){
				printf("%.2lf ", aux->valor.arrayReal[i-aux->inicio] );
			}
			puts("");
		}
		else if(aux->type == array_boolean){
			printf("nome: %s tipo: array of integer\t[%d..%d]\t", aux->name, aux->inicio, aux->fim);
			for(i = aux->inicio; i<=aux->fim; i++){
				printf("%d ", aux->valor.arrayInt[i-aux->inicio] );
			}
			puts("");
		}
		
	}
		

}

