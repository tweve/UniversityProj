#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/wait.h>
#include <math.h>

#include "ppmtools.h"

#define READ_BUFFER_LEN 16
 
 
 char* readType(FILE *fp)
 {
	char buf[READ_BUFFER_LEN];
	char *t; 
    
    //get line of the file into read buffer -> type of ppm
    t = fgets(buf, READ_BUFFER_LEN, fp);
    //check if it reads something and if it is a P6 type
    if (t == NULL)
    {
		printf("could not read type from file\n");
		return "";
	}
	
	if(strncmp(buf, "P6\n", 3) != 0 )
	{
		printf("type is not P6\n");
		return "";
	}
	else
	{
		printf("set type as P6\n");
		//set type as P6
		return "P6";
	}    
}


unsigned int readNextHeaderValue(FILE *fp)
{
	char buf[READ_BUFFER_LEN];
	char *t; 
	int r;
	int value;
	
	printf("read next header value\n");
    //load next line
    t = fgets(buf, READ_BUFFER_LEN, fp);
    if(t == NULL)
    {
		printf("Error reading from file");
		return 0;
	}
	
	//read value as unsigned int
    r = sscanf(buf, "%u", &value);
    
    //check if the read was successful
    if ( r != 1 ) 
    {
		printf("Could not read width value\n");
		return 0;
	}
	else
	{
		return value;
	}
}
        
 
header* getImageHeader(FILE *fp)
{
	//alloc header
	header* h = malloc(sizeof(header));  
	
	//check if file pointer is null
    if (fp == NULL)
    {
		//file pointer is null
		return NULL;
	}
	
	//get type
	char* type = readType(fp);
	if(strcmp(type, "") != 0)
	{
		//write type
		strcpy(h->type,type);
	}
	else
	{
		//error reading type
		return NULL;
	}
    
    h->width = readNextHeaderValue(fp);
    h->height = readNextHeaderValue(fp);
    h->depth = readNextHeaderValue(fp);
    
    //check for successful read and valid color range
	if(h->depth != 255 || h->width == 0 || h->height == 0)
	{
		//printf("error reading values\n");
		return NULL;
	}
	
	return h;
}
//NOTE:
// The program fails if the first byte of the image is equal to 32 (ascii for space char). because
// the fscanf in eats the space (ascii 32) and the image is read with one byte less


int getImageRow(int pixelNo, pixel* row, FILE *fp)
{
	int i;
	byte b;
	//reading line
	
	for (i = 0; i < pixelNo; i++)
	{
		//set pixel values
		if(fread(&row[i],sizeof(b),3*sizeof(b),fp) < 1)
			return -1;
	}
	
	return pixelNo;
}


void invertRow(int pixelNo, pixel* row)
{
	pixel aux;
	int i;
	
	//for each pixel
	for(i=0; i < pixelNo/2; i++)
	{
		//exchange pixel [i] with the pixel at the end minus [i]
		aux = row[i];
		row[i] = row[pixelNo-i-1];
		row[pixelNo-i-1] = aux;
	}
}

int writeImageHeader(header* h, FILE* fp)
{
	//write header fields with newline between them
	if(fprintf(fp,"%s\n%u\n%u\n%u\n",h->type,h->width,h->height,h->depth) < 1)
	{
		return -1;
	}
	
	return 0;
}

int writeRow(int pixelNo, pixel* row, FILE* fp)
{
	int i, r;
	
	//for each pixel
	for(i = 0; i < pixelNo; i++)
	{
		//write one byte per RGB channel in pixel
		r = fwrite(&row[i],sizeof(byte),3*sizeof(byte),fp);
		
		//check for errors
		if(r <1)
		{
			return -1;
		}
	}
	
	return 0;
}
