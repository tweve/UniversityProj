FLAGS = -Wall -g -lrt
CC = gcc
INV = invert
OBJS = ppmtools.o SO_MMF.o timeprofiler.o

all: ${INV} clean

clean:
	rm *o

${INV}:	${OBJS}
	${CC} ${FLAGS} ${OBJS} -o $@	
	
.c.o:
	${CC} ${FLAGS} $< -c
	
	
# # # # # # # # #

ppmtools.o: ppmtools.c
SO_MMF.o: SO_MMF.c
timeprofiler.o: timeprofiler.c

