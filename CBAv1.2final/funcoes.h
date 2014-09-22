// Funcoes estruturais do programa
int login(Lista_utilizadores users);
void levanta_quantia(Lista_utilizadores destino,double quantia);
int tranfere(unsigned int conta_origem,unsigned int conta_dest, double quantia);
int conta_contas(Lista_utilizadores users);

// Menus e Interacçao com utilizador / administrador
void mostra_menu_admin(void);
void mostra_menu_utilizador(void);
int menu_levantamentos();
int menu_tranferencias();
int menu_depositos();
void menu_alterapin();
void menu_cria_conta();
int menu_elimina_conta();
void menu_lista_clientes();
void menu_lista_operacoes();
void menu_carrega_notas();
void menu_bloqueia();
void menu_desbloqueia();
void menu_imprimir_conta();
void imprime_clientes();
void menu_sair();

// Funcoes para edicao de listas ligadas
Lista_utilizadores cria_lista_utilizadores (void);
void insere_lista_utilizadores(Lista_utilizadores users, unsigned int a, char b[4],double  c, short d);
void procura_utilizadores( Lista_utilizadores users , int num_conta, Lista_utilizadores *anterior, Lista_utilizadores *actual);
Lista_utilizadores destroi_lista_utilizadores (Lista_utilizadores lista);
int lista_vazia(Lista_utilizadores lista);

// Funcoes de interacçao com ficheiros
void carrega_info_ficheiro();
void salva_info_ficheiro();
void imprime_operacoes();
void salva_operacao(int n,double quantia,Lista_utilizadores origem,Lista_utilizadores destino);
void limpa_operacoes();
