#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <netdb.h>
#include <string.h>

/*
	Trabalho realizado por:
	
	-> Igor Nelson Garrido da Cruz        - 2009111924
	
	-> Carlos Manuel Fernandes dos Santos - 2009108991

Informaçao auxiliar:

	O servidor serve-se do ficheiro BD (ficheiro binário) para armazenar as informaçoes dos utilizadores
e do ficheiro DBinfo.txt(ficheiro de texto) para armazenar o numero de utilizadores

*/


#define SERVER_PORT 8008
#define BUF_SIZE 1024
#define VISIVEL_AMIGOS 2
#define VISIVEL 1	// visibilidade de dados
#define INVISIVEL 0
#define LOGGED 1	// autenticaçao
#define UNLOGGED 0


typedef struct Utilizador{

    short privilegios;  // 0 - Utilizador | 1 - Administrador
	int vis;			// visibilidade dos dados

    char nome[20];		// login
    char password[20];

	char nome_completo[40];
    int idade;
    char morada[30];
    char curso[30];
    
    int num_amigos;
    int amigos[50];				// lista dos amigos
    int num_pedidos_amizade;
    int pedidos_amizade[50];	// lista de pedidos de amizade
    
    int msg_num;				
    char mensagens[30][50];		// lista de mensagens
    
    char gostos[30];

}User;

void process_client(int fd);
void erro(char *msg);
User *users;
void cria_admin();
void cria_user(int client_fd);
void apaga_user(int client_fd);
void envia_mensagem(int client_fd,int user);
void menu_admin(int client_fd,int i);
void menu_user(int client_fd,int i);
void imprime_perfil(int client_fd,int user,int dest);
void altera_perfil(int client_fd,int user);
void ler_mensagens(int client_fd,int user);
void salva_info();
void carrega_info();
void ler_mensagens(int client_fd,int user);
void adicionar_amigo(int client_fd,int user);
void eliminar_amigo(int client_fd,int user);
void ver_perfis(int client_fd,int user);
void aceitar_amigo(int client_fd,int i);
void lista_amigos(int client_fd,int user);

int main() {
	int fd, client;
	struct sockaddr_in addr, client_addr;

	users = malloc(50*sizeof(struct Utilizador));

	int client_addr_size;
	bzero((void *) &addr, sizeof(addr));
	addr.sin_family = AF_INET;
	addr.sin_addr.s_addr = htonl(INADDR_ANY);
	addr.sin_port = htons(SERVER_PORT);
	if ( (fd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
		erro("na funcao socket");
	if ( bind(fd,(struct sockaddr*)&addr,sizeof(addr)) < 0)
		erro("na funcao bind");
	if( listen(fd, 5) < 0)
		erro("na funcao listen");
	while (1) {
		client_addr_size = sizeof(client_addr);
	client = accept(fd,(struct sockaddr *)&client_addr,&client_addr_size);
	if (client > 0) {
		if (fork() == 0) {
			process_client(client);
			exit(0);
			}
		close(client);
		}
	}
	return 0;
}

void process_client(int client_fd)
{
	int i;
	int b;
	char buffer[BUF_SIZE];
	char buffer2[BUF_SIZE];
	char temp_username[20],temp_pw[20];
	short estado = UNLOGGED;
	int num_users;
	FILE *DBinfo;
		
	DBinfo = fopen("DBinfo.txt","r");
	fscanf(DBinfo,"%d",&num_users);
	fclose(DBinfo);

	carrega_info();

	b = sprintf(buffer,"Username : ");
	write(client_fd,buffer,b);

	read(client_fd,buffer2,BUF_SIZE);
	sscanf(buffer2,"%s",temp_username);

	b = sprintf(buffer,"Password : ");
	write(client_fd,buffer,b);

	read(client_fd,buffer2,BUF_SIZE);
	sscanf(buffer2,"%s",temp_pw);

	for(i=0;i<num_users;i++){		
		if (strcmp(users[i].nome,temp_username)==0 && strcmp(users[i].password,temp_pw)==0){
			estado = LOGGED;
			b = sprintf(buffer,"\nLogin efectuado com sucesso!\nAguarde...\n");
			write(client_fd,buffer,b);
			sleep(1);
			break;
		}
	}

	if (estado == LOGGED){
		if (users[i].privilegios==1)
			menu_admin(client_fd,i);
		else
			menu_user(client_fd,i);
	}
	else{
		b = sprintf(buffer,"Login errado!\nA coneccao vai fechar dentro de 1 segundo...\n");
		write(client_fd,buffer,b);
		sleep(1);
		}
	close(client_fd);
}

void erro(char *msg)
{
	printf("Erro: %s\n", msg);
	exit(-1);
}

void menu_admin(int client_fd,int i)
{
/* 	-recebe um file descriptor do socket e o índice do utilizador na lista;
	-mostra o menu de administrador;
*/

	int opcao;
	int b;
	char buffer[BUF_SIZE];
	char buffer2[BUF_SIZE];
	int num_users;
	FILE *DBinfo;
	
	carrega_info();
	
	DBinfo = fopen("DBinfo.txt","r");
	fscanf(DBinfo,"%d",&num_users);
	fclose(DBinfo);

	b = sprintf(buffer,"Lista de Users:\n");
	write(client_fd,buffer,b);
	
	for (;i<num_users;i++){	
		b = sprintf(buffer,"%s\n",users[i].nome);
        write(client_fd,buffer,b);
	}
    
    b = sprintf(buffer,"\n1 - Enviar mensagem\n2 - Criar utilizador\n3 - Apagar utilizador\n4 - Ler mensagens\n0 - Sair\n\n");
    write(client_fd,buffer,b);
	
	read(client_fd,buffer2,BUF_SIZE);
	opcao = atoi(buffer2);

	while(opcao != 0){
		if (opcao == 1)
			envia_mensagem(client_fd,i); // i indice do utilizador de origem
        else if (opcao == 2)
            cria_user(client_fd);
        else if (opcao == 3)
            apaga_user(client_fd);
        else if (opcao == 4)
            ler_mensagens(client_fd,i);
        else if (opcao == 0){
            b = sprintf(buffer,"A terminar Sessao...\n\n");
            write(client_fd,buffer,b);
        }
        else {
            b = sprintf(buffer,"Opcao Errada!\n\n");
            write(client_fd,buffer,b);
        }
          b = sprintf(buffer,"\n1 - Enviar mensagem\n2 - Criar utilizador\n3 - Apagar utilizador\n4 - Ler mensagens\n0 - Sair\n\n");
        write(client_fd,buffer,b);
        read(client_fd,buffer2,BUF_SIZE);
        opcao = atoi(buffer2);
	}

	salva_info();

}
void menu_user(int client_fd,int i)
{
/* 	-recebe um file descriptor do socket e o índice do utilizador na lista;
	-mostra o menu de utilizador;
*/
	int opcao;
	int b;
	char buffer[BUF_SIZE];
	char buffer2[BUF_SIZE];
	
	carrega_info();
	
    b = sprintf(buffer,"\n\n1 - Consultar Perfil\n2 - Alterar Perfil\n3 - Adicionar Amigo\n4 - Eliminar Amigo\n5 - Ver Perfis\n6 - Ler Mensagens\n7 - Escrever Mensagem\n8 - Aceitar Amigo\n9 - Lista Amigos\n0 - Sair\n\n");
    write(client_fd,buffer,b);
	read(client_fd,buffer2,BUF_SIZE);
	opcao = atoi(buffer2);

	while(opcao != 0){
		if (opcao == 1)
			imprime_perfil(client_fd,i,i);
        else if (opcao == 2)
            altera_perfil(client_fd,i);
        else if (opcao == 3)
            adicionar_amigo(client_fd,i);
        else if (opcao == 4)
			eliminar_amigo(client_fd,i);
		else if (opcao == 5)
			ver_perfis(client_fd,i);
		else if (opcao == 6)
			ler_mensagens(client_fd,i);
		else if (opcao == 7)
			envia_mensagem(client_fd,i);
		else if (opcao == 8)
			aceitar_amigo(client_fd,i);
		else if (opcao == 9)
			lista_amigos(client_fd,i);
		else if (opcao == 0){
            b = sprintf(buffer,"A terminar Sessao...\n\n");
        	write(client_fd,buffer,b);
       	}
        else{
            b = sprintf(buffer,"Opcao Errada!\n\n");
            write(client_fd,buffer,b);
        }
        
    	b = sprintf(buffer,"\n\n1 - Consultar Perfil\n2 - Alterar Perfil\n3 - Adicionar Amigo\n4 - Eliminar Amigo\n5 - Ver Perfis\n6 - Ler Mensagens\n7 - Escrever Mensagem\n8 - Aceitar Amigo\n9 - Lista Amigos\n0 - Sair\n\n");
        write(client_fd,buffer,b);
        
        read(client_fd,buffer2,BUF_SIZE);
		opcao = atoi(buffer2);
	}
	salva_info();
}
void imprime_perfil(int client_fd,int user,int dest){
/*

	client_fd - file descriptor do destinatario
	user - indice do utilizador actual logado no sistema
	dest - indice do utilizador a imprimir o perfil

	ambos os users teem de existir em sistema

*/
	int b,i,sao_amigos;
	char buffer[BUF_SIZE];
	
	
	for (i=0;i<users[user].num_amigos;i++){
		if (users[user].amigos[i]==dest)
			sao_amigos =1;
	}

	b = sprintf(buffer,"\n\n\n\n\n\nPerfil: %s\n\n",users[dest].nome);
	write(client_fd,buffer,b);
	
	if (user == dest || users[dest].vis == VISIVEL || (sao_amigos && users[dest].vis == VISIVEL_AMIGOS)  ){// se estivermos a imprimir o perfil do utilizador 
		b = sprintf(buffer,"\nNome : %s\nIdade : %d\nMorada : %s\nCurso : %s\nGostos : %s\nAmigos : %d\nMensagens : %d\nPedidos Amizade : %d\n\n",users[dest].nome,users[dest].idade,users[dest].morada,users[dest].curso,users[dest].gostos,users[dest].num_amigos,users[dest].msg_num,users[dest].num_pedidos_amizade);
		write(client_fd,buffer,b);
	}
	
}
void envia_mensagem(int client_fd,int user){
    int k,b;
    char nome[20];
    char buff[BUF_SIZE];
	short estado=0;
	int num_users;
	FILE *DBinfo;
	
	carrega_info();
	
	DBinfo = fopen("DBinfo.txt","r");
	fscanf(DBinfo,"%d",&num_users);
	fclose(DBinfo);

	b = sprintf(buff,"\nUsername de Destino:\n");
    write(client_fd,buff,b);
    
    b=read(client_fd,buff,BUF_SIZE);
    sscanf(buff,"%s",nome);

    for (k=0;k<num_users;k++){
        if (strcmp(users[k].nome,nome)==0){
			if (users[k].msg_num == 30){
				b = sprintf(buff,"Caixa de mensagens do destino cheia\n");
				write(client_fd,buff,b);
				break;
			}
			
			else{	
				b = sprintf(buff,"\nMensagem:\n");		// pede a mensagem
    			write(client_fd,buff,b);
    			
    			b=read(client_fd,buff,BUF_SIZE);
    			buff[b]='\0';
				strcat(buff," -");
				strcat(buff,users[user].nome);
				strcpy(users[k].mensagens[ users[k].msg_num ],buff);
				users[k].msg_num++;
				estado=1;								// estado = 1 indica que a mensagem foi entregue
				
				b = sprintf(buff,"\nMensagem enviada com sucesso!\n");
			    write(client_fd,buff,b);
				
				break;
			}
		}
 	}

	if (estado!=1){		//se a mensagem nao foi entregue
		b = sprintf(buff,"Utilizador inexistente\n");
		write(client_fd,buff,b);
	}
	
	salva_info();

}
void cria_user(int client_fd){

		char nome[20];
		char password[20];
		char buff[BUF_SIZE];
		int num_users;
		int b,i,existe =0;
		FILE *DBinfo;
		
		DBinfo = fopen("DBinfo.txt","r");
		fscanf(DBinfo,"%d",&num_users);
		fclose(DBinfo);
		
		// Nome
		
		b = sprintf(buff,"Nome : ");
		write(client_fd,buff,b);
		
		b=read(client_fd,buff,BUF_SIZE);
		buff[b]='\0';
		
		sscanf(buff,"%s",nome);
		
		for (i = 0;i<num_users;i++)
			if (strcmp(nome,users[i].nome)==0)
				existe = 1;			
		
		if (existe ==1){
			
			b = sprintf(buff," Nome ja existente na BD\n");
			write(client_fd,buff,b);
		}
		else{
		
			// nome
			
			strcpy(users[num_users].nome,nome);
		
			// Password
		
			b = sprintf(buff,"Password : ");
			write(client_fd,buff,b);
		
			b=read(client_fd,buff,BUF_SIZE);
			buff[b]='\0';
		
			sscanf(buff,"%s",password);
		
			strcpy(users[num_users].password,password);
			
			// variáveis a zero
			
			users[num_users].num_amigos =0;
			users[num_users].num_pedidos_amizade =0;
			users[num_users].msg_num =0;

			b = sprintf(buff,"A conta foi criada com sucesso!\nO utilizador deve agora entrar nela e escolher a opcao alterar perfil para preencher os seus dados\n");
			write(client_fd,buff,b);
		
			num_users++;
		}	
		
	DBinfo = fopen("DBinfo.txt","w");
	fprintf(DBinfo,"%d",num_users);
	fclose(DBinfo);
	salva_info();
}

void apaga_user(int client_fd){
	
	char nome[20];
	int k;
	char buff[BUF_SIZE];
	int num_users;
	int b;
	FILE *DBinfo;
		
	DBinfo = fopen("DBinfo.txt","r");
	fscanf(DBinfo,"%d",&num_users);
	fclose(DBinfo);
		
	// Nome
		
	b = sprintf(buff,"Nome : ");
	write(client_fd,buff,b);
		
	b=read(client_fd,buff,BUF_SIZE);
	buff[b]='\0';
		
	sscanf(buff,"%s",nome);
		
	for (k=0;k<num_users;k++){		// procura user
		if (strcmp(users[k].nome,nome)==0){ // encontra user
		 	// fazemos uma translacçao na memória de forma ao dados sobreporem o utilizador a ser apagado
			memmove(&users[k],&users[k+1],(50-k)*sizeof(User));
			b = sprintf(buff,"\nApagado com sucesso!\n");
   			write(client_fd,buff,b);
			break;	
		}
	}
	
	num_users--;
	DBinfo = fopen("DBinfo.txt","w");
	fprintf(DBinfo,"%d",num_users);
	fclose(DBinfo);
	salva_info();
}
	
void altera_perfil(int client_fd,int user){
	
	char buff[BUF_SIZE];
	int b;

	carrega_info();
	
	
	b = sprintf(buff,"Nome - ");
	write(client_fd,buff,b);
	
	b=read(client_fd,buff,BUF_SIZE);
	buff[b]='\0';
    strcpy(users[user].nome_completo,buff);
	
	// pw
	b = sprintf(buff,"Password - ");
	write(client_fd,buff,b);
	
	b=read(client_fd,buff,BUF_SIZE);
    sscanf(buff,"%s",users[user].password);
	
	// idade
	b = sprintf(buff,"Idade - ");
	write(client_fd,buff,b);
	
	b=read(client_fd,buff,BUF_SIZE);
    	sscanf(buff,"%d",&users[user].idade);
		
	
	// morada
	b = sprintf(buff,"Morada - ");
	write(client_fd,buff,b);
	
	b=read(client_fd,buff,BUF_SIZE);
	buff[b]='\0';
    	
	strcpy(users[user].morada,buff);

	// curso
	b = sprintf(buff,"Curso - ");
	write(client_fd,buff,b);
	
	b=read(client_fd,buff,BUF_SIZE);
	buff[b]='\0';
    	
	strcpy(users[user].curso,buff);
	
	// gostos
	b = sprintf(buff,"Gostos - ");
	write(client_fd,buff,b);
	
	b=read(client_fd,buff,BUF_SIZE);
    buff[b]='\0';
    	
	strcpy(users[user].gostos,buff);
	
	// visibilidade do perfil

	b = sprintf(buff,"Visibilidade (0-Privado|1-Publico|2-Amigos) - ");
	write(client_fd,buff,b);

	b=read(client_fd,buff,BUF_SIZE);
	sscanf(buff,"%d",&users[user].vis);
	
	salva_info();
}

void adicionar_amigo(int client_fd,int user){
	
	char buff[BUF_SIZE];
	int num_users;
	int b,i=0,indice;
	FILE *DBinfo;
		
	DBinfo = fopen("DBinfo.txt","r");
	fscanf(DBinfo,"%d",&num_users);
	fclose(DBinfo);
	
	carrega_info();

	for (;i<num_users;i++){		//impedimos que o utilizador se veja a ele proprio na lista
		if (user != i){
			b = sprintf(buff,"%d - %s\n",i,users[i].nome);
			write(client_fd,buff,b);
		}
	}
	
	b=read(client_fd,buff,BUF_SIZE);
    sscanf(buff,"%d",&indice);
	
	if (users[indice].num_pedidos_amizade==50||users[indice].num_amigos==50){
		b = sprintf(buff,"Lista de amigos do destino cheia\n");
		write(client_fd,buff,b);
	}
		
	else{
		if (users[user].num_amigos==50){
			b = sprintf(buff,"A sua lista de amigos esta cheia\n");
			write(client_fd,buff,b);
	}
		else{
			users[indice].pedidos_amizade[users[indice].num_pedidos_amizade]= user;
			users[indice].num_pedidos_amizade++;
		}
	}
		
	salva_info();
}

void eliminar_amigo(int client_fd,int user){
	
	int i =0,j=0;
	int b,indice;
	char buff[BUF_SIZE];
	
	if (users[user].num_amigos>0){
		for(;i<users[user].num_amigos;i++){					
			b = sprintf(buff,"%d - %s\n",i,users[users[user].amigos[i]].nome);
			write(client_fd,buff,b);
		}
	
		b=read(client_fd,buff,BUF_SIZE);
    	sscanf(buff,"%d",&indice);
    

		// precisamos encontrar o indice no amigo de destino
		for (;j<users[ users[user].amigos[indice] ].num_amigos;j++)
		{
			if (users[ users[user].amigos[indice] ].amigos[j]== user)
			{
				// translacçao da memoria na lista de amigos do destino
				memmove(&users[ users[user].amigos[indice] ].amigos[j],  &users[ users[user].amigos[indice] ].amigos[j+1]   , (users[ users[user].amigos[indice] ].num_amigos-(j+1))*sizeof(int));
			 
				users[ users[user].amigos[indice] ].num_amigos--; 		// decrementamos o numero de amigos do destino
				memmove(&users[user].amigos[indice],&users[user].amigos[indice+1], (users[user].num_amigos-(indice+1))*sizeof(int)); // tranladamos a lista de amigos do utilizador
			users[user].num_amigos--;		// decrementamos o numero de amigos do utilizador
			}
		}
	}
}

void ler_mensagens(int client_fd,int user){
	
	int i = 0,num_mensagens= users[user].msg_num,b;
	char buff[BUF_SIZE];
	b = sprintf(buff,"Tem %d novas mensagens\n",num_mensagens);
	write(client_fd,buff,b);
	
	for (;i<num_mensagens;i++)
	{
		b = sprintf(buff,"Mensagem %d - %s\n",i, users[user].mensagens[i]);
		write(client_fd,buff,b);
	}	
	
	// Mensagens apagadas automaticamente depois de lidas
	
	users[user].msg_num = 0;

}

void aceitar_amigo(int client_fd,int user){

	int i =0, indice,b;
	char buff[BUF_SIZE];

	for(;i<users[user].num_pedidos_amizade;i++){
		b = sprintf(buff,"%d - %s\n",i,users[users[user].pedidos_amizade[i]]   .nome);
		write(client_fd,buff,b);
	}
	
	b=read(client_fd,buff,BUF_SIZE);
    sscanf(buff,"%d",&indice);

	if (users[user].num_amigos<50 && users[ users[user].pedidos_amizade[indice] ].num_amigos <50){
		
		users[user].amigos[users[user].num_amigos]=users[user].pedidos_amizade[indice];
		users[ users[user].pedidos_amizade[indice] ].amigos[ users[ users[user].pedidos_amizade[indice] ].num_amigos ]= user;
		
		users[ users[user].pedidos_amizade[indice] ].num_amigos++;
			
		users[user].num_amigos++;
		users[user].num_pedidos_amizade--;	
		b = sprintf(buff,"Aceite\n");
		write(client_fd,buff,b);	
	}
	else{
		b = sprintf(buff,"Lista cheia\n");
		write(client_fd,buff,b);	
	}

}

void ver_perfis(int client_fd,int user){

	char buff[BUF_SIZE];
	int num_users;
	int b,i=0,indice;

	FILE *DBinfo;		
	DBinfo = fopen("DBinfo.txt","r");
	fscanf(DBinfo,"%d",&num_users);
	fclose(DBinfo);

	for (;i<num_users;i++){		//impedimos que o utilizador se veja a ele proprio na lista
		if (user != i){
			b = sprintf(buff,"%d - %s\n",i,users[i].nome);
			write(client_fd,buff,b);
		}
	}
	
	b=read(client_fd,buff,BUF_SIZE);
    sscanf(buff,"%d",&indice);
	
	imprime_perfil(client_fd,user,indice);
	
}

void lista_amigos(int client_fd,int user){
	
	char buff[BUF_SIZE];
	int b,i=0;
	
	for (;i<users[user].num_amigos;i++){		
		b = sprintf(buff,"%d - %s\n",i,users[users[user].amigos[i]].nome);
		write(client_fd,buff,b);	
	}
}

void salva_info(){
	FILE *f;
	f = fopen("DB","w");
	fwrite(users,sizeof(User),50,f);
	fclose(f);
}

void carrega_info(){
	FILE *f;
	f = fopen("DB","r");
	fread(users,sizeof(User),50,f);
	fclose(f);

}

