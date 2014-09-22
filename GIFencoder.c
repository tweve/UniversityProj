#include "GIFencoder.h"

#include "math.h"
#include "stdlib.h"
#include "stdio.h"
#include "string.h"


//Trabalho realizado por:
//	Igor Cruz 2009111924
//	Carlos Santos 2009108991
//  Inacabado


typedef struct No_St{
    int index;
    char *key;
    struct No_St *prox;
}No;


void LZWCompress(FILE* file, char minCodeSize, char *pixels, int size);
No * cria_lista(No * raiz,char minCodeSize);
void inserirNoFim(No *raiz,char key[]);
No*  procuraLista(No *raiz,char key[]);

// conversão de um objecto do tipo Image numa imagem indexada
imageStruct* GIFEncoder(unsigned char *data, int width, int height) {

	imageStruct* image = (imageStruct*)malloc(sizeof(imageStruct));
	image->width = width;
	image->height = height;

	//converter para imagem indexada
	RGB2Indexed(data, image);

	return image;
}

//conversão de lista RGB para indexada: máximo de 256 cores
void RGB2Indexed(unsigned char *data, imageStruct* image) {
	int x, y, colorIndex, colorNum = 0;
	char *copy;


	image->pixels = (char*)calloc(image->width*image->height, sizeof(char));
	image->colors = (char*)calloc(MAX_COLORS * 3, sizeof(char));


	for (x = 0; x < image->width; x++) {
		for (y = 0; y < image->height; y++) {
			for (colorIndex = 0; colorIndex < colorNum; colorIndex++) {
				if (image->colors[colorIndex * 3] == (char)data[(y * image->width + x)*3] &&
					image->colors[colorIndex * 3 + 1] == (char)data[(y * image->width + x)*3 + 1] &&
					image->colors[colorIndex * 3 + 2] == (char)data[(y * image->width + x)*3 + 2])
					break;
			}

			if (colorIndex >= MAX_COLORS) {
				printf("Demasiadas cores...\n");
				exit(1);
			}

			image->pixels[y * image->width + x] = (char)colorIndex;

			if (colorIndex == colorNum)
			{
				image->colors[colorIndex * 3]	  = (char)data[(y * image->width + x)*3];
				image->colors[colorIndex * 3 + 1] = (char)data[(y * image->width + x)*3 + 1];
				image->colors[colorIndex * 3 + 2] = (char)data[(y * image->width + x)*3 + 2];
				colorNum++;
			}
		}
	}

	//define o número de cores como potência de 2 (devido aos requistos da Global Color Table)
	image->numColors = nextPower2(colorNum);

	//refine o array de cores com base no número final obtido
	copy = (char*)calloc(image->numColors*3, sizeof(char));
	memset(copy, 0, sizeof(char)*image->numColors*3);
	memcpy(copy, image->colors, sizeof(char)*colorNum*3);
	image->colors = copy;

	image->minCodeSize = numBits(image->numColors - 1);
	if (image->minCodeSize == (char)1)  //imagens binárias --> caso especial (pág. 26 do RFC)
		image->minCodeSize++;
}


//determinação da próxima potência de 2 de um dado inteiro n
int nextPower2(int n) {
	int ret = 1, nIni = n;

	if (n == 0)
		return 0;

	while (n != 0) {
		ret *= 2;
		n /= 2;
	}

	if (ret % nIni == 0)
		ret = nIni;

	return ret;
}


//número de bits necessário para representar n
char numBits(int n) {
	char nb = 0;

	if (n == 0)
		return 0;

	while (n != 0) {
		nb++;
		n /= 2;
	}

	return nb;
}


//---- Função para escrever imagem no formato GIF, versão 87a
//// COMPLETAR ESTA FUNÇÃO
void GIFEncoderWrite(imageStruct* image, char* outputFile) {

	FILE* file = fopen(outputFile, "wb");
	char trailer;

	//Escrever cabeçalho do GIF
	writeGIFHeader(image, file);




	//Escrever cabeçalho do Image Block
	// CRIAR FUN‚ÌO para ESCRITA do IMAGE BLOCK HEADER!!!
	//Sugest‹o da assinatura do mŽtodo a chamar:
	//
	writeImageBlockHeader(image, file);

	/////////////////////////////////////////
	//Escrever blocos com 256 bytes no m‡ximo
	/////////////////////////////////////////
	//CODIFICADOR LZW AQUI !!!!
	//Sugest‹o de assinatura do mŽtodo a chamar:
	//
	LZWCompress(file, image->minCodeSize, image->pixels, image->width*image->height);
	fprintf(file, "%c", (char)0);

	//trailer
	trailer = 0x3b;
	fprintf(file, "%c", trailer);

	fclose(file);
}


//--------------------------------------------------
//gravar cabeçalho do GIF (até global color table)
void writeGIFHeader(imageStruct* image, FILE* file) {

	int i;
	char toWrite, GCTF, colorRes, SF, sz, bgci, par;

	//Assinatura e versão (GIF87a)
	char* s = "GIF87a";
	for (i = 0; i < (int)strlen(s); i++)
		fprintf(file, "%c", s[i]);

	//Ecrã lógico (igual à da dimensão da imagem) --> primeiro o LSB e depois o MSB
	fprintf(file, "%c", (char)( image->width & 0xFF));
	fprintf(file, "%c", (char)((image->width >> 8) & 0xFF));
	fprintf(file, "%c", (char)( image->height & 0xFF));
	fprintf(file, "%c", (char)((image->height >> 8) & 0xFF));

	//GCTF, Color Res, SF, size of GCT
	GCTF = 1;
	colorRes = 7;  //número de bits por cor primária (-1)
	SF = 0;
	sz = numBits(image->numColors - 1) - 1; //-1: 0 --> 2^1, 7 --> 2^8
	toWrite = (char) (GCTF << 7 | colorRes << 4 | SF << 3 | sz);
	fprintf(file, "%c", toWrite);

	//Background color index
	bgci = 0;
	fprintf(file, "%c", bgci);

	//Pixel aspect ratio
	par = 0; // 0 --> informação sobre aspect ratio não fornecida --> decoder usa valores por omissão
	fprintf(file, "%c",par);

	//Global color table
	for (i = 0; i < image->numColors * 3; i++)
		fprintf(file, "%c", image->colors[i]);
}

void writeImageBlockHeader(imageStruct* image, FILE* file){

    // Image Separator
    fprintf(file, "%c", (char)(0x2C));
    //Image Left Position 2bytes
    fprintf(file, "%c%c", (char)( 0 ),(char) (0));
	// Image Top Position 2bytes
	fprintf(file, "%c%c", (char)( 0 ),(char) (0));
	// Image Width
    fprintf(file, "%c", (char)( image->width & 0xFF));
	fprintf(file, "%c", (char)((image->width >> 8) & 0xFF));
	// Image Height
	fprintf(file, "%c", (char)( image->height & 0xFF));
	fprintf(file, "%c", (char)((image->height >> 8) & 0xFF));
    // Flags
    fprintf(file, "%c", (char)(0)); //Todas a 0
    // LZW Minimum Color Size
    fprintf(file, "%c", (char)(image->minCodeSize));
}

void LZWCompress(FILE* file, char minCodeSize, char *pixels, int size){

    char ClearCode = pow(2,(minCodeSize));

    int imgpos = 0,blocpos = 0;
    int nbits=0, remainingbits = 0;
    int i = 0;

    char buffer[4096];
    char temp[4096];
    char bloco[256];       // define um bloco
    char c[2];             //  char + \0

    char previous,temp2,temp3,byte,byte2, aux,aux2;

    buffer[0]= '\0';
    temp[0] = '\0';
    c[0] = '\0';

    No * dicionario;
    No * elemento;

    dicionario=cria_lista(dicionario,minCodeSize);

    while(imgpos != size){
        // LZW da Imagem
        c[0] = pixels[imgpos];
        c[1] = '\0';

        strcpy(temp,buffer);        //temp = buffer + c
        strcat(temp,c);

        if (blocpos == 255){
            //escreve bloco no ficheiro e volta a meter posiçao = 0
            i = 0;
            for (i=0;i<256;i++){
                fprintf(file, "%c", (char)( bloco[i] ));
                printf("Bloco[%i] -> %d\n",blocpos,bloco[i]);
            }
            exit (0);
            blocpos = 0;
        }
        if (blocpos == 0){
            // existe informaçao para o bloco e estamos na posiçao 0
            bloco[blocpos] = 255;
            blocpos++;
        }
        if (blocpos == 1 && imgpos == 0){
            // Se é o primeiro bloco escreve na posiçao bloco[1] o clearcode
            bloco[blocpos] = (char)(ClearCode);
            nbits = numBits(ClearCode);
            remainingbits = 8 - nbits;
            blocpos++;
        }
        if (imgpos == size-1){
            // Se é o ultimo elemento da imagem
            //nbits = numBits(buffer);

            bloco[blocpos]  = (char) pow(2,minCodeSize)+1;
        }

        if (elemento= procuraLista(dicionario,temp)!= NULL){
            // Encontra

            strcat(buffer,c);       // se encontra, concatena

        }

        else{
             // Não Encontra temp no dicionario
            elemento = procuraLista(dicionario,buffer);
            // procura o buffer
            if (remainingbits == 0 || remainingbits == 8){
                // se houver 0 ou 8 bits em atraso
                if (remainingbits == 8){
                    // se houver 8, entao passamos para a pos anterior e dizemos que existem zero
                    blocpos--;
                    remainingbits = 0;
                }
                //  Não existem bits do byte anterior para serem escritos
                bloco[blocpos]  = elemento->index;
                remainingbits = 8-minCodeSize;
                blocpos++;
                inserirNoFim(dicionario,temp);
                strcpy(buffer,c);
            }
            else if (remainingbits >0 || remainingbits<8 ){
                // Se existem bits restantes
                if (blocpos == 1)                       // se for na posiçao 1 do bloco
                    previous = bloco[255];              // vai buscar o ultimo elemento do bloco anterior para previous
                else                                    //caso contrario
                    previous = bloco[(blocpos-1)];      //vai buscar o elemento anterior

                temp2 = bloco[blocpos] >> remainingbits;
                temp3 = bloco[blocpos] << (8-remainingbits);  //cria variavel temporaria com os bits na posiçao certa para o indice anterior

                // de seguida utilizar mascara para escolher os necessarios
                byte = previous;
                aux =  (char)11111111;        // mascara para retirar os bits que precisamos preencher
                aux2 = (char)11111111;

                aux = aux >> remainingbits;     // mascara para o byte anterior (mete a 1 o que precisamos dele)
                aux2 = aux2 <<(8-remainingbits);  // mascara para o byte actual (mete a 1 o que precisamos dele)

                byte = previous & aux;      // liga os necessarios do anterior
                byte2 = temp3 | aux2;       // liga os necessarios do temp3

                byte = byte | byte2;

                bloco[blocpos-1] = byte;
                bloco[blocpos]= temp2;
                blocpos++;

                inserirNoFim(dicionario,temp);
                strcpy(buffer,c);
            }

        }
        imgpos++;           //avança uma posiçao na imagem
    }
}

No * cria_lista(No * raiz,char minCodeSize){     /*Função do tipo apontador para lista, i.e., o tipo de função tem de ser igual ao tipo que retorna*/

    raiz = (No *)malloc(sizeof(No));   /*Aloca memória do tamanho de uma célula*/

    if(raiz == NULL) exit(0);    /*Se não alocar memória significa que não há memoria disponível, logo deve sair*/

    raiz->index = (2^minCodeSize)+1; // no inicio o indice vai ser o end of information depois é incrementado, quando encontrar um valor
    raiz->prox = NULL;

    return (raiz);
}

void inserirNoFim(No *raiz,char key[]){
    No *novo;
    novo = (No *) malloc(sizeof(No));
    if(novo == NULL) exit(0);

    No *temp = raiz;
    while(temp->prox != NULL);
        temp = temp->prox;

    novo->index =  temp->index+1;
    novo->key = malloc(sizeof(key));
    strcpy(novo->key,key);
    novo->prox = NULL;

    if(raiz == NULL){
        raiz = novo;
    }else{
        temp->prox = novo;
    }
}

No*  procuraLista(No *raiz,char key[]){
    No *temp = raiz;
    while(temp->prox != NULL)
        if (strcmp(temp->key,key)==0)
            return temp;
    return NULL;
}
