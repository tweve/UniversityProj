typedef struct
{
    // Estrutura de um utilizador
    unsigned int numero_conta;              // Numero de conta
    char PIN[4];                       // Pin da conta
    double montante;                  // variavel que armazena a quantia de dinheiro
    short nivel;                     // 1 - funcionario | 0 - utilizador | -1 - cartao bloqueado
}struct_user;

typedef struct lnode *Lista_utilizadores;
typedef struct lnode {
  struct_user *info;
  Lista_utilizadores next;
  Lista_utilizadores previous;
} List_node;

typedef struct{
    // Estrutura da caixa forte da CBA
    // armazena o numero de notas de cada tipo
    unsigned short notas50;
    unsigned short notas20;
    unsigned short notas10;
    unsigned short notas5;

}dinheiro;
