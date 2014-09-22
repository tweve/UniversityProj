

/*---------------------

Autores:

-> Igor Nelson Garrido da Cruz        - 2009111924

-> Carlos Manuel Fernandes dos Santos - 2009108991 not

Tempo total dedicado ao trabalho: 25 horas

----------------------*/

#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/wait.h>
#include <sys/ipc.h> 
#include <sys/shm.h>
#include <sys/fcntl.h>
#include <sys/mman.h>
#include <math.h>
#include <sys/time.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <fcntl.h>
#include <semaphore.h>
#include <limits.h>	
#include "timeprofiler.h"
#include "ppmtools.h"

#define nprocessos 2
#define	FILE_MODE	(S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH)
#define DEBUG 



typedef struct {
	sem_t sem_mutex1; 
	sem_t sem_mutex2;
	sem_t sem_mutex3;
	sem_t sem_remaining_lines;
	int total_lines;
	int next_line;
	int processed_lines;

} mem_struct;

sem_t *mutex1, *mutex2, *mutex3, *sem_remaining_lines;
header* h;
char *src;
char *dst;
int size;

void init();
void terminate();
void worker(int i );
int get_stat(int fdin);
int write_pos, read_pos;
int shmid;
int* buf;
mem_struct *sh_mem;
int worker_id[nprocessos];
int offset;

void init() {

	

	shmid = shmget(IPC_PRIVATE, sizeof(mem_struct), IPC_CREAT|0700);
	if (shmid < 1)
		exit(0);
	sh_mem = (mem_struct*) shmat(shmid, NULL, 0);
	if (sh_mem < (mem_struct*) 1)
		exit(0);  
	
	#ifdef DEBUG
		puts("Aloca memoria partilhada\n");
	#endif

	sem_init(&sh_mem->sem_remaining_lines, 1,h->height);
	sem_remaining_lines = &sh_mem->sem_remaining_lines;
	sem_init(&sh_mem->sem_mutex1, 1, 1);
	mutex1 = &sh_mem->sem_mutex1;
	sem_init(&sh_mem->sem_mutex2, 1, 0);
	mutex2 = &sh_mem->sem_mutex2;
	sem_init(&sh_mem->sem_mutex3, 1, 1);
	mutex3 = &sh_mem->sem_mutex3;

	#ifdef DEBUG
		puts("Cria semaforos com sucesso!\n");
	#endif
}

void terminate() {
	sem_close(mutex1);
	sem_close(mutex2);
	sem_close(mutex3);
	sem_close(sem_remaining_lines);
	
	#ifdef DEBUG
		puts("Fecha semaforos com sucesso!\n");
	#endif

	shmctl(shmid, IPC_RMID, NULL);
	#ifdef DEBUG
		puts("Desaloca memoria partilhada com sucesso!\n");
	#endif

}
int main(int argc, char *argv[])
{	
	
	char fich[20];
	int fdin,fdout;
	double start, stop, startms, stopms;
	int i;

	if(argc < 3)
	{
		printf("Incorrect usage.\nPlease use \"./invert input_filename.ppm output_filename.ppm\"\n");
		exit(0);
	}
	
	//start timer
	start = getCurrentTimeMicro();
	startms = getCurrentTimeMili();
	
	
	#ifdef DEBUG
		puts("Inicia file descriptor input\n");
	#endif

	if ( (fdin = open(argv[1], O_RDONLY)) < 0)
	{
		printf("Could not open input file\n");
		exit(1);
	}

	size = get_stat(fdin);
	
	#ifdef DEBUG
		puts("Encontra o tamanho do ficheiro\n");
	#endif


	if ( (src = mmap(0, size, PROT_READ, MAP_FILE | MAP_SHARED, fdin, 0)) == (caddr_t) -1)
	{
		fprintf(stderr,"mmap error for input\n");
		exit(1);
	}

	
	#ifdef DEBUG
		puts("Mapeia o ficheiro de input em memoria\n");
	#endif

	puts("Imagem copiada para memoria com sucesso!");

	printf("Opening output file [%s]\n",argv[2]);
	
	strcpy(fich,argv[2]);
	
	if ( (fdout = open(fich, O_RDWR | O_CREAT | O_TRUNC,FILE_MODE)) < 0)
	{
		fprintf(stderr,"can't creat %s for writing\n", fich);
		exit(1);
	}

	if (lseek(fdout, size - 1, SEEK_SET) == -1)
	{
		fprintf(stderr,"lseek error\n");
		exit(1);
	}
	if (write(fdout, "", 1) != 1)
	{
		fprintf(stderr,"write error\n");
		exit(1);
	}
	
	#ifdef DEBUG
		puts("Inicia file descriptor output\n");
	#endif

	dst = mmap(0, size, PROT_READ | PROT_WRITE,MAP_SHARED,fdout, 0);
		
	#ifdef DEBUG
		puts("Mapeia ficheiro de output em memoria\n");
	#endif

	printf("Getting header\n");

	h = malloc(sizeof(header));

	sscanf(src,"%c%c\n%d %d\n%d\n",&h->type[0],&h->type[1],&h->width,&h->height,&h->depth);
	
	printf("Got file Header: %s - %u x %u - %u\n", h->type, h->width, h->height, h->depth);

	printf("Saving header to output file\n");	
	
	memcpy(dst,src,size);

	int counter,index;

	for (counter=0, index=0; counter<3;index++)
	{
		if (src[index]=='\n')
			++counter;
	} 	

	offset=index+4;

	dst = dst+offset;

	init();			//inicia semaforos

	sh_mem->total_lines = h->height;

	for (i=0;i<nprocessos;i++)			//Cria N processos 
		if(fork()==0)
			worker(i);

	printf("Starting work\n");
	sem_wait(mutex2);
	
	//	WORKERS executam...
	//	...

	
	munmap(src,size);
	munmap(dst,size);

	printf("Closing file descriptors.\n");
	close(fdin);
	close(fdout);

	free (h);
	#ifdef DEBUG
		puts("Liberta a memoria do header\n");
	#endif	
			
	
	terminate();
	
	stop = getCurrentTimeMicro();
	stopms = getCurrentTimeMili();
	printTimeElapsed(start, stop, "microseconds");
	printTimeElapsed(startms, stopms, "miliseconds");
	
	printf("Done!\n");

	for (i=0;i<nprocessos;i++)
		kill(worker_id[i],SIGINT);	//SIGKILL imprime "Morto" na consola
	
	for (i=0;i<nprocessos;i++)		// Espera pelos n processos
		wait(0);
	
	return (0);
}

int get_stat(int fdin)
{
	struct stat pstatbuf;	
	
if (fstat(fdin, &pstatbuf) < 0)	/* need size of input file */
	{
		fprintf(stderr,"fstat error\n");
		exit(1);
	}
	return pstatbuf.st_size;
}


void worker(int i ){


	int linha;
	worker_id[i]= getpid();

	while (1){
	
	sem_wait(sem_remaining_lines);
	
	sem_wait(mutex3);
	printf("Reading row... ");
	
	linha = sh_mem->next_line;
	sh_mem->next_line++;
	printf("Got row %d || ",(linha+1));
	sem_post(mutex3);

	//INVERTE

	printf("Inverting row... ");

	invertRow(h->width, &dst[linha*3*(h->width)]);

	printf("Done || ");
	printf("Saving row... ");		
	printf("Done\n");

	sh_mem->processed_lines++;
	
	sem_wait(mutex1);
	
	if (sh_mem->processed_lines ==   sh_mem->total_lines)
		sem_post(mutex2);
	
	sem_post(mutex1);	
	}
}


