
/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/

#include "funcoes.h"
#include "estruturas.h"
#include<stdlib.h>
#include<stdio.h>
#include <string.h>
#include <ctype.h>


/*
	Igor Nelson Garrido da Cruz 2009111924
	Gonçalo Silva Pereira 2009111643
	Compiladores Meta 2
*/


void upper(char * palavra){
	int i = 0;
	for(;i<strlen(palavra);i++){
		palavra[i] = toupper(palavra[i]);
	}
}

is_expression* insert_DIGSEQ(int digseq){
    	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	ie-> disc_d = d_digseq;				//Coloca etiqueta a informar que é um numero
	ie->dataExpression.digseq=digseq;	//Guarda o conteúdo
	return ie;		
    
}

is_expression* insert_REALNUMBER(double realnumber){
    
       	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	ie-> disc_d = d_realnumber;				//Coloca etiqueta a informar que é um numero
	ie->dataExpression.realnumber=realnumber;	//Guarda o conteúdo
		
	return ie;		
    
}

is_expression* insert_OP1(is_expression* exp1, char *symbol, is_expression* exp2){

	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	
	upper(symbol);
	
	if (strcmp(symbol,"AND")==0)
		ie->disc_d = d_OP1and;
	else
		ie->disc_d = d_OP1or;
		
	ie->exp1=exp1;
	ie->exp2=exp2;
	ie->symbol = symbol;
	
	return ie;	
}
is_expression* insert_OP2(is_expression* exp1, char *symbol, is_expression* exp2){
	
	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	upper(symbol);
	if (strcmp(symbol,"<")==0)
		ie->disc_d = d_OP21;
	else if (strcmp(symbol,">")==0)
		ie->disc_d = d_OP22;
	else if (strcmp(symbol,"=")==0)
		ie->disc_d = d_OP23;
	else if (strcmp(symbol,"<>")==0)
		ie->disc_d = d_OP24;
	else if (strcmp(symbol,"<=")==0)
		ie->disc_d = d_OP25;
	else if (strcmp(symbol,">=")==0)
		ie->disc_d = d_OP26;
	
	ie->exp1=exp1;
	ie->exp2=exp2;
	
	ie->symbol = symbol;
	
	return ie;
}
is_expression* insert_OP3(is_expression* exp1, char *symbol, is_expression* exp2){

     	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	
	upper(symbol);
	if (strcmp(symbol,"+")==0)
		ie->disc_d = d_OP3plus;
	else if (strcmp(symbol,"-")==0)
		ie->disc_d = d_OP3minus;
		
	
	ie->exp1=exp1;
	ie->exp2=exp2;
	
	ie->symbol = symbol;
	
	return ie;
}
is_expression* insert_OP4(is_expression* exp1, char *symbol, is_expression* exp2){
      is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	
	upper(symbol);
	if (strcmp(symbol,"*")==0)
		ie->disc_d = d_OP4Mult;
	else if (strcmp(symbol,"/")==0)
		ie->disc_d = d_OP4RealDiv;
	else if (strcmp(symbol,"DIV")==0)
		ie->disc_d = d_OP4Div;
	else if (strcmp(symbol,"MOD")==0)
		ie->disc_d = d_OP4Mod;
	 
	ie->exp1=exp1;
	ie->exp2=exp2;
	
	ie->symbol = symbol;
	
	return ie;
}


is_expression* insert_OP3_expression(char *symbol, is_expression* exp){
	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	
	upper(symbol);
	if (strcmp(symbol,"+")==0)
		ie->disc_d = d_OP3Plus_exp;
	else if (strcmp(symbol,"-")==0)
		ie->disc_d = d_OP3Minus_exp;
		
	ie->exp2=exp;
	ie->symbol = symbol;
	
	return ie;
}


is_expression* insert_NOT_expression(is_expression* exp){
	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	ie->disc_d = d_NOT_exp;
	
	ie->exp2=exp;
	
	return ie;
}

is_expression* insert_lparen_expression_rparen(is_expression* exp){
	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	ie->disc_d = d_lparen_expression_rparen;
	
	ie->exp2 = exp;
	
	return ie;	
}

is_expression* insert_id_lbrac_expression_rbrac(char* id,is_expression* exp){
	is_expression* ie=(is_expression*)malloc(sizeof(is_expression));
	ie->disc_d = d_id_lbrac_expression_rbrac;
	ie->id = id;
	ie->exp2 = exp;
	
	return ie;

}


is_expression* insert_id_lparen_rep_expression_rparen(char* id, is_rep_expression* exp){



	is_expression* ic=(is_expression*)malloc(sizeof(is_expression));
	ic->disc_d = d_id_lparen_rep_expression_rparen;
	ic->id = id;
	ic->dataExpression.aux = exp;
	
	return ic;	
	
}

is_expression* insert_id(char* id){



	is_expression* ic=(is_expression*)malloc(sizeof(is_expression));
	ic->disc_d = d_id;
	ic->id = id;
	return ic;	
	
}


is_rep_expression* insert_rep_expression(is_rep_expression* list,is_expression* exp){

	
	is_rep_expression* re=(is_rep_expression*)malloc(sizeof(is_rep_expression));
	re->exp = exp;
	if (list==NULL)
		return re;

	is_rep_expression* aux;

	for(aux=list; aux->next!=NULL; aux=aux->next);	//procura pelo final da lista
		aux->next=re;					//adiciona no final da lista
	
	return list;

}

is_statement* insert_begin_statementlist_end(is_statement_list* exp){
	
	if (exp != NULL){
		is_statement* ic=(is_statement*)malloc(sizeof(is_statement));
		ic->disc_d = d_begin_statementlist_end;
		ic->dataStatement.aux1 = exp;
	
		return ic;		
	}
	else
	return NULL;
}

is_statement* insert_id_assign_expression(char* id, is_expression* exp){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_id_assign_expression;
	
	st->id = id;
	
	st->exp2 = exp;
	
	return st;

}


is_statement_list* insert_statement_list(is_statement_list* list, is_statement* exp){

	is_statement_list* re=(is_statement_list*)malloc(sizeof(is_statement_list));
	re->stt = exp;
	re->next = NULL;

	if (list==NULL && exp != NULL){
		re->disc_tipo = node;
		return re;
	}
	if (list == NULL && exp == NULL )
		return NULL;
	if (list != NULL &&  exp == NULL)
		return list;

	is_statement_list* aux;

	for(aux=list; aux->next!=NULL; aux=aux->next);		//procura pelo final da lista
		aux->next=re;					//adiciona no final da lista
	
	list->disc_tipo = flist;

	return list;
}

is_statement* insert_id_exp_exp(char* id,is_expression* exp1,is_expression* exp2){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_id_exp_exp;
	
	st->id = id;
	st-> exp1 = exp1;
	st->exp2 = exp2;
	
	return st;
}


is_statement* insert_exp_stt(is_expression* exp1,is_statement* stt2){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_exp_stt;
	st->exp1 = exp1;
	st->stt = stt2;
	
	return st;
}

is_statement* insert_exp_stt_stt(is_expression* exp1,is_statement* stt,is_statement* stt2){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_exp_stt_stt;
	st->exp1 = exp1;
	
	st->stt = stt;
	st->stt2 = stt2;
	
	return st;
}

is_statement* insert_wexp_stt(is_expression* exp1,is_statement* stt2){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_wexp_stt;
	st->exp1 = exp1;
	st->stt = stt2;
	
	return st;
}

is_statement* insert_id_exp_exp_stt(char* id,is_expression* exp1,is_expression* exp2,is_statement* stt){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_id_exp_exp_stt;
	st->id = id;
	st->exp1 = exp1;
	st->exp2 = exp2;
	
	st->stt = stt;
	
	return st;
}

is_statement* insert_id_exp_dexp_stt(char* id,is_expression* exp1,is_expression* exp2,is_statement* stt){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_id_exp_dexp_stt;
	st->id = id;
	st->exp1 = exp1;
	st->exp2 = exp2;
	
	st->stt = stt;
	
	return st;
}

is_statement* insert_tokens_exp_id(is_expression* exp1, char* id){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_tokens_exp;
	st->id = id;
	st->exp1 = exp1;
	
	return st;
}

is_statement* insert_writeln_exp(is_expression* exp){
	is_statement* st=(is_statement*)malloc(sizeof(is_statement));
	
	st->disc_d = d_writeln_exp;
	st->exp1 = exp;
	
	return st;
}

is_function_block* insert_bstatement_list(is_statement_list* sttl){
	if (sttl != NULL){
		is_function_block* st=(is_function_block*)malloc(sizeof(is_function_block));
		st->disc_d = d_bstatement_list;
		st->sttl = sttl;
	
		return st;
	}
	else
		return NULL;
}

is_function_block* insert_var_bstatement_list(is_var_dec_list* varl ,is_statement_list* sttl){

	is_function_block* st=(is_function_block*)malloc(sizeof(is_function_block));
	st->disc_d = d_var_bstatement_list;
	st->varl = varl;
	st->sttl = sttl;
	return st;

}

is_var_dec_list* insert_var_dec(is_var_dec* vardec){
	is_var_dec_list* vdecl = (is_var_dec_list*) malloc(sizeof(is_var_dec_list));
	
	vdecl->disc_d = d_tipo1;
	vdecl->dec = vardec;

	return vdecl;
}

is_var_dec_list* insert_var_dec_rep(is_var_dec* vardec, is_rep_var_dec* list){
	is_var_dec_list* vdecl = (is_var_dec_list*) malloc(sizeof(is_var_dec_list));
	
	vdecl->disc_d = d_tipo2;
	vdecl->dec = vardec;
	vdecl->repdec = list;
	
	return vdecl;
}

is_var_dec* insert_idl_id(is_id_list* list , char* id){

	is_var_dec* vd = (is_var_dec*) malloc(sizeof(is_var_dec));
	
	
	vd->disc_d = d_tipox1;

	vd->id = id;
	vd->idl = list;
	

	return vd;
}

is_var_dec* insert_idl_id_2(is_id_list* list, int dig1, int dig2, char*id){
	is_var_dec* vd = (is_var_dec*) malloc(sizeof(is_var_dec));
	
	
	vd->disc_d = d_tipox2;
	
	vd->idl = list;
	vd->id = id;
	vd->dig1 = dig1;
	
	vd->dig2 = dig2;
	return vd;
}


is_id_list* insert_id_list(is_id_list* list, char* id){

	is_id_list* re = (is_id_list*) malloc(sizeof(is_id_list));
	
	re->id = id;


	if (list==NULL)
		return re;

	is_id_list* aux;

	for(aux=list; aux->next!=NULL; aux=aux->next);	//procura pelo final da lista
		aux->next=re;					//adiciona no final da lista
	
	return list;
	
	
}

is_rep_var_dec* insert_rep_var_dec(is_rep_var_dec* list, is_var_dec* vardec){
	is_rep_var_dec* re = (is_rep_var_dec*) malloc(sizeof(is_rep_var_dec));
	
	re->vd = vardec;

	if (list==NULL)
		return re;

	is_rep_var_dec* aux;

	for(aux=list; aux->next!=NULL; aux=aux->next);	//procura pelo final da lista
		aux->next=re;					//adiciona no final da lista
	
	return list;
	
}


is_function_definition* insert_function_definition(is_function_heading* fh, is_function_block* fb){
	is_function_definition* fd = (is_function_definition*)malloc(sizeof(is_function_definition));

	fd->fh = fh;
	fd->fb = fb;

	return fd;
}
is_function_heading* insert_function_heading(char* id1, char* id2){
	is_function_heading* fh = (is_function_heading*) malloc(sizeof(is_function_heading));

	fh->disc_d = t1;
	fh->id1 = id1;
	fh->id2 = id2;
	
	return fh;
}





is_function_heading* insert_function_heading2(char* id1,is_rep_formal_args* list, char * id2){
	is_function_heading* fh = (is_function_heading* )malloc(sizeof(is_function_heading));
	
	fh->disc_d = t2;
	fh->id1 = id1;
	fh->id2 = id2;
	fh->repf = list;
	
	return fh;

}

is_rep_formal_args* insert_rep_formal_args_list(is_rep_formal_args* list, is_formal_args* args){
	is_rep_formal_args* re= (is_rep_formal_args*) malloc(sizeof(is_rep_formal_args));
	
	re->fa =args;

	if (list==NULL)
		return re;

	is_rep_formal_args* aux;

	for(aux=list; aux->next!=NULL; aux=aux->next);	//procura pelo final da lista
		aux->next=re;					//adiciona no final da lista
	
	return list;
	
	
}




is_formal_args* insert_formal_args(is_id_list* list, char* id, int tipo){
	is_formal_args* fa = (is_formal_args*)malloc(sizeof(is_formal_args));
	if (tipo== 1){
		fa->disc_d = tipo1;
	}
	else if (tipo==2){
		fa->disc_d = tipo2;
	}
	else if (tipo==3){
		fa->disc_d = tipo3;
	}
	else if (tipo==4){
		fa->disc_d = tipo4;
	}
	
	fa->idl = list;
	fa->id = id;
	
	return fa;

}

is_function_declaration* insert_function_heading3(is_function_heading* fh){
	is_function_declaration* fd = (is_function_declaration*) malloc(sizeof(is_function_declaration));
	fd->fh = fh;
	return fd;

}


is_block* insert_block1(is_var_dec_list* vardecl, is_rep_dec_def* repdecdef, is_statement_list* sttl){
	is_block* bk = (is_block*)malloc(sizeof(is_block));
	bk->disc_d = bk1;
	
	bk->vardecl = vardecl;
	bk->repdecdef = repdecdef;
	bk->sttl = sttl;
	
	return bk; 
}
is_block* insert_block2(is_rep_dec_def* repdecdef, is_statement_list* sttl){

	is_block* bk = (is_block*)malloc(sizeof(is_block));
	bk->disc_d = bk2;
	bk->vardecl = NULL;
	bk->repdecdef = repdecdef;
	bk->sttl = sttl;
	
	return bk; 
}

is_rep_dec_def* insert_rep_dec(is_rep_dec_def* list, is_function_declaration* decl){
	is_rep_dec_def* re= (is_rep_dec_def*) malloc(sizeof(is_rep_dec_def));
	re->disc_d = dec;
	re->dec =decl;

	if (list==NULL){
		re->disc_tipo = node;
		return re;
	}
	is_rep_dec_def* aux;

	for(aux=list; aux->next!=NULL; aux=aux->next);	//procura pelo final da lista
		aux->next=re;					//adiciona no final da lista

	
	list->disc_tipo = flist;
	return list;
	
}

is_rep_dec_def* insert_rep_def(is_rep_dec_def*list ,is_function_definition* defi){
	is_rep_dec_def* re= (is_rep_dec_def*) malloc(sizeof(is_rep_dec_def));
	re->disc_d = def;
	re->def = defi;

	if (list==NULL){
		re->disc_tipo = node;	
		return re;
	}
	is_rep_dec_def* aux;

	for(aux=list; aux->next!=NULL; aux=aux->next);	//procura pelo final da lista
		aux->next=re;					//adiciona no final da lista

	list->disc_tipo = flist;
	return list;
}


