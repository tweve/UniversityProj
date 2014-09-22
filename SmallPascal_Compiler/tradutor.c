
/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#include "tradutor.h"
#include "estruturas.h"
#include "tabelaSimbolos.h"
#include <stdio.h>

char express[] = "";

int labeln = 0;
int iInicio = 0;
int	iFim = 0;


void translate(is_block* iel)
{
	translate_header();
	translate_block(iel);
	translate_footer();
}

void translate_header()
{
	printf("#include \"frame.h\"\n");
	printf("#include <stdlib.h>\n");
	printf("#include <stdio.h>\n\n");
}

void translate_footer()
{
	printf("\treturn 0;\n");
	printf("\n}\n\n");
}

void translate_block(is_block* s){

	//lista_global = insert_el("paramcount", integer, 0, lista_global);
	//lista_global = insert_el(s->id, program, 0, lista_global);

	//fprintf( , "sp=(frame*)malloc(sizeof(frame));\n");

	printf("int main(int argc, char **argv)\n{\n");
	printf("\tframe* sp=NULL;\n");
	printf("\tsp = (frame*)malloc(sizeof(frame));\n");

	switch(s->disc_d)
    	{
    		case bk1:
    			translate_var_dec_list(s->vardecl,NULL,0);
    			//translate_rep_dec_def(s->repdecdef);
    			translate_statement_list(s->sttl,NULL);
    			break;
    		case bk2:
    			//translate_rep_dec_def(s->repdecdef);
    			translate_statement_list(s->sttl,NULL);
    			break;
    	}
}


void translate_var_dec_list(is_var_dec_list* varl, char* nome_funcao, int tipo){					/*tipo 0 - global/1 -parametro/2 - var local*/
	if (varl!= NULL){
		
				if (varl->disc_d == d_tipo1)	
				{	
					translate_variable_declaration(varl->dec,nome_funcao,tipo );
				}
				else
				{
					translate_variable_declaration(varl->dec,nome_funcao,tipo );
					translate_rep_var_dec(varl->repdec,nome_funcao,tipo );
				}	
	}
}

void translate_variable_declaration(is_var_dec* vd, char* nome_funcao, int tipo){
	
		if (nome_funcao == NULL)
		{
			switch(vd->disc_d)
			{
					case d_tipox1:
						translate_id_list(vd->idl, vd->id, variavel, 0,nome_funcao );					/*0 indica que provem de uma declist global*/
						break;

					case d_tipox2:
						
						iInicio = vd->dig1;
						iFim = vd->dig2;
						translate_id_list(vd->idl, vd->id, array, 0,nome_funcao );					/*0 indica que provem de uma declist global*/
						
						break;
			}
		}
		else{
				switch(vd->disc_d)
				{
					case d_tipox1:
						translate_id_list(vd->idl, vd->id, variavel, tipo,nome_funcao );					/*1 indica que provem de uma varlist local*/
					break;
					case d_tipox2:
					
						translate_id_list(vd->idl, vd->id, array, tipo,nome_funcao );					/*1 indica que provem de uma declist local*/
			
					break;
			}
		}

}

void translate_id_list(is_id_list* idl, char* idc, tipo_dados tipo, int scope, char* nome_funcao){

	is_id_list* aux;
	if (scope == 0){
		for(aux=idl; aux!=NULL; aux=aux->next)
		{
			
			if (tipo == variavel){
				if (strcasecmp(idc,"integer") == 0){

					table_element* te;
					te = search_el_global(aux->id,lista_global);
					printf("int g%d;\n", te->offset);


					//lista_global = insert_el(aux->id, integer, 0, lista_global);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = integer;
					//novo->valor.integer = 0;
					//valores = insert_exp(novo,valores);
				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					
					table_element* te;
					te = search_el_global(aux->id,lista_global);
					printf("double g%d;\n", te->offset);

					//lista_global = insert_el(aux->id, real, 0, lista_global);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = real;
					//novo->valor.real = 0;
					//valores = insert_exp(novo,valores);
					
				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					table_element* te;

					te = search_el_global(aux->id,lista_global);
					printf("int g%d;\n", te->offset);


					//lista_global = insert_el(aux->id, boolean, 0, lista_global);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = boolean;
					//novo->valor.boolean = 0;
					//valores = insert_exp(novo,valores);
				}
				else {	
					//printf("Unknown type %s\n", idc);
					//exit(0);	
				}
			}
			else if (tipo == array){
				if (strcasecmp(idc,"integer") == 0){
					
					table_element* te;

					te = search_el(aux->id,lista_global);
					te->inicio = iInicio;
					te->fim = iFim;
					printf("int *g%d;\n", te->offset);
					printf("g%d = (int*)malloc(%d* sizeof(int));\n", te->offset,te->fim-te->inicio+1);

					//lista_global = insert_el(aux->id, array_integer, 0, lista_global);
					//element* novo = (element*) malloc(sizeof(element));
				//	novo->name = aux->id;
					//novo->type = array_integer;
					//novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);

				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					table_element* te;

					te = search_el_global(aux->id,lista_global);
					te->inicio = iInicio;
					te->fim =iFim;
					printf("double *g%d;\n", te->offset);
					printf("g%d = (double*)malloc(%d* sizeof(double));\n", te->offset,te->fim-te->inicio+1);


					//lista_global = insert_el(aux->id, array_real, 0, lista_global);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = array_real;
					//novo->valor.arrayReal = calloc((indiceFim-indiceInicio), sizeof(double));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);

				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					table_element* te;

					te = search_el_global(aux->id,lista_global);
					te->inicio = iInicio;
					te->fim = iFim;
					printf("int *g%d;\n", te->offset);
					printf("g%d = (int*)malloc(%d* sizeof(int));\n", te->offset,te->fim-te->inicio+1);


					//lista_global = insert_el(aux->id, array_boolean, 0, lista_global);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = array_boolean;
					//novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);

				}
			}	
		}
	}
	if (scope == 1){
	

		for(aux=idl; aux!=NULL; aux=aux->next)
		{
			//environment_list* funcao;
			//funcao = procura_funcao(nome_funcao);
		
			if (tipo == variavel){
				if (strcasecmp(idc,"integer") == 0){

					table_element* te;

					te = search_el_global(aux->id,lista_global);
					
					printf("int g%d;\n", te->offset);

					//funcao->params = insert_el(aux->id, integer, 0, funcao->params);

					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = integer;
					//novo->valor.integer = 0;
					//valores = insert_exp(novo,valores);
					
					//funcao->nparams++;
				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					
					// funcao->params = insert_el(aux->id, real, 0, funcao->params);
					 
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = real;
					//novo->valor.real = 0;
					//valores = insert_exp(novo,valores);
					 //funcao->nparams++;
					
				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					 //funcao->params =  insert_el(aux->id, boolean, 0, funcao->params);
					 //		element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
				//	novo->type = boolean;
					//novo->valor.boolean = 0;
					//valores = insert_exp(novo,valores);
					 //funcao->nparams++;
				}
				else {	
					//printf("Unknown type %s\n", idc);
					//exit(0);	
				}
			}
			else if (tipo == array){
				if (strcasecmp(idc,"integer") == 0){	
					//funcao->params =  insert_el(aux->id, array_integer, 1, funcao->params);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = array_integer;
					//novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);
					//funcao->nparams++;

				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					//funcao->params =  insert_el(aux->id, array_real, 1, funcao->params);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = array_real;
					//novo->valor.arrayReal = calloc((indiceFim-indiceInicio), sizeof(double));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);
					//funcao->nparams++;

				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					//funcao->params = insert_el(aux->id, array_boolean, 1, funcao->params);
					//	element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = array_boolean;
					//novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);
					//funcao->nparams++;

				}
				else {	
					//printf("Unknown type %s\n", idc);
					//exit(0);	
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
					//printf("Symbol %s already defined\n", aux->id);
					//exit(0);			
				}
			}			

			if (tipo == variavel){
				if (strcasecmp(idc,"integer") == 0){
					//funcao->locals = insert_el(aux->id, integer, 0, funcao->locals);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = integer;
					//novo->valor.integer = 0;
					//valores = insert_exp(novo,valores);
				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					// funcao->locals = insert_el(aux->id, real, 0, funcao->locals);
					// lista_global = insert_el(aux->id, real, 0, lista_global);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = real;
					//novo->valor.real = 0;
					//valores = insert_exp(novo,valores);
					
				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					// funcao->locals =  insert_el(aux->id, boolean, 0, funcao->locals);
					// 		element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = boolean;
					//novo->valor.boolean = 0;
					//valores = insert_exp(novo,valores);
				}
				else {	
					//printf("Unknown type %s\n", idc);
					//exit(0);	
				}
			}
			else if (tipo == array){
				if (strcasecmp(idc,"integer") == 0){	
					//funcao->locals =  insert_el(aux->id, array_integer, 0, funcao->locals);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = array_integer;
					//novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);

				}
				else if (strcasecmp(idc,"real") == 0)	
				{	
					//funcao->locals =  insert_el(aux->id, array_real, 0, funcao->locals);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = array_real;
					//novo->valor.arrayReal = calloc((indiceFim-indiceInicio), sizeof(double));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);

				}
				else if (strcasecmp(idc,"boolean") == 0)	
				{	
					//funcao->locals = insert_el(aux->id, array_boolean, 0, funcao->locals);
					//element* novo = (element*) malloc(sizeof(element));
					//novo->name = aux->id;
					//novo->type = array_boolean;
					//novo->valor.arrayInt = calloc((indiceFim-indiceInicio), sizeof(int));
					//novo->inicio = indiceInicio;
					//novo->fim = indiceFim;
					//valores = insert_exp(novo,valores);

				}
				else {	
					//printf("Unknown type %s\n", idc);
					//exit(0);	
				}	
			}	
		}
	}
}


void translate_rep_var_dec(is_rep_var_dec* repd,char* nome_funcao, int tipo){
	is_rep_var_dec* aux;

	for(aux=repd; aux!=NULL; aux=aux->next)
	{
			translate_variable_declaration(aux->vd,nome_funcao,tipo);
	}
}



void translate_statement_list(is_statement_list* stlist, char* nome_funcao){


	if (stlist == NULL){
	}
	else if(stlist->disc_tipo == flist){
		is_statement_list* aux;

		for(aux=stlist; aux!=NULL; aux=aux->next)
		{	
			translate_statement(aux->stt, nome_funcao);
		}
	}
	else{
		translate_statement(stlist->stt, nome_funcao);
	}
	
}


void translate_statement(is_statement* is, char* nome_funcao){

	element* exp1;
	element* exp2;

	environment_list * funcao;
	table_element* temp;
	int labelnum;

	if (is == NULL)
		return ;
	
	switch(is->disc_d)
    	{
		case d_begin_statementlist_end:
			translate_statement_list(is->dataStatement.aux1,nome_funcao);
			break;
		
		case d_id_assign_expression:
			/*ASSIGN*/
		
			if (nome_funcao == NULL){
				table_element* te= search_el(is->id,lista_global);		
				printf("\tg%d = ",te->offset);	
				exp1 = translate_expression((is_expression*)is->exp2,nome_funcao);
				printf("%s;\n",exp1->exp);
		
				free(exp1);

				//temp = search_el(is->id, lista_global);
			}
			
			/*else if(nome_funcao != NULL){	
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
			}		*/
			break;
		case d_id_exp_exp:

			exp1 = translate_expression((is_expression*)is->exp1,nome_funcao);
			exp2 = translate_expression((is_expression*)is->exp2,nome_funcao);
			table_element* te= search_el(is->id,lista_global);	
	
			printf("\tg%d[(%s)-(%d)] = %s;\n",te->offset, exp1->exp, te->inicio, exp2->exp);	
			
			free(exp1);
			free(exp2);


			/*ASSIGN de array*/
			/*exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);
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
			}*/
			/*
			verifica se estamos a aceder a um indice do array
			*/

/*			if (exp1->type != integer){
					printf("Incompatible type (got %s, required integer)\n", converte(exp1->type));
					exit(0);			
			}

			//verifica a expressao a atribuir
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
				if (exp2->type != real){
					printf("Incompatible type (got %s, required real)\n", converte(exp2->type));
					exit(0);
				}
				else{
					if (interpretador){
					element* var = procura_variavel(is->id,valores);
					var->valor.arrayReal[exp1->valor.integer - var->inicio] = exp2->valor.real;
					}
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
			*/
			break;
			
		case d_exp_stt:
			

			exp1 = translate_expression((is_expression*)is->exp1,nome_funcao);
			
			labelnum = labeln;
			labeln++;

			printf("\tif (%s) goto if%d ;\n",exp1->exp, labelnum);
			printf("\tgoto endif%d ;\n", labelnum);
			
			printf("if%d:\n",labelnum);
			translate_statement(is->stt,nome_funcao);
		
			printf("endif%d:\n",labelnum); 

			free(exp1);

			break;
			
		case d_exp_stt_stt:

			exp1 = translate_expression((is_expression*)is->exp1,nome_funcao);

			labelnum = labeln;
			labeln++;

			printf("\tif (%s) goto if%d ;\n",exp1->exp, labelnum);
			
			translate_statement(is->stt2,nome_funcao);
			
			printf("\tgoto endif%d ;\n", labelnum);
			
		
			printf("\tif%d:\n",labelnum);

			translate_statement(is->stt,nome_funcao);

			printf("\tendif%d:\n",labelnum); 
			



			/*exp1 = verifica_expression((is_expression*)is->exp1,nome_funcao);

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
			}*/
			free(exp1);

			break;

		
		case d_wexp_stt:

			exp1 = translate_expression((is_expression*)is->exp1, nome_funcao);

			labelnum = labeln;
			labeln++;
			
			printf("\nstartwhile%d:\n",labelnum); 
						
			printf("\tif (!(%s)) goto endwhile%d ;\n",exp1->exp, labelnum);
			// printf("\tgoto endwhile%d;\n", labelnum);
			
			//printf("while%d:\n",labelnum);
			translate_statement(is->stt,nome_funcao);
			printf("\tgoto startwhile%d ;\n", labelnum);
		
			printf("\tendwhile%d:\n",labelnum); 
						free(exp1);

			break;
		
		case d_id_exp_exp_stt:

		/*	exp1 = translate_expression((is_expression*)is->exp1,nome_funcao);
			exp2 = translate_expression((is_expression*)is->exp2,nome_funcao);
			
			table_element* te = search_el( is->id,lista_global);
			printf("\tg%d = ",te->offset);	
			

			for (;var->valor.integer <= exp2->valor.integer; var->valor.integer++){
				if (is->stt!= NULL)verifica_statement(is->stt,nome_funcao);
			}
		
			else{  
				if (is->stt!= NULL)verifica_statement(is->stt,nome_funcao);
			}
			break;*/

			exp1 = translate_expression((is_expression*)is->exp1, nome_funcao);
			exp2 = translate_expression((is_expression*)is->exp2,nome_funcao);

			labelnum = labeln;
			labeln++;

			table_element* f1 = search_el( is->id,lista_global);
			printf("\tg%d = %s;\n",f1->offset,exp1->exp);	

			printf("\n\tstartfor%d:\n",labelnum);

			printf("\tif ( g%d > %s) goto endfor%d ;\n",f1->offset, exp2->exp, labelnum);
			
			translate_statement(is->stt, nome_funcao);			
			printf("\tg%d++;\n",f1->offset);

			printf("\tgoto startfor%d;\n",labelnum); 

			printf("\tendfor%d:\n",labelnum); 

			free(exp1);
			free(exp2);

			break;

		
		case d_id_exp_dexp_stt:

			exp1 = translate_expression((is_expression*)is->exp1, nome_funcao);
			exp2 = translate_expression((is_expression*)is->exp2, nome_funcao);

			labelnum = labeln;
			labeln++;

			table_element* f2 = search_el( is->id,lista_global);
			printf("\tg%d = %s;\n",f2->offset,exp1->exp);	

			printf("\n\tstartfor%d:\n",labelnum);

			printf("\tif ( g%d < %s) goto endfor%d ;\n",f2->offset, exp2->exp, labelnum);
			
			translate_statement(is->stt, nome_funcao);
			printf("\tg%d--;\n",f2->offset);			
			printf("\tgoto startfor%d;\n",labelnum); 

			printf("\tendfor%d:\n",labelnum); 

			free(exp1);
			free(exp2);

			break;

		case d_tokens_exp:

			exp1 = translate_expression((is_expression*)is->exp1,nome_funcao);
			
			//verificar se o id existe na tabela de simbolos como sendo um inteiro
			if (nome_funcao == NULL){			
				temp = search_el(is->id, lista_global);
			}
			
			else if(nome_funcao != NULL){			
				funcao = procura_funcao(nome_funcao);
				temp = search_el(is->id, funcao->locals);
			
				if (temp== NULL){
					temp = search_el (is->id, funcao->params);
					if (temp == NULL){
						temp = search_el (is->id, lista_global);
			
					}
				}
			}
		
			if (temp->type == integer){
					table_element* par1 = search_el(is->id,lista_global);
					printf("\tsscanf(argv[%s],\"%%d\",&g%d);\n",exp1->exp, par1->offset );
			}
			else if(temp->type == real){
					table_element* par2 = search_el(is->id,lista_global);
					printf("\tsscanf(argv[%s],\"%%f\",&g%d);\n",exp1->exp, par2->offset );
			}
			free(exp1);

			break;

		case d_writeln_exp:
	
				exp1 = translate_expression((is_expression*)is->exp1,nome_funcao);		

				if (exp1->type == integer){
						printf("\tprintf(\"%%d\\n\",%s);\n", exp1->exp);
				}
				else if (exp1->type == real){
						printf("\tprintf(\"%%f\\n\",%s);\n", exp1->exp);
				}
				else if(exp1->type == boolean){
						printf("\tprintf(\"%%s\\n\", %s==1 ? (\"true\") : (\"false\"));\n", exp1->exp);
				}
				
			
			//printf(  "\tprintf( \"%d\" ,");
		//	printf("%d\n", is->exp1);
			//translate_expression((is_expression*)is->exp1,nome_funcao);				
			//printf(");\n");
			/*if (exp1->type == integer){
				printf(  "\tprintf(\"%d\",");
				translate_expression((is_expression*)is->exp1,nome_funcao);				
				printf(");\n");
			}
			else if (exp1->type == real){
					printf(  "\tprintf(\"%lf\",);\n");
			}
			else if(exp1->type == boolean){

					if(exp1->valor.boolean == 1)
						printf("true\n");
					else
						printf("false\n");
				
			
			}*/
			
			//if (temp->type == integer) printf("%d\n",temp->valor.digseq);
			//else if (temp->type == real) printf("%f\n",temp->valor.real);
			//else if (temp->type == boolean && temp->valor.boolean == 1) printf("true\n");
			//else if (temp->type == boolean && temp->valor.boolean == 0) printf("false\n");	
			free(exp1);
		
			break;

		default:
			break;
   	}
}

element* translate_expression(is_expression* ie, char* nome_funcao){

	
	element* aux = (element*) malloc (sizeof(element));
	//element* temp2;
	element* exp1;
	element* exp2;
	//table_element* apontador_parametros;
	//environment_list* func;
	
	
	environment_list * funcao;
	table_element* temp;
	int encontrado = 0;

	switch(ie->disc_d)
    	{
    	case d_digseq:
			aux->type = integer;
			//aux->valor.integer = ie->dataExpression.digseq;
			sprintf(aux->exp,"%d",ie->dataExpression.digseq);
			//strcat(express,tmp);					
			return aux;

		case d_realnumber:
			
			aux->type = real;
			sprintf(aux->exp,"%f",ie->dataExpression.realnumber);
			//strcat(express,tmp);			
			return aux;

		case d_OP1and:
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				//aux->valor.integer = (exp1->valor.integer && exp2->valor.integer);
				sprintf(aux->exp,"(%s && %s)",exp1->exp, exp2->exp);
				//strcat(express,tmp);				
				return aux;
			}
			
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"(%s && %s)",exp1->exp, exp2->exp);
				//strcat(express,tmp);
				return aux;
			}
			
			free(exp1);
			free(exp2);

			break;

		case d_OP1or:
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				sprintf(aux->exp,"(%s || %s)",exp1->exp, exp2->exp);
				return aux;
			}
			
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"(%s || %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
		
		case d_OP21:
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s < %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s < %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s < %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s < %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"(%s < %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;

		
		case d_OP22:
			
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s > %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s > %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s > %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s > %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"(%s > %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
	
		case d_OP23:
			
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s == %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s == %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s == %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s == %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"(%s == %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
		
		case d_OP24:
				
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s != %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s != %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s != %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type  == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s != %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"(%s != %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
		
		case d_OP25:
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s <= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s <= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s <= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s <= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"(%s <= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
				
		case d_OP26:
	
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s >= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s >= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = boolean;
				sprintf(aux->exp,"(%s >= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = boolean;
				sprintf(aux->exp,"(%s >= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == boolean && exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"(%s >= %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
				
		case d_OP3plus:	
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				sprintf(aux->exp,"(%s + %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s + %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = real;
				sprintf(aux->exp,"(%s + %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s + %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
		
		case d_OP3minus:
			
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				sprintf(aux->exp,"(%s - %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s - %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = real;
				sprintf(aux->exp,"(%s - %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s - %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
		
		case d_OP4Mult:
		
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				sprintf(aux->exp,"(%s * %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s * %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = real;
				sprintf(aux->exp,"(%s * %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s * %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;

		case d_OP4RealDiv:
	
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = real;
				sprintf(aux->exp,"(%s / (double) %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == integer && exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s / %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == integer){
				aux->type = real;
				sprintf(aux->exp,"(%s / %s)",exp1->exp, exp2->exp);
				return aux;
			}
			else if  (exp1->type == real && exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s / %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			break;
		
		case d_OP4Div:

			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				sprintf(aux->exp,"(%s / %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);


			break;
		
		case d_OP4Mod:
	
			exp1 = translate_expression(ie->exp1,nome_funcao);
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp1->type == integer && exp2->type == integer){
				aux->type = integer;
				sprintf(aux->exp,"(%s %% %s)",exp1->exp, exp2->exp);
				return aux;
			}
			free(exp1);
			free(exp2);

			
			break;
				
		case d_OP3Plus_exp:
	
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp2->type == integer){
				aux->type = integer;
				sprintf(aux->exp,"(%s)",exp2->exp);
				return aux;
			}
			else if  (exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"(%s)",exp2->exp);
				return aux;
			}

			free(exp2);

			break;

		case d_OP3Minus_exp:
	
			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if (exp2->type == integer){
				aux->type = integer;
				sprintf(aux->exp,"-(%s)",exp2->exp);
				return aux;
			}
			else if  (exp2->type == real){
				aux->type = real;
				sprintf(aux->exp,"-(%s)",exp2->exp);
				return aux;
			}

			free(exp2);

			break;

		case d_NOT_exp:

			exp2 = translate_expression(ie->exp2,nome_funcao);
			
			if  (exp2->type == boolean){
				aux->type = boolean;
				sprintf(aux->exp,"!(%s)",exp2->exp);
				return aux;
			}
			free(exp2);

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

					encontrado = 1;
			}


			if (encontrado == 1){
				if( temp-> type == array_integer || temp-> type == array_boolean || temp-> type == array_real)
				{
					exp2 = translate_expression(ie->exp2,nome_funcao);

					if (exp2->type == integer){
						if (temp-> type == array_integer){

							table_element* var = search_el(ie->id,lista_global);
							aux->type = integer;
							sprintf(aux->exp,"g%d[(%s)-%d]",temp->offset, exp2->exp, var->inicio);	
							return aux;
						}
						else if (temp-> type == array_boolean){
							table_element* var = search_el(ie->id,lista_global);
							aux->type = boolean;
							sprintf(aux->exp,"g%d[(%s)-%d]",temp->offset, exp2->exp, var->inicio);	
							return aux;
						}
						else if (temp-> type == array_real){
							table_element* var = search_el(ie->id,lista_global);
							aux->type = real;
							sprintf(aux->exp,"g%d[(%s)-%d]",temp->offset, exp2->exp, var->inicio);	
							return aux;
						}
					}

				}

			}

			free(exp2);

			break;

		case  d_id_lparen_rep_expression_rparen:
			
			temp = search_el(ie->id, lista_global);

			table_element* apontador_parametros;
			environment_list* func;
			element* temp2;

			if (temp->type == function){
				
				func = procura_funcao(ie->id);
				temp2 = verifica_rep_expression(ie->id, ie->dataExpression.aux, nome_funcao);	
				apontador_parametros = func->params;
				int narg = 0;
				for (; temp2!= NULL;temp2= temp2->next){
					narg++;
					if(temp2->type == apontador_parametros->type || (temp2->type == integer && apontador_parametros->type == real)  ){
					
					}
					apontador_parametros = apontador_parametros->next;
				}
				
				aux->type = func->tipo_retorno;
				//aux->valor.integer = 0;
				return aux;
				
			}

			break;
		
		
		case d_id:

			if (nome_funcao == NULL){	
				temp = search_el(ie->id, lista_global);
			}
			
			else if(nome_funcao != NULL){			
				//funcao = procura_funcao(nome_funcao);
				//temp = search_el(ie->id, funcao->locals);
			
				//if (temp== NULL){
			//		temp = search_el (ie->id, funcao->params);
			//		if (temp == NULL){
			//			temp = search_el (ie->id, lista_global);
			//			if (temp == NULL){
			//				printf("Symbol %s not defined\n", ie->id);
			//				exit(0);
			//			}
			//		}
			//	}
			}

			if (temp->type == boolean){
				table_element* var = search_el(ie->id,lista_global);
				aux->type = var->type;				
				sprintf(aux->exp,"g%d",var->offset);
				return aux;
			}

			else if (temp->type == reserved_id && strcasecmp(ie->id,"true")==0){
				aux->type = boolean;
				aux->valor.boolean = 1;
				sprintf(aux->exp,"1");
				return aux;
			}
			else if (temp->type == reserved_id && strcasecmp(ie->id,"false")==0){
				aux->type = boolean;
				aux->valor.boolean = 0;
				sprintf(aux->exp,"0");
				return aux;
			}

			else if (temp->type == integer){
				if (strcasecmp(ie->id,"paramcount")==0){
					aux->type = integer;
					sprintf(aux->exp,"argc");
					return aux;
				}
				else{
					table_element* var = search_el(ie->id,lista_global);
					aux->type = var->type;
					sprintf(aux->exp,"g%d",var->offset);
					return aux;
				}
			}
			
			else if (temp->type == real){
				table_element* var = search_el(ie->id,lista_global);
				aux->type = var->type;
				sprintf(aux->exp,"g%d",var->offset);
				return aux;
			}

			//TODO: verificar se isto está certo
			else if (temp->type == array_integer){
				table_element* var = search_el(ie->id,lista_global);
				aux->type = var->type;
				sprintf(aux->exp,"g%d",var->offset);
				return aux;
			}
			
			else if (temp->type == array_real){
				table_element* var = search_el(ie->id,lista_global);
				aux->type = var->type;
				sprintf(aux->exp,"g%d",var->offset);
				return aux;
			}
			else if (temp->type == array_boolean){
				table_element* var = search_el(ie->id,lista_global);
				aux->type = var->type;
				sprintf(aux->exp,"g%d",var->offset);
				return aux;
			}
		
			break;
			
		default:
			break;
   	}
	return NULL;
}
