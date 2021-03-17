# Archivo Makefile
# @author Adrian Gonzalez Pardo
JVC=javac
SRCC=$(wildcard *.java)
flags=-cp gson-2.3.1.jar
OBJS=$(SRCC:.java=.class)

all: ${OBJS}

%.class: %.java
	${JVC} ${SRCC} ${flags}

.PHONY: clean

clean:
	rm *.class
