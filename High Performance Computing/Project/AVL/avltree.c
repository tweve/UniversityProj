#include "avltree.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fatal.h"
 
struct AvlNode
{
    ElementType Element;
    AvlTree  Left;
    AvlTree  Right;
    int      Height;
};
 
AvlTree MakeEmpty( AvlTree T )
{
    if( T != NULL )
    {
        MakeEmpty( T->Left );
        MakeEmpty( T->Right );
        free( T );
    }
    return NULL;
}

int compareLeaf(int * vec1, int * vec2){
	int i;

	for (i = 0;i<RULE_SIZE;i++){
		if (vec1[i] < vec2[i])
			return -1;
		else if (vec1[i] > vec2[i])
			return 1;
	}
	return 0;
}

int compare(int * vec1, int * vec2){
	int i;

	for (i = 0;i<INPUT_SIZE;i++){
		if (vec1[i]==0 || vec2[i]==0){
			continue;
}
		if (vec1[i] < vec2[i]){
			return -1; 
}
		else if (vec1[i] == vec2[i]){
			continue;
}
		else{
			return 1;
}	
	}
	return 0;
}

void print_rule(int * input, int * rule){
	int i = 0;
	for (;i<INPUT_SIZE;i++){
		printf("%d,", input[i]);
	}
	printf("%d\n", rule[RULE_SIZE-1]);
}

void Find( ElementType X, AvlTree T )
{
    if( T == NULL ){
    }
    else{

		if (compare(T->Element,X) == 0){
		
			print_rule(X,T->Element);
			if (T->Right != NULL)
 				Find( X, T->Right );
			if (T->Left != NULL)
				Find( X, T->Left );

		}
		if( compare(T->Element,X) == -1 ){
			if (T->Right != NULL)
		    		Find( X, T->Right );
			if (T->Left != NULL)
   				Find( X, T->Left );
		}
		else if( compare(T->Element,X) == 1 ){
			if (T->Left != NULL)
   				Find( X, T->Left );
			if (T->Right != NULL)
   				Find( X, T->Right );
		}
	}
}

static int Height( Position P )
{
    if( P == NULL )
        return -1;
    else
        return P->Height;
}
 
static int Max( int Lhs, int Rhs )
{
    return Lhs > Rhs ? Lhs : Rhs;
}
 
/* This function can be called only if K2 has a left child */
/* Perform a rotate between a node (K2) and its left child */
/* Update heights, then return new root */
 
static Position SingleRotateWithLeft( Position K2 )
{
    Position K1;
 
    K1 = K2->Left;
    K2->Left = K1->Right;
    K1->Right = K2;
 
    K2->Height = Max( Height( K2->Left ), Height( K2->Right ) ) + 1;
    K1->Height = Max( Height( K1->Left ), K2->Height ) + 1;
 
    return K1;  /* New root */
}
 
/* This function can be called only if K1 has a right child */
/* Perform a rotate between a node (K1) and its right child */
/* Update heights, then return new root */
 
static Position SingleRotateWithRight( Position K1 )
{
    Position K2;
 
    K2 = K1->Right;
    K1->Right = K2->Left;
    K2->Left = K1;
 
    K1->Height = Max( Height( K1->Left ), Height( K1->Right ) ) + 1;
    K2->Height = Max( Height( K2->Right ), K1->Height ) + 1;
 
    return K2;  /* New root */
}
 
/* This function can be called only if K3 has a left */
/* child and K3's left child has a right child */
/* Do the left-right double rotation */
/* Update heights, then return new root */
 
static Position DoubleRotateWithLeft( Position K3 )
{
    /* Rotate between K1 and K2 */
    K3->Left = SingleRotateWithRight( K3->Left );
 
    /* Rotate between K3 and K2 */
    return SingleRotateWithLeft( K3 );
}
 
/* This function can be called only if K1 has a right */
/* child and K1's right child has a left child */
/* Do the right-left double rotation */
/* Update heights, then return new root */
 
static Position DoubleRotateWithRight( Position K1 )
{
    /* Rotate between K3 and K2 */
    K1->Right = SingleRotateWithLeft( K1->Right );
 
    /* Rotate between K1 and K2 */
    return SingleRotateWithRight( K1 );
}
 
AvlTree Insert( ElementType X, AvlTree T )
{
    if( T == NULL )
    {
        /* Create and return a one-node tree */
        T = malloc( sizeof( struct AvlNode ) );
	T->Element = malloc(sizeof(int)*RULE_SIZE);        
	if( T == NULL )
            FatalError( "Out of space!!!" );
        else
        {
            memcpy(T->Element,X,RULE_SIZE*sizeof(int));
	    	T->Height = 0;
            T->Left = T->Right = NULL;
        }
    }
    else
        if(compareLeaf(X, T->Element)==-1 )
        {
            T->Left = Insert( X, T->Left );
            if( Height( T->Left ) - Height( T->Right ) == 2 )
                if( compareLeaf(X,T->Left->Element)==-1 )
                    T = SingleRotateWithLeft( T );
                else
                    T = DoubleRotateWithLeft( T );
        }
        else
            if( compareLeaf(X,T->Element)==1 )
            {
			
                T->Right = Insert( X, T->Right );
                if( Height( T->Right ) - Height( T->Left ) == 2 )
                    if( compareLeaf(X,T->Right->Element)==1 )
                        T = SingleRotateWithRight( T );
                    else
                        T = DoubleRotateWithRight( T );
            }
            /* Else X is in the tree already; we'll do nothing */
 
            T->Height = Max( Height( T->Left ), Height( T->Right ) ) + 1;
            return T;
}

 
ElementType Retrieve( Position P )
{
    return P->Element;
}
