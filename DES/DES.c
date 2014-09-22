/*Author: Rui Pedro Paiva
Teoria da Informação, LEI, 2008/2009*/


/*

//Trabalho realizado por:
//	Igor Cruz 2009111924
//	Carlos Santos 2009108991

*/

#include "DES.h"

unsigned long long function(unsigned long long Ri,unsigned long long Ki);
unsigned long long functiong(unsigned long long U);
unsigned long long functiong2(unsigned long long U);
unsigned long long build_parity(unsigned long long U);
dimensao =  0;
//função para encriptação
int DES (char* inFileName, unsigned long long key)
{
	return DESgeneral(inFileName, key, 0);
}

//função para decriptação
int unDES (char* inFileName, unsigned long long key)
{
	return DESgeneral(inFileName, key, 1);
}

//função geral para encriptação (type = 0) e decriptação (type = 1) de um ficheiro
int DESgeneral (char* inFileName, unsigned long long key, int type)
{
	FILE* DESInFile;
	unsigned char* inByteArray;
	long inFileSize;
	unsigned char* crpByteArray;
	char* outFileName;
	int write;
	char response;
	struct stat stFileInfo;
	FILE* DESOutFile;
	unsigned char* hash;
	char suf[5];


	//abrir ficheiro e ler tamanho
	DESInFile = fopen(inFileName, "rb");
	if (DESInFile == NULL)
	{
		printf("Error opening file for reading. Exiting...\n");
		return 1;
	}
	fseek(DESInFile, 0L, SEEK_END);
	inFileSize = ftell(DESInFile);  //ignore EOF
	fseek(DESInFile, 0L, SEEK_SET);


	//ler ficheiro inteiro para array inByteArray
	inByteArray = (unsigned char*) calloc(inFileSize, sizeof(unsigned char));
	fread(inByteArray, 1, inFileSize, DESInFile);


	//criar assinatura
	if (type == 0)  //encripta‹o
	{
		/******* ADICIONAR CîDIGO:
		 implementar ˆ fun‹o:
		 unsigned char* signature(unsigned char* inByteArray, long dim, unsigned long long key)  // ver abaixo
		 e adicionar hash aos dados
		 ***********************/

        hash = signature(inByteArray,inFileSize);  // ver abaixo
        //hash tem 16 bytes
        inByteArray = (unsigned char*) realloc(inByteArray,(inFileSize+16)*sizeof(unsigned char));
        int k =0;
		for (k=0;k<16;k++){
            inByteArray[inFileSize+k] = hash[k];
		}
		inFileSize=inFileSize+16;
	}


	//encriptar dados e assinatura no array
	crpByteArray = encryptDES(inByteArray, inFileSize, key, type);

	//flush do crpByteArray para ficheiro
	//nome do ficheiro de saída
	if (type == 0)  //encriptação
	{
		outFileName = (char*) calloc(strlen(inFileName) + 5, sizeof(char));
		strcpy(outFileName, inFileName);
		strcat(outFileName, ".DES");
	}
	else  //decriptação
	{
		strcpy(suf, &inFileName[strlen(inFileName) - 4]);
		if (strcmp(suf, ".DES") == 0)
		{
			outFileName = (char*) calloc(strlen(inFileName) + 5, sizeof(char));
			strcpy(outFileName, "DES_");
			strcat(outFileName, inFileName);
			outFileName[strlen(outFileName) - 4] = 0;
		}
		else
		{
			outFileName = (char*) calloc(14, sizeof(char));
			strcpy(outFileName, "DES_decrypted");
		}

	}


	//verificar assinatura
	if (type == 1)
	{
		/******* ADICIONAR CîDIGO:
		 implementar ˆ fun‹o:
		 int checkSignature(unsigned char* inByteArray, unsigned char* hash)  // ver abaixo
		 e retirar hash aos dados
		 abortar desencripta‹o caso a verifica‹o da assinatura n‹o passe no teste
		 ***********************/

        inFileSize= inFileSize-16;
        dimensao = inFileSize;
		hash = (unsigned char*) malloc(16* sizeof(unsigned char));		//128 bits, 16 espaчos de 1byte

        int k =0;
		for (k=0;k<16;k++){

		    hash[k]=crpByteArray[inFileSize+k];
		}
		// retiramos a parte da hash dos dados do ficheiro
		inByteArray = (unsigned char*) realloc(inByteArray,inFileSize*sizeof(unsigned char));

        if (checkSignature(crpByteArray,hash) ==0 ){
            return 0;
        }


	}

	//criar ficheiro
	write = 1;
	if(stat(outFileName, &stFileInfo) == 0) //see if file already exists
	{
		printf ("File already exists. Overwrite (y/n)?: ");
		response = getchar();
		if (response == 'n')
			write = 0;
		printf("\n");
		fflush(stdin);
	}

	if (write)
	{
		DESOutFile = fopen(outFileName, "wb");
		if (DESOutFile == NULL)
		{
			printf("Error opening file for writing!!! Exiting...\n");
			return -1;
		}
		fwrite(crpByteArray, 1, inFileSize, DESOutFile);
		fclose(DESOutFile);
	}

	//finalizações
	free(inByteArray);
	free(outFileName);
	free(crpByteArray);
	fclose(DESInFile);

	return 0;
}


// função para encriptação/decriptação de dados no array inByteArray, de dimensão dim
unsigned char* encryptDES(unsigned char* inByteArray, long dim, unsigned long long key, int type)
{
	unsigned long long subKeys[16];
	unsigned char* outByteArray;
	unsigned long long plain, cipher;
	int i, j;


	//obtém sub-keys (16 de comprimento 48)
	/**** ADICIONAR CÓDIGO PARA A FUNÇÃO DESKEYSCHEDULE (ABAIXO) ********/
	DESKeySchedule(key, subKeys);


	if (type == 1) //decrypt --> inverter subKeys
	{
	    unsigned long long aux[16];

		/**************** ADICIONAR CÓDIGO ****************************/
        int i = 0;
        for (;i<16;i++){
            aux[i]= subKeys[15-i];
            }
        for (i=0;i<16;i++){
            subKeys[i]= aux[i];

            }
	}

	outByteArray = (unsigned char*) calloc(dim, sizeof(unsigned char));
	i = 0;
	plain = 0;
	while (i < dim)
	{
		plain = 0;
		j = i;
		while (j < i + 8 && j < dim)
		{
 			plain = plain | ((unsigned long long)inByteArray[j] << (64 - 8*(j-i+1)));
			j++;
		}

		//determina cifra
		if (j - i == 8)  //ficheiro é múltiplo de 8 bytes
			/**** ADICIONAR CÓDIGO PARA A FUNÇÃO ENCRYPTDESPLAIN (ABAIXO) ********/
			cipher = encryptDESplain(plain, subKeys);
		else
			cipher = plain;


		//guarda cifra no array de saída
		j = i;
		while (j < i + 8 && j < dim)
		{
			outByteArray[j] = (unsigned char) (cipher >> (56 - 8*(j-i)) & (0xFF));
			j++;
		}

		i = j;
	}

	return outByteArray;
}


/************************************************************************************/
/***************************** ADICIONAR CóDIGO *************************************/
/************************************************************************************/


// função para encriptação de uma mensagem de 64 bits (plain), com base nas subKeys
//devolve a mensagem cifrada
unsigned long long encryptDESplain(unsigned long long plain, unsigned long long* subKeys)
{
    int i;
    unsigned long long Li=0,Ri=0, aux=0,final;
    unsigned long long temp,mascEsc=0xFFFFFFFF00000000LL,mascDir=0x00000000FFFFFFFFLL;

    # ifdef DEBUGDES
   // printf("entrada -> %llx |",plain);
    #endif

    short IP[64]={
        58,50,42,34,26,18,10,2,
        60,52,44,36,28,20,12,4,
        62,54,46,38,30,22,14,6,
        64,56,48,40,32,24,16,8,
        57,49,41,33,25,17,9,1,
        59,51,43,35,27,19,11,3,
        61,53,45,37,29,21,13,5,
        63,55,47,39,31,23,15,7
    };

    short IP_1[64]={
        40,8,48,16,56,24,64,32,
        39,7,47,15,55,23,63,31,
        38,6,46,14,54,22,62,30,
        37,5,45,13,53,21,61,29,
        36,4,44,12,52,20,60,28,
        35,3,43,11,51,19,59,27,
        34,2,42,10,50,18,58,26,
        33,1,41,9,49,17,57,25
    };


    for (i=0;i<64;i++){
        aux = aux | ( ((plain >> (64-IP[i]))&1)<<(63-i));
    }

    Li = aux & mascEsc;
    Ri = aux & mascDir;
    Li = Li>>32;

    # ifdef DEBUGDES
    printf("\nL0 -> %llx |",Li);
    printf("\nR0 -> %llx |",Ri);
    #endif

    for (i=1;i<=16;i++){
        temp = Li;  // guarda numa variável temporária
        Li = Ri;
        Ri = temp ^ function(Ri,subKeys[i-1]); // (Li-1) XOR f(Ri-1,Ki)
    }
    aux = 0,final = 0;
    Ri = Ri & 0xFFFFFFFF;
    aux = Ri<<32 | Li;

    # ifdef DEBUGDES
    printf("L16,R16 -%llx- \n",aux);
    #endif

    for (i=0;i<64;i++){
        final = final | ( ((aux >> (64-IP_1[i]))&1)<<(63-i));
    }
    # ifdef DEBUGDES
    printf("cipher -%llx- \n",final);
    #endif
    return final;
}

unsigned long long function(unsigned long long Ri,unsigned long long Ki){

    unsigned long long aux=0,aux2=0,final=0;
    unsigned Si;
    int i;
    short c=0,l=0;

    # ifdef DEBUGDES
    printf("\nRi -> %llx |",Ri);
    printf("\nKey -> %llx |",Ki);
    #endif

    short S1[4][16]={
        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
        {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
        {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
        {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
    };

    short S2[4][16]={
        {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
        {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
        {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
        {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
    };

    short S3[4][16]={
        {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
        {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
        {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
        {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
    };

    short S4[4][16]={
        {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
        {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
        {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
        {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
    };

    short S5[4][16]={
        {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
        {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
        {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
        {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
    };

    short S6[4][16]={
        {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
        {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
        {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
        {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
    };

    short S7[4][16]={
        {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
        {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
        {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
        {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
    };

    short S8[4][16]={
        {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
        {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
        {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
        {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
    };

    short E[48]={
        32,1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9,10,11,12,13,
        12,13,14,15,16,17,
        16,17,18,19,20,21,
        20,21,22,23,24,25,
        24,25,26,27,28,29,
        28,29,30,31,32,1
    };

    short P[32]={
        16,7,20,21,
        29,12,28,17,
        1,15,23,26,
        5,18,31,10,
        2,8,24,14,
        32,27,3,9,
        19,13,30,6,
        22,11,4,25
    };

    //E (Ri)

    for (i=0;i<48;i++){
        aux = aux | ( (((Ri >> 32-E[i])&1)<<(47-i)));

    }


    # ifdef DEBUGDES
    printf("T -> %llx |",aux);
    #endif
    //XOR com a subkey

    aux = aux ^ Ki;
    aux2=0;

    //# ifdef DEBUGDES
    //printf("T' -> %llx |",aux);
    //#endif

    // Aplicar funçao S aos blocos (8X6)

    for (i=1;i<9;i++){

        Si = (aux >>48-(6*i))& 63;
        c = (Si & 30)>>1;

        l = 2*(Si>>5 & 1);
        l =l+ (Si&1);

        # ifdef DEBUGDES
        printf("\nB%d -> %.2x \n",i,Si);
        printf("\nl -> %d, c -> %d \n",l,c);
        #endif

        switch (i){
        case 1:
        Si = S1[l][c];
        break;
        case 2:
        Si = S2[l][c];
        break;
        case 3:
        Si = S3[l][c];
        break;
        case 4:
        Si = S4[l][c];
        break;
        case 5:
        Si = S5[l][c];
        break;
        case 6:
        Si = S6[l][c];
        break;
        case 7:
        Si = S7[l][c];
        break;
        case 8:
        Si = S8[l][c];
        break;
        }
        # ifdef DEBUGDES
    printf("-%x- \n",Si);
    #endif
        aux2 = aux2 | (Si&0xF);
        if (i !=8)aux2 = aux2 << 4;

    }
    # ifdef DEBUGDES
    printf("-%llx- \n",aux2);
    #endif
    final =0;
    for (i=0;i<32;i++){
        final = final | ( (( aux2 >> (32-P[i]))&1)<<(31-i));
    }
    # ifdef DEBUGDES
    printf("-%llx- \n",final);
    #endif


    return final;
}

// função para gerar sub-keys (uma chave para cada uma das 16 iterações)
void DESKeySchedule(unsigned long long key, unsigned long long* subKeys)
{
    unsigned long long Ci=0,Di=0,temp=0,aux = 0;
    int i =0,j = 0;

    short P1[56]={
        57,49,41,33,25,17,9,
        1,58,50,42,34,26,18,
        10,2,59,51,43,35,27,
        19,11,3,60,52,44,36,
        63,55,47,39,31,23,15,
        7,62,54,46,38,30,22,
        14,6,61,53,45,37,29,
        21,13,5,28,20,12,4
    };

    short PC2[48]={
        14,17,11,24,1,5,
        3,28,15,6,21,10,
        23,19,12,4,26,8,
        16,7,27,20,13,2,
        41,52,31,37,47,55,
        30,40,51,45,33,48,
        44,49,39,56,34,53,
        46,42,50,36,29,32
    };

    for (i=0;i<56;i++){
        aux = ( ((key >> (64-(P1[i])))&1));
        temp=(temp<<1);
        temp= temp | aux;

    }
    Ci = (temp>>28)&0xFFFFFFF;

    Di = temp & 0xFFFFFFF;

        #ifdef DEBUG
        printf("C0-> %llX ;\n",Ci);
        printf("D0-> %llX ;\n",Di);
        #endif

    for (i=1;i<=16;i++){
        subKeys[i-1]=0;
        if (i == 1 || i ==2 || i== 9 || i == 16){

            Ci = (Ci << 1) | (Ci >> (28 - 1));
            Di = (Di << 1) | (Di >> (28 - 1));
            Ci = Ci & 0x0FFFFFFF;
            Di = Di & 0x0FFFFFFF;
            #ifdef DEBUG
            printf("C%d-> %llX ;\n",i,Ci);
            printf("D%d-> %llX ;\n",i,Di);
            #endif
        }
        else {
            Ci = (Ci << 2) | (Ci >> (28 - 2));
            Di = (Di << 2) | (Di >> (28 - 2));
            Ci = Ci & 0x0FFFFFFF;
            Di = Di & 0x0FFFFFFF;
        }

    // neste ponto ja temos Ci Di
    // necessitamos concatenar para criar as chaves

    temp = Ci<< 28| Di;
    #ifdef DEBUG
    printf("Escolha Permutada->%llX;\n",temp);
    #endif

    #ifdef DEBUG
    printf("Subkey %d ->%llX ;\n",i,subKeys[i]);
    #endif

    for (j=0;j<48;j++){
        subKeys[i-1]= subKeys[i-1]| ( ((temp >> (56-PC2[j]))&1)<<47-j);
    }

    #ifdef DEBUG
    printf("Subkey %d ->%llX ;\n",i,subKeys[i-1]);
    #endif

    }
    // todos os resultados coincidem com a tabela

}

// fun‹o para cria‹o de de uma hash a partir dos dados do ficheiro, usando MDC-4
unsigned char* signature(unsigned char* inByteArray, long dim){

	unsigned char* hash = (unsigned char*) malloc(16* sizeof(unsigned char));		//128 bits, 16 espaчos de 1byte
	unsigned long long IV = 0x5252525252525252LL;
	unsigned long long IV2 = 0x2525252525252525LL;

	unsigned long long X1,X2,Ei,Ei2,out1,out2,ki,ki2,keytrans,keytrans2,kpL,kpR,Ci,Ci2,ji,ji2,Gi,Gi2,Hi,Hi2;
	Gi = IV;
	Gi2 = IV2;

	unsigned long long subKeysIV[16];
	unsigned long long subKeysIV2[16];
	int i=0,j=0;

	while(i<dim){

		X1 = 0;
		X2 = 0;



		while (j < i + 8 && j < dim)
		{
 			X1 = X1 | ((unsigned long long)inByteArray[j] << (64 - 8*(j-i+1)));
			j++;
		}
		i = j;
		while (j < i + 8 && j < dim)
		{
 			X2 = X2 | ((unsigned long long)inByteArray[j] << (64 - 8*(j-i+1)));
			j++;
		}
		i = j;

		//MDC-2

		ki = functiong(Gi);
		ki2 = functiong2(Gi2);

		kpL = build_parity(ki);
		kpR = build_parity(ki2);

		DESKeySchedule(kpL, subKeysIV);
		DESKeySchedule(kpR, subKeysIV2);

		Ei = encryptDESplain(X1, subKeysIV);
		Ei2 = encryptDESplain(X2, subKeysIV2);

		Ci = X1^Ei;
		Ci2 = X2^Ei2;

		Hi = (Ci & 0xFFFFFFFF00000000LL) | (Ci2 & 0xFFFFFFFF);
		Hi2 = (Ci2 & 0xFFFFFFFF00000000LL) | (Ci & 0xFFFFFFFF);

        //Segundo MDC 2

		X1 = Gi2;
		X2 = Gi;

		ji = functiong(Hi);
		ji2 = functiong2(Hi2);

		kpL = build_parity(ji);
		kpR = build_parity(ji2);

		DESKeySchedule(kpL, subKeysIV);
		DESKeySchedule(kpR, subKeysIV2);

		Ei = encryptDESplain(X1, subKeysIV);
		Ei2 = encryptDESplain(X2, subKeysIV2);
		//XOR
		Ci = X1^Ei;
		Ci2 = X2^Ei2;

		Gi = (Ci & 0xFFFFFFFF00000000LL) | (Ci2 & 0xFFFFFFFF);
		Gi2 = (Ci2 & 0xFFFFFFFF00000000LL) | (Ci & 0xFFFFFFFF);

		//printf("out1: %llx | ",Gi);
		//printf("out2: %llx\n",Gi2);
	}

    // escrever ultima hash como conjunto de caracteres
	int k=0;
	for (;k < 16;k++){
		if(k<8)
			hash[k] = (unsigned char) (Gi >> (56 - 8*k) & (0xFF));
		else
			hash[k] = (unsigned char) (Gi2 >> (120 - 8*k) & (0xFF));
	}

	return hash;

}

unsigned long long functiong(unsigned long long U){


    unsigned long long temp=0;
    unsigned long long mascara=0xFE00000000000000;

    short pos = 0;
    short iteracao=0;

    // eliminar as oitavas posiçoes
    for(;iteracao<8;iteracao++){
        temp = temp | ((U & mascara)<<iteracao);
        mascara = mascara >> 8;
    }
    temp = temp | 0x4000000000000000;
    temp = temp & 0xDFFFFFFFFFFFFFFF;
    temp = temp >> 8;
    return temp;

}

unsigned long long functiong2 (unsigned long long U){

    unsigned long long temp=0;
    unsigned long long mascara=0xFE00000000000000;

    short pos = 0;
    short iteracao=0;

    // eliminar as oitavas posiçoes
    for(;iteracao<8;iteracao++){
        temp = temp | ((U & mascara)<<iteracao);
         //printf("aux %llx",temp);
        mascara = mascara >> 8;
    }
    temp = temp | 0x2000000000000000;
    temp = temp & 0xBFFFFFFFFFFFFFFF;
    temp = temp>>8;
    return temp;

}

unsigned long long build_parity(unsigned long long U){

    short i=0,k=0,counter=0;
    unsigned long long masc = 0x7F;
    unsigned long long temp=0;
    unsigned long long aux=0;
    unsigned long long aux2=0;

    for (i=0;i<8;i++){
        aux = U & masc;
        aux = aux <<i+1;
        counter =0;
        for (k=0;k<64;k++){
            counter += (aux >> k) & 1;
        }

        if (counter%2==0){
            aux2 = 0;
            aux2 = aux2 <<(i*8);
            aux = aux|aux2;
        }
        else{
            aux2 = 1;
            aux2 = aux2 <<(i*8);
            aux = aux|aux2;
        }

        temp = temp | aux;

        masc = masc << 7;

        }
     return temp;

}

//fun‹o para verifica‹o da assinatura: verificar se a hash criada a partir dos dados Ž igual ˆ hash recebida
int checkSignature(unsigned char* inByteArray, unsigned char* hash){
    int i = 0;
	unsigned char* hash2;
	hash2 = signature(inByteArray,dimensao);

	for(i=0;i<16;i++){
		if(hash2[i]!=hash[i]){
			printf("Dados Corrompidos!");
			return 0;
		}
	}
	return 1;
}
