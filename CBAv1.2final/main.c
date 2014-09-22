/*
-------------------------
Igor Cruz
João Magalhaes
Versao 1.2 - final

-------------------------
                        */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "estrutura.h"          // Structs e tipos de dados utilizados no programa
#include "funcoes.h"            // Cabeçalhos das funçoes uteis a execuçao do programa

short tentativas = 2;           // verifica o num de tentativas de login seguidas na mesma conta
short logged = 0;               // 0 -> Existe user logado no programa | 1 -> Nao existe user logado

int num_conta_anterior =0;

Lista_utilizadores anterior;    // Utilizadores em tempo de execução
Lista_utilizadores actual;

Lista_utilizadores users;

dinheiro *caixa;                 // armazena as notas existentes no CBA

int main(){

    users = cria_lista_utilizadores();
    carrega_info_ficheiro();                // Carrega dos ficheiros database.txt e notas.txt

    system("CLS");
    printf("\nBem vindo ao Simulador de Caixa Bancaria Automatica.");
    printf("\n\nversao 1.2 - final");

    while (logged == 0){                    // Enquanto nao existir um utilizador conenctado
        logged = login(users);              // O multibanco vai pedir informacoes para login
        if (logged == -1){                  // conta bloqueada
            printf("\nO seu cartao esta bloqueado, dirija-se a uma agencia!\n");
            salva_operacao(0,0.0,actual,actual);
            logged = 0;
            system("PAUSE");
        }
        else if (logged == 1){               // utilizador
            mostra_menu_utilizador();
        }
        else if (logged == 2){               // admin
            mostra_menu_admin();
        }
        else if (logged == 3){              // conta existente , pin errado
            if (tentativas > 0){           // tentativas suficientes
                salva_operacao(0,0.0,actual,actual);
                printf("Tentativa errada, tem mais %hd tentativas.\n", tentativas);
                system("PAUSE");
                tentativas--;
                logged = 0;
            }
            else{
                                            // nao existem mais tentativas
                actual->info->nivel=-1;     // bloqueia utilizador
                tentativas = 2;             // restore no numero de tentativas
                logged = 0;
                printf("A sua Conta foi bloqueada, Por favor contacte um funcionario.\n");
                system("PAUSE");
            }
        }
    }

    return 0;
}

int login(Lista_utilizadores users){

    /* Dada uma lista de utilizadores, esta funçao revolve:

        -1 -> Caso a conta esteja bloqueada;
         0 -> Caso a conta seja inexistente na base de dados;
         1 -> Caso a conta seja de um utilizador e o pin seja correcto;
         2 -> Caso a conta seja de um administrador e o pin seja correcto;
         3 -> Caso a conta exista, mas o pin inserido seja incoincidente com o existente na base de dados;
    */

    unsigned num_conta;
    char pin[10];

    printf("\n\n\n\tLogin");
    printf("\n\n\n\t.Numero De Conta: ");
    scanf("%u", &num_conta);
    printf("\n\t.PIN: ");
    scanf("%s", pin);

    if (num_conta != num_conta_anterior)    // se inserir-mos outra conta as tentativas voltam a 2
        tentativas = 2;

    num_conta_anterior = num_conta;         // guardamos o num de conta para verificar na proxima tentativa

    procura_utilizadores(users , num_conta, &anterior, &actual);    // procuramos a conta na base de dados

    if (actual == NULL){
        printf("\nConta inexistente na base de dados.\n\n");          // caso nao exista nenhum dos casos acima consideramos conta inexistente
        system("PAUSE");
        return 0;
    }
    if(actual->info->nivel==-1)                                 // conta bloqueada
        return -1;
    else if(strcmp(actual->info->PIN,pin)==0 && actual->info->nivel ==0){  // conta de utilizador + pin correcto
        salva_operacao(1,0.0,actual,actual);
        return 1;
    }
    else if(strcmp(actual->info->PIN,pin)==0 && actual->info->nivel ==1){  // conta de administrador + pin correcto
        salva_operacao(1,0.0,actual,actual);
        return 2;
    }
    else if(strcmp(actual->info->PIN,pin)!=0)                             // pin inserido != existente na conta da base de dados
        return 3;

    return 0;
}

void mostra_menu_admin(void){

    short opcao;

    printf("\n\n-----------------------------------------------------\n\n");                       //Mostra as opçoes de admin + as notas existentes em caixa
    printf("Menu Funcionario:\n\n\n");
    printf("1 - Levantamentos\t\t2 - Tranferencias\t Notas existentes:\n");
    printf("3 - Depositos\t\t\t4 - Alteracao PIN\t .Notas de 5  : %hu\n",caixa->notas5);
    printf("\t\t\t\t\t\t\t .Notas de 10 : %hu\n",caixa->notas10);
    printf("5 - Criar Conta\t\t\t6 - Eliminar Conta\t .Notas de 20 : %hu\n",caixa->notas20);
    printf("7 - Listar Clientes\t\t8 - Listar operacoes\t .Notas de 50 : %hu\n",caixa->notas50);
    printf("9 - Alterar numero Notas\t10- Imprimir Conta\n\n");
    printf("11-Bloquear Conta\t\t12-Desbloquear Conta\n\n");
    printf("99-Terminar Execucao\t\t13 - Abandonar\n\n");
    printf("-----------------------------------------------------\n\n");
    printf("Montante: %.2lf\t\t Opcao: ", actual->info->montante);

    scanf("%hd", &opcao);

    switch (opcao){                                 // reencaminha para as funçoes desejadas
        case 1:
            menu_levantamentos();
            mostra_menu_admin();
            break;
        case 2:
            menu_tranferencias();
            mostra_menu_admin();
            break;
        case 3:
            menu_depositos();
            mostra_menu_admin();
            break;
        case 4:
            menu_alterapin();
            mostra_menu_admin();
            break;
        case 5:
            menu_cria_conta();
            mostra_menu_admin();
            break;
        case 6:
            menu_elimina_conta();
            mostra_menu_admin();
            break;
        case 7:
            menu_lista_clientes();
            mostra_menu_admin();
            break;
        case 8:
            menu_lista_operacoes();
            mostra_menu_admin();
            break;
        case 9:
            menu_carrega_notas();
            mostra_menu_admin();
            break;
        case 10:
            menu_imprimir_conta();
            mostra_menu_admin();
            break;
        case 11:
            menu_bloqueia();
            mostra_menu_admin();
            break;
        case 12:
            menu_desbloqueia();
            mostra_menu_admin();
            break;
        case 13:
            salva_operacao(13,0.0,actual,actual);
            logged = 0;
            break;
        case 99:
            menu_sair();
            mostra_menu_admin();
            break;
        default:
            printf("Opcao inexistente, por favor escolha uma opcao de 1 a 11.");
            system("PAUSE");
            mostra_menu_admin();
            break;
    }
}

void mostra_menu_utilizador(void){

    short opcao;

    printf("\n\n-----------------------------------------------------\n\n");  // imprime menu user
    printf("\n\tMenu:\n\n");
    printf("1 - Levantamentos\t\t2 - Tranferencias\n");
    printf("3 - Depositos\t\t\t4 - Alteracao PIN\n\n");
    printf("\t\t\t\t5 - Abandonar\n\n\n");
    printf("Montante: %.2lf\n\n", actual->info->montante);

    scanf("%hd", &opcao);
    switch (opcao){                                                           // reencaminha para a funçao respectiva
        case 1:
            menu_levantamentos();
            mostra_menu_utilizador();
            break;
        case 2:
            menu_tranferencias();
            mostra_menu_utilizador();
            break;
        case 3:
            menu_depositos();
            mostra_menu_utilizador();
            break;
        case 4:
            menu_alterapin();
            mostra_menu_utilizador();
            break;
        case 5:
            salva_operacao(13,0.0,actual,actual);
            logged = 0;
            break;
        default:
            printf("Escolheu uma opcao invalida, escolha opcao de 1 a 5, obrigada.");
            system("PAUSE");
            mostra_menu_utilizador();
            break;

    }
}

int menu_levantamentos(){

    short  confirma, selector;
    unsigned int destino;
    double quantia;
    Lista_utilizadores ant,dest;

    printf("\n\n-----------------------------------------------------\n\n"); // menu levantamentos
    printf ("\nEscolheu a opcao levantamentos:\n\n");

    if (actual->info->nivel == 1){   //se o user for func pedimos o numero de conta para depositar
        printf("\nInsira o numero da conta do destinatario:\n>>> ");
        scanf("%u",&destino);
        procura_utilizadores(users,destino,&ant,&dest);
        if (dest == NULL){        // se a conta de destino nao existe
            printf("Essa conta nao existe na base de dados\n");
        return 0;
        }
    }
    else                            // se nao for funcionario a conta de destino é a propria conta
        dest = actual;

    printf ("Montante desejado?\n\n");
    printf ("1 - 20 Euros\t\t2 - 40 Euros\n3 - 60 Euros\t\t4 - 100 Euros\n5 - 150 Euros\t\t6 - 200 Euros\n7 - Outro Montante\n\t\t\tOpcao:");

    scanf("%hd", &selector);

    switch (selector){
        case 1:
            quantia = 20.0;
            break;
        case 2:
            quantia = 40.0;
            break;
        case 3:
            quantia = 60.0;
            break;
        case 4:
            quantia = 100.0;
            break;
        case 5:
            quantia = 150.0;
            break;
        case 6:
            quantia = 200.0;
            break;
        case 7:
            printf ("\n\nMontante desejado?\n>>> ");
            scanf("%lf",&quantia);
            break;
    }

    printf("Levantar %.2lf euros ?\n1-Confirmar\t\t2-cancelar\n>>> ", quantia);

    scanf("%hd",&confirma);

    if (confirma == 1 && (int)quantia%5 == 0 && (int)quantia <= 200){  //confirma + (quantia div 5 + quantia <=200)
        levanta_quantia(dest,quantia);
        salva_operacao(3,quantia,actual,dest);
    }
    else if(confirma == 1){                          //confirma mas quantia nao é valida
        printf("Montante invalida, insira um montante (max 200) que possa ser entregue em notas.\n");
        system("PAUSE");
    }
    else{
        printf("\nCancelado!\n");
        system("PAUSE");
    }
    return 0;
}

void levanta_quantia(Lista_utilizadores destino,double quantia){

    int quantia_temporaria = 0;
    dinheiro dado,copia_caixa;

    dado.notas50 =0;
    dado.notas20 =0;
    dado.notas10 =0;
    dado.notas5 =0;
    copia_caixa.notas5 = caixa->notas5;    //copia das notas em caixa, para quando tiramos uma nota pelo meio ela nao nao desaparecer no caso de nao haver notas suficientes em caixa
    copia_caixa.notas10 = caixa->notas10;
    copia_caixa.notas20 = caixa->notas20;
    copia_caixa.notas50 = caixa->notas50;
    if (actual->info-> montante >= quantia){         //Algoritmo para escolha de notas;

            //RETIRAMOS UMA NOTA DE CADA

        if ((int)quantia - quantia_temporaria >= 5 && copia_caixa.notas5 >0){
                quantia_temporaria += 5;
                dado.notas5++;
                copia_caixa.notas5--;
                }
        if ((int)quantia - quantia_temporaria >= 10 && copia_caixa.notas10 >0){
                quantia_temporaria += 10;
                dado.notas10++;
                copia_caixa.notas10--;
            }
        if ((int)quantia - quantia_temporaria >= 20 && copia_caixa.notas20 >0){
                quantia_temporaria += 20;
                dado.notas20++;
                copia_caixa.notas20--;
            }
        if ((int)quantia - quantia_temporaria >= 50 && copia_caixa.notas50 >0){
                quantia_temporaria += 50;
                dado.notas50++;
                copia_caixa.notas50--;
            }

        /*Apesar de ja termos uma nota de cada podemos nao ter ainda obtido a quantia necessaria,
        vamos por isso escolher as notas mais altas para obter as somas de modo a minimizar o numero de notas*/

        while ((int)quantia - quantia_temporaria >= 50 && copia_caixa.notas50 >0){
                quantia_temporaria += 50;
                dado.notas50++;
                copia_caixa.notas50--;
                }
        while ((int)quantia - quantia_temporaria >= 20 && copia_caixa.notas20 >0){
                quantia_temporaria += 20;
                dado.notas20++;
                copia_caixa.notas20--;
                }
        while((int)quantia - quantia_temporaria >= 10 && copia_caixa.notas10 >0){
                quantia_temporaria += 10;
                dado.notas10++;
                copia_caixa.notas10--;
                }
        while((int)quantia - quantia_temporaria >= 5 && copia_caixa.notas5 >0){
                quantia_temporaria += 5;
                dado.notas5++;
                copia_caixa.notas5--;
                }

        if((int)quantia - quantia_temporaria >0){
            printf("\nEste terminal nao tem notas suficientes.");
            printf("\nPor favor dirija-se a outro terminal ou a uma agencia, Obrigada.");
            system("PAUSE");
        }
        else{
            caixa->notas5 -= dado.notas5;
            caixa->notas10 -= dado.notas10;
            caixa->notas20 -= dado.notas20;
            caixa->notas50 -= dado.notas50;

            destino->info-> montante -= quantia;

            printf("\nNotas devolvidas:\nNotas de 5 : %hu Notas de 10 : %hu Notas de 20 : %hu Notas de 50 : %hu\n",dado.notas5,dado.notas10,dado.notas20,dado.notas50);
            system("PAUSE");
        }
    }
    else{
        printf("\nDinheiro em conta insuficiente!");
        system("PAUSE");
    }
}

int menu_tranferencias(){

    unsigned int conta_dest,conta_origem;
    double quantia;
    short confirma;
    char pin[10];

    printf ("\nEscolheu a opcao transferencias:\n\n");

    if (actual->info->nivel == 1){   //se o user for func pedimos o numero de conta para depositar
        printf("\nInsira o numero da conta de origem:\n>>> ");
        scanf("%u",&conta_origem);
    }

    printf ("Insira o numero de conta do destinario:\n>>> ");
    scanf("%ud", &conta_dest);
    printf ("Insira o montante que deseja transferir:\n>>> ");
    scanf("%lf", &quantia);
    printf ("Insira o seu PIN:\n>>> ");
    scanf("%s", pin);

    printf("\nTranferir %.2lf euros da conta %u para a conta %u. \n1 - Confirmar\t 2 - Cancelar",quantia,conta_origem,conta_dest);
    printf ("\n>>> ");
    scanf("%hd", &confirma);

    if (confirma == 1){
        if( strcmp(pin,actual->info->PIN)==0) //pin certo
            tranfere(conta_origem,conta_dest,quantia);
        else{
            printf("PIN errado");
            system("PAUSE");
        }
    }

    return 0;
}

int tranfere(unsigned int conta_origem,unsigned int conta_dest, double quantia){

    Lista_utilizadores index1,index2,index3,index4;

    procura_utilizadores(users,conta_origem,&index1,&index2);

    if (index2 == NULL){
        printf("Conta de origem inexistente.\n");
        system("PAUSE");
        return 0;
        }
    else if (index2->info->montante >= quantia){  // se tiver dinheiro suficiente procura a conta e transfere

        procura_utilizadores(users , conta_dest, &index3, &index4); //procuramos nas listas

        if (index4 == NULL){
            printf("A Conta de destino nao existe");
            system("PAUSE");
            return 0;
        }
        else {
            salva_operacao(4,quantia,index2,index4);
            index2->info->montante -= quantia;
            index4->info->montante += quantia;
            printf("Tranferencia efectuada com sucesso!");
            system("PAUSE");
            }
    }
    else{
        printf("Nao tem dinheiro suficiente em conta.\n");
        system("PAUSE");
    }
    return 0;
}

int menu_depositos(){

    double quantia;
    short confirma;
    unsigned int destino;
    char pin[10];
    dinheiro inserido;
    Lista_utilizadores ant,dest;

    printf ("\nEscolheu a opcao depositos:\n\n");

    if (actual->info->nivel == 1){   //se o user for func pedimos o numero de conta para depositar
        printf("\nInsira o numero da conta do destinatario:\n>>> ");
        scanf("%u",&destino);
        procura_utilizadores(users,destino,&ant,&dest);
        if (dest == NULL){        // se a conta de destino nao existe
            printf("Essa conta nao existe na base de dados\n");
            return 0;
        }
    }
    else
        dest= actual;

    printf ("Insira o montante que deseja depositar:\n>>> ");
    scanf("%lf", &quantia);
    printf ("Insira o seu PIN:\n>>> ");
    scanf("%s", pin);

    printf("\nDepositar %.2lf euros na conta %u?\n 1 - Confirmar\t 2 - Cancelar\n>>> ",quantia,dest->info->numero_conta);
    scanf("%hd", &confirma);

    if (confirma == 1){
        if ( strcmp(pin,actual->info->PIN)==0){
            printf("Insira as notas: ");
            printf("Notas de 5: ");
            scanf("%hu",&inserido.notas5);
            printf("Notas de 10: ");
            scanf("%hu",&inserido.notas10);
            printf("Notas de 20: ");
            scanf("%hu",&inserido.notas20);
            printf("Notas de 50: ");
            scanf("%hu",&inserido.notas50);

            if (inserido.notas5 *5 +inserido.notas10 *10+inserido.notas20 *20 +inserido.notas50 *50 != (int)quantia){
                salva_operacao(16,quantia,actual,actual);
                printf("Inseriu numero de notas errado!");
                system("PAUSE");
                }
            else{
                salva_operacao(5,quantia,actual,actual);
                dest->info->montante += quantia;
                caixa->notas5 += inserido.notas5;
                caixa->notas10 += inserido.notas10;
                caixa->notas20 += inserido.notas20;
                caixa->notas50 += inserido.notas50;
                printf("\n%.2lf euros foram depositados conta %u.",quantia,dest->info->numero_conta);
                system ("PAUSE");
                }
        }
        else{
            printf("\nPIN errado!");
            system("PAUSE");
        }
    }

    return 0;
}

void menu_alterapin(){

    char pin2[10];
    short confirma;

    printf ("\nEscolheu a opcao alterar PIN:\n\n");
    printf ("\nInsira o seu novo PIN:\n");
    scanf("%s",pin2);
    printf("\n1 - Confirmar\t 2 - Cancelar");
    printf ("\n>>> ");
    scanf("%hd", &confirma);

    if (confirma == 1){
        salva_operacao(6,0.0,actual,actual);
        strcpy(actual->info->PIN,pin2);
        printf("Alterado com sucesso!");
        system("PAUSE");
    }
    else{
        printf("\nCancelado!");
        system("PAUSE");
    }
}

void menu_cria_conta(){

    char pin[10];
    short confirma, nivel;
    int numero_conta;
    Lista_utilizadores aux,ant;

    printf ("\nEscolheu a opcao Criar Conta:");

    printf("\n\nTipos de conta:\n0 - cliente\t1 - Funcionario");
    printf ("\n>>> ");
    scanf("%hd",&nivel);

    printf ("\nInsira um pin para a conta: ");
    scanf("%s", pin);

    if (nivel == 0 || nivel == 1){
        printf("\n1 - Confirmar\t 2 - Cancelar");
        printf ("\n>>> ");
        scanf("%hd", &confirma);

            if (confirma == 1){
                //precisamos de um numero para a conta
                aux = users;
                while (aux!= NULL){

                    if (aux->next == NULL){
                        numero_conta = aux->info->numero_conta+1; // o numero de conta = numero ultima conta +1
                        printf("%u",numero_conta);
                    }
                    aux = aux->next;

                }

                insere_lista_utilizadores(users,numero_conta,pin,0.0,nivel);
                procura_utilizadores(users,numero_conta,&ant,&aux);
                salva_operacao(7,0.0,actual,aux);
                printf("\nFoi criada uma nova conta:");
                printf("\n->Numero de conta : %d",numero_conta);
                printf("\n->PIN : %s",pin);
                printf("\n->Montante : 0 ");
                if (nivel == 0)
                    printf("\n->Tipo de Conta: cliente ");
                else if (nivel == 1)
                    printf("\n->Tipo de Conta: Funcionario ");

                printf("\n\nGuarde as informacoes para referencia futura!");
                system("PAUSE");
            }
            else{
                printf("\nCancelado!");
                system("PAUSE");
            }
    }
    else{
        printf("Opcao invalida. Processo cancelado.");
        system("PAUSE");
    }
}

int menu_elimina_conta(){

    unsigned int num_conta;
    short confirma;
    Lista_utilizadores ant, no;

    printf ("\nEscolheu a opcao Eliminar Conta:\n\n");
    printf ("Insira o numero de conta\n>>> ");
    scanf("%u", &num_conta);

    if (conta_contas(users)< 3){
        printf("A conta nao pode ser apagada, pois o programa necessita de pelo menos 2 contas\n");
        return 0;
    }

    if (num_conta != actual->info->numero_conta){
        printf ("\nA conta %u sera eliminada...",num_conta);
        printf("\n1 - Confirmar\t 2 - Cancelar");
        printf ("\n>>> ");
        scanf("%hd", &confirma);
        if (confirma == 1){
            procura_utilizadores(users,num_conta, &ant, &no);
            salva_operacao(8,0.0,actual,no);
            if (no == NULL){
                printf("Conta inexistente em base de dados!\n");
                system("PAUSE");
            }
            else if (no->info->montante>200){
                printf("O montante da conta e Muito alto!");
                system("PAUSE");
            }
            else{
                levanta_quantia(no,no->info->montante);
                if (no->next== NULL){ //se for o ultimo elem da lista nao podemos meter o previous pk n temos espaço alocado
                    ant->next = NULL;
                }
                else{
                    ant->next = no->next;
                    no->next->previous = ant;
                }

                free(no->info);
                free(no);
                printf("A conta %u foi eliminada!",num_conta);
                system("PAUSE");
            }
        }
        else{
            printf("\nCancelado!");
            system("PAUSE");
        }
    }
    else{
        printf("\nImpossivel eliminar sua propria conta!");
        system("PAUSE");
    }
    return 0;
}

void menu_lista_clientes(){

    int n = conta_contas(users);
    Lista_utilizadores ant,no;
    struct_user *temp;

    ant = users->next,no = ant->next; // ant = users->next deixamos o header no inicio da fila porem posteriormente nao sera impresso pois a funcao imprime começa tb em users->next
  //Ordenamos por montantes
    while(n> 0){

        while (no != NULL){
            if (ant->info->montante < no->info->montante){
                temp = no->info;
                no->info = ant->info;
                ant->info = temp;
            }
            ant = no;
            no = no->next;
        }
        ant = users->next,no = ant->next;
        n--;
    }

    imprime_clientes(); //Imprimimos depois de ordenado por montantes
    salva_operacao(9,0.0,actual,actual);

    n = conta_contas(users);
    ant = users->next,no = ant->next;

    //Reordenamos por numeros de conta
    while(n> 0){

        while (no != NULL){
            if (ant->info->numero_conta > no->info->numero_conta){
                temp = no->info;
                no->info = ant->info;
                ant->info = temp;
            }
            ant = no;
            no = no->next;
        }
        ant = users->next,no = ant->next;
    n--;
    }
}

void menu_lista_operacoes(){

    short confirma;

    printf ("\nEscolheu a opcao Listar operacoes:");
    printf ("Opcoes");
    printf("\n1 - Listar\t\t\n2 - Apagar lista");
    printf ("\n\nOpcao: ");
    scanf("%hd", &confirma);
        switch (confirma){
            case 1:
                salva_operacao(10,0.0,actual,actual);
                imprime_operacoes();
                break;
            case 2:
                limpa_operacoes();
                salva_operacao(17,0.0,actual,actual);
                break;
            default:
                printf("Cancelado!");
                system("PAUSE");
                break;
            }
}

void menu_carrega_notas(){

    short confirma;
    unsigned short notas5,notas10,notas20,notas50;

    printf("\nEscolheu a opcao carregar notas:");
    printf("\n\nDinheiro existente em caixa:");
    printf("\n\nNotas de 5 : %u\nNotas de 10 : %u\nNotas de 20 : %u\nNotas de 50 : %u\n",caixa->notas5,caixa->notas10,caixa->notas20,caixa->notas50);
    printf("\nInsira as quantias que deseja carregar: ");

    printf("\nNotas de 5 : ");
    scanf("%hu", &notas5);
    printf("Notas de 10 : ");
    scanf("%hu", &notas10);
    printf("Notas de 20 : ");
    scanf("%hu", &notas20);
    printf("Notas de 50 : ");
    scanf("%hu", &notas50);

    printf("\nDepositar multibanco com esta quantia de notas?\n1 - Confirmar\t 2 - Cancelar");
    printf ("\n>>> ");
    scanf("%hd", &confirma);

    if (confirma == 1){
        salva_operacao(11,0.0,actual,actual);
        caixa->notas5+=notas5;
        caixa->notas10+=notas10;
        caixa->notas20+=notas20;
        caixa->notas50+=notas50;
        printf("\nMultibanco carregado com Sucesso!");
        printf("\n\nDinheiro existente em caixa:");
        printf("\nNotas de 5 : %u\nNotas de 10 : %u\nNotas de 20 : %u\nNotas de 50 : %u\n",caixa->notas5,caixa->notas10,caixa->notas20,caixa->notas50);
        system("PAUSE");
    }
    else{
        printf("Cancelado!");
        system("PAUSE");
    }
}


void menu_bloqueia(){

    short  confirma;
    unsigned num_conta;

    printf ("\nEscolheu a opcao bloquear conta:\n\n");
    printf ("Insira o numero de conta:\n>>> ");
    scanf("%u", &num_conta);
    printf("Bloquear a conta %u?\n1-Confirmar\t\t2-cancelar\n>>> ", num_conta);
    scanf("%hd",&confirma);

    if (confirma == 1){
            Lista_utilizadores index1;
            Lista_utilizadores index2;
            procura_utilizadores(users , num_conta, &index1, &index2);
            salva_operacao(3,0.0,actual,index2);
            index2->info->nivel = -1;
            printf("\nA conta foi bloqueada com sucesso!");
            system("PAUSE");
    }
    else{
        printf("Cancelado!");
        system("PAUSE");
    }
}

void menu_desbloqueia(){

    short  confirma;
    unsigned num_conta;

    printf ("\nEscolheu a opcao desbloquear conta:\n\n");
    printf ("Insira o numero de conta:\n>>> ");
    scanf("%u", &num_conta);
    printf("Desbloquar a conta %u?\n1-Confirmar\t\t2-cancelar\n>>> ", num_conta);
    scanf("%hd",&confirma);

    if (confirma == 1){
            Lista_utilizadores index1;
            Lista_utilizadores index2;
            procura_utilizadores(users , num_conta, &index1, &index2);
            salva_operacao(18,0.0,actual,index2);
            index2->info->nivel = 0;
            printf("\nA conta foi desbloqueada com sucesso!");
            system("PAUSE");
    }
    else{
        printf("Cancelado!");
        system("PAUSE");
    }
}

void menu_imprimir_conta(){

    unsigned num_conta;
    Lista_utilizadores ant,no;

    printf ("\nEscolheu a opcao imprimir conta:\n\n");
    printf ("Insira o numero de conta:\n>>> ");
    scanf("%u",&num_conta);

    procura_utilizadores(users,num_conta,&ant,&no);

    if (no == NULL)
        printf("Conta Inexistente na Base de dados!");
    else
    {
        printf("\nNumero conta: %u",no->info->numero_conta);
        printf("\nPIN:\t %s",no->info->PIN);
        printf("\nDinheiro: %.2lf",no->info->montante);
        if (no->info->nivel == 0)
            printf("\nTipo de conta: cliente\n");
        else if (no->info->nivel == 1)
            printf("\nTipo de conta: Funcionario\n");
        else if (no->info->nivel == -1)
            printf("\nConta Bloqueada\n");
    }

    system("PAUSE");
}

void menu_sair(){

    short  confirma;

    printf ("\nATENCAO ESTA OPERACAO TERMINARA A EXECUCAO DO CBA.\nTodos os dados serao guardados em ficheiros\n");
    printf ("Decerteza que pretende continuar?:\n1-Confirmar\t\t2-cancelar\n>>> ");
    scanf("%hd",&confirma);

    if (confirma == 1){
        salva_operacao(14,0.0,actual,actual);
        salva_info_ficheiro();
        users = destroi_lista_utilizadores (users);
        free (caixa);
        exit(EXIT_SUCCESS);
    }
    else{
        printf("Cancelado!");
        system("PAUSE");
    }
}

Lista_utilizadores cria_lista_utilizadores (void){

    Lista_utilizadores aux;
    struct_user *infos;

    aux = (Lista_utilizadores) malloc (sizeof (List_node));
    infos = (struct_user *) malloc (sizeof(struct_user));

    aux->info = infos;

    aux->info->numero_conta = 0;
    strcpy(aux->info->PIN,"0000");
    aux->info->montante = 0;
    aux->info->nivel = 0;

    aux->next = NULL;
    aux->previous = NULL;

    return aux;
}

void insere_lista_utilizadores(Lista_utilizadores users, unsigned int numero_conta, char b[10],double  c, short d){

    Lista_utilizadores no;
    Lista_utilizadores anterior, inutil;
    struct_user *infos;

    no = (Lista_utilizadores) malloc (sizeof (List_node));
    infos = (struct_user *) malloc (sizeof(struct_user));

    no->info = infos;

    no->info->numero_conta = numero_conta;
    strcpy(no->info->PIN,b);
    no->info->montante = c;
    no->info->nivel = d;

    procura_utilizadores(users,numero_conta, &anterior, &inutil);
    if (inutil == NULL){
        no->previous = anterior;
        no->next = NULL;
        anterior->next = no;
    }
    else{
        no->previous = anterior;
        no->next = inutil->next;
        anterior->next = no;

    }
}

void procura_utilizadores(Lista_utilizadores users, int num_conta, Lista_utilizadores *ant, Lista_utilizadores *actual){
    *ant = users; *actual = users->next;

    while ((*actual) != NULL && (*actual)->info-> numero_conta< num_conta){
        *ant = *actual;
        *actual = (*actual)->next;
    }
    if ((*actual) != NULL && (*actual)->info->numero_conta != num_conta)
        *actual = NULL;
}

void carrega_info_ficheiro(){

    FILE *data;
    Lista_utilizadores temp,anterior,no;
    struct_user aux;
    char pin[10];
    double montante;


    data = fopen ("database.bin" , "rb");
    if (data == NULL) printf("O programa nao localizou o ficheiro database.bin\n");
    else {
        while (fread(&aux,sizeof(struct_user),1,data)){
            temp = (Lista_utilizadores) malloc(sizeof(List_node));
            temp->info = (struct_user *)malloc(sizeof(struct_user));
            *(temp->info) = aux;
            procura_utilizadores(users,temp->info->numero_conta, &anterior, &no);
            if (no == NULL){
                temp->previous = anterior;
                temp->next = NULL;
                anterior->next = temp;

            }
        }
    fclose (data);
    }
    if ((users->next) == NULL){          // Se nada foi carregado do ficheiro para a lista

        printf("Base de Dados vazia, insira exemplo teste:\n\nConta de Funcionario\nNumero de Conta: 1\n");
        printf("PIN : ");
        scanf("%s",pin);
        printf("Montante : ");
        scanf("%lf",&montante);

        insere_lista_utilizadores(users,1,pin,montante,1);

        printf("\nConta de cliente :\nNumero de conta: 2\n");
        printf("PIN : ");
        scanf("%s",pin);
        printf("Montante : ");
        scanf("%lf",&montante);

        insere_lista_utilizadores(users,2,pin,montante,0);

    }

    data = fopen ("notas.bin" , "rb");
    caixa = (dinheiro *) malloc(sizeof(dinheiro));
    caixa->notas5 = 0;
    caixa->notas10 = 0;
    caixa->notas20 = 0;
    caixa->notas50 = 0;
    if (data == NULL) printf("Erro ao abrir ficheiro notas!");
    else {
        fread(caixa,sizeof(dinheiro),1,data);
    }
}


void salva_info_ficheiro(){

    FILE *database;
    database = fopen ("database.bin" , "wb");
    Lista_utilizadores temp =users->next; //descartamos o header

    if (database == NULL)
        printf("\nSEM CONECCAO COM BASE DE DADOS\n");
    else    //SALVA UTILIZADORES
    {
        salva_operacao(15,0.0,actual,actual);
        while(temp != NULL){
            fwrite(temp->info,sizeof(struct_user),1,database);
            temp = temp->next;
        }
        fclose(database);
    }

    database = fopen("notas.bin", "wb");

    if (database == NULL)
        printf("\nSEM CONECCAO COM FICHEIRO NOTAS\n");
    else    //SALVA NOTAS
    {
        fwrite(caixa,sizeof(dinheiro),1,database);
    }
}

Lista_utilizadores destroi_lista_utilizadores (Lista_utilizadores lista){

    Lista_utilizadores temp;

    while (lista_vazia(lista)== 0){
        temp = lista;
        lista = lista->next;
        free(temp->info);
        free(temp);
    }
    free(lista);

    return NULL;
}

int lista_vazia(Lista_utilizadores lista){
    return (lista->next == NULL ? 1 : 0);
}

int conta_contas(Lista_utilizadores lista){

    int n=0;

    lista = lista->next;        //Descartamos o header
    while (lista != NULL){
        lista = lista->next;
        n++;
    }

    return n;
}

void salva_operacao(int n,double quantia,Lista_utilizadores origem,Lista_utilizadores destino){

    FILE *operacoes;
    time_t leituratempo;

    struct tm *tempo;

    time(&leituratempo);

    tempo = localtime(&leituratempo);

    operacoes = fopen("operacoes.txt","a");

    if (operacoes == NULL)
        printf("Sem conecao com ficheiro de operacoes");
    else{
        switch(n){
            case 0:
                fprintf(operacoes,".Login Falhado     | conta: %.5u | %s", actual->info->numero_conta,asctime(tempo));
                break;
            case 1:
                fprintf(operacoes,".Login Efectuado   | conta: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 2:
                fprintf(operacoes,".Conta Bloqueada   | conta: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 3:
                fprintf(operacoes,".Levantamento      | conta: %.5u | montante: %.2lf | %s",actual->info->numero_conta,quantia,asctime(tempo));
                break;
            case 4:
                fprintf(operacoes,".Tranferencia      | conta: %.5u | destino: %.5u |montante: %.2lf | %s",origem->info->numero_conta,destino->info->numero_conta,quantia,asctime(tempo));
                break;
            case 5:
                fprintf(operacoes,".Deposito          | conta: %.5u | montante: %.2lf | %s",actual->info->numero_conta,quantia,asctime(tempo));
                break;
            case 6:
                fprintf(operacoes,".Alteracao PIN     | conta: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 7:
                fprintf(operacoes,".Conta criada      | admin: %.5u | conta: %.5u | %s",actual->info->numero_conta,destino->info->numero_conta,asctime(tempo));
                break;
            case 8:
                fprintf(operacoes,".Conta Eliminada   | admin: %.5u | conta: %.5u | %s",actual->info->numero_conta,destino->info->numero_conta,asctime(tempo));
                break;
            case 9:
                fprintf(operacoes,".Lista Clientes    | admin: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 10:
                fprintf(operacoes,".Lista Operacoes   | admin: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 11:
                fprintf(operacoes,".Alteracao Notas   | admin: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 12:
                fprintf(operacoes,".Desbloqueio Conta | admin: %.5u | conta:%.5u | %s",actual->info->numero_conta,destino->info->numero_conta,asctime(tempo));
                break;
            case 13:
                fprintf(operacoes,".Logout efectuado  | conta: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 14:
                fprintf(operacoes,".TERMINAL DESLIGADO | admin: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 15:
                fprintf(operacoes,".database.txt | notas.txt actualizados | %s",asctime(tempo));
                break;
            case 16:
                fprintf(operacoes,".Deposito falhado notas erradas | conta : %.5u | montante : %.2lf | %s",actual->info->numero_conta,quantia,asctime(tempo));
                break;
            case 17:
                fprintf(operacoes,".Lista operacoes apagada | admin: %.5u | %s",actual->info->numero_conta,asctime(tempo));
                break;
            case 18:
                fprintf(operacoes,".Conta Bloqueada | admin: %.5u | conta:%.5u | %s",actual->info->numero_conta,destino->info->numero_conta,asctime(tempo));
                break;
        }

        fclose(operacoes);
    }

}

void imprime_operacoes(){

    FILE *operacoes;
    char linha[70];

    operacoes = fopen("operacoes.txt","r");

    printf("\n\n-----------------------------------------------------\n");
    if (operacoes != NULL){
        while (fgets(linha,70,operacoes))
            printf("%s",linha);
    printf("\n-----------------------------------------------------\n");
    printf("Listagem de Operacoes efectuada com sucesso!\n");
    fclose(operacoes);
    system("PAUSE");
    }
    else{
        printf("Sem contacto com ficheiro operacoes.txt\n");
        system("PAUSE");
    }
}

void imprime_clientes(){

    Lista_utilizadores temp;

    temp = users->next;

    while(temp != NULL){
        printf("Conta :%d Montante: %.2lf\n",temp->info->numero_conta,temp->info->montante);
        temp = temp->next;
    }


    system("PAUSE");
}

void limpa_operacoes(){

    FILE *operacoes;

    operacoes = fopen("operacoes.txt","w");
    fclose(operacoes);

    printf("Listagem de Operacoes limpa com sucesso!\n");
    system("PAUSE");

}

