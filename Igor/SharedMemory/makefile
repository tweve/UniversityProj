FLAGS = -Wall -g -lrt
CC = gcc
INV = invert
OBJS = ppmtools.o SO_SM.o timeprofiler.o

all: ${INV} clean

clean:
	rm *o

${INV}:	${OBJS}
	${CC} ${FLAGS} ${OBJS} -o $@	
	
.c.o:
	${CC} ${FLAGS} $< -c
	
	
# # # # # # # # #

ppmtools.o: ppmtools.c
SO_SM.o: SO_SM.c
timeprofiler.o: timeprofiler.c

