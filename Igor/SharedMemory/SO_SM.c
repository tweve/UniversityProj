

/*---------------------

Autores:

-> Igor Nelson Garrido da Cruz        - 2009111924

-> Carlos Manuel Fernandes dos Santos - 2009108991

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
pixel *dst;
int size;

void init();
void terminate();
void master(int argc, char *argv[]);
void worker();
int get_stat(int fdin);
int write_pos, read_pos;
int shmid;
int shmidimg;
pixel * sh_mem_img;
int* buf;
mem_struct *sh_mem;
header *h;
int worker_id[nprocessos];

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

  shmctl(shmid, IPC_RMID, NULL);
}
int main(int argc, char *argv[])
{	
	double start, stop, startms, stopms;
	FILE *fpin,*fpout;

	if(argc < 3)
	{
		printf("Incorrect usage.\nPlease use \"./invert input_filename.ppm output_filename.ppm\"\n");
		exit(0);
	}
	
	//start timer
	start = getCurrentTimeMicro();
	startms = getCurrentTimeMili();
	
	
	printf("Opening input file [%s]\n", argv[1]);
	fpin = fopen(argv[1], "r");
	if(fpin == NULL)
	{
		printf("Could not open input file\n");
		exit(-1);
	}

	printf("Opening output file [%s]\n",argv[2]);
	fpout = fopen(argv[2], "w");
	if(fpout == NULL)
	{
		printf("Could not open output file\n");
		exit(-1);
	}
	
	printf("Getting header\n");
	h = getImageHeader(fpin);
	if(h == NULL)
	{
		printf("Error getting header from file\n");
		exit(-1);
	}
	printf("Got file Header: %s - %u x %u - %u\n", h->type, h->width, h->height, h->depth);
	
	printf("Saving header to output file\n");
	if(writeImageHeader(h, fpout) == -1)
	{
		printf("Could not write to output file\n");
		exit(-1);
	}

	shmidimg = shmget(IPC_PRIVATE, (h->width)*(h->height)*sizeof(pixel), IPC_CREAT|0700);
	if (shmidimg == -1){
		exit(-1);
	}
	
	sh_mem_img = (pixel*) shmat(shmidimg, NULL, 0);
	if (sh_mem_img == (pixel*) -1){
		exit(-1);
	}
	
	int i;

	if(getImageRow(h->width*h->height, sh_mem_img, fpin) == -1)
		{
			printf("Error while reading row\n");
			exit(-1);
		}


	
	puts("Starting work...\n");
	init();			//inicia semaforos
	sh_mem->total_lines = h->height;
	
	for (i=0;i<nprocessos;i++)			//Cria N processos 
			if(fork()==0)
				worker(i);

	sem_wait(mutex2);
	
	//	WORKERS executam...
	//	...



	if(writeRow(h->width*h->height, sh_mem_img, fpout) == -1)
		{
			printf("Error while writing row\n");
			exit(-1);
		}

	fclose(fpin);
	fclose(fpout);
	
  	shmctl(shmidimg, IPC_RMID, NULL);
	free(h);
		
	terminate();
	
	stop = getCurrentTimeMicro();
	stopms = getCurrentTimeMili();
	printTimeElapsed(start, stop, "microseconds");
	printTimeElapsed(startms, stopms, "miliseconds");
	
	printf("Done!\n");

	for (i=0;i<nprocessos;i++)
		kill(worker_id[i],SIGINT);  // SIGKILL Devolve "Morto" para a consola
			
	for (i=0;i<nprocessos;i++)			//Espera
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


void worker(int i){


	int linha;
	worker_id[i]= getpid();
	while (1){
	
	sem_wait(sem_remaining_lines);
	
	sem_wait(mutex3);
	
	linha =   sh_mem->next_line;
	sh_mem->next_line++;
	

	sem_post(mutex3);

	printf("Got row %d || ",(linha+1));

	//INVERTE

	printf("Inverting row... ");
	
	invertRow(h->width, &sh_mem_img[linha*h->width]);
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


