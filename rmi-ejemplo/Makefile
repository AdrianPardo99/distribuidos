# Archivo Makefile
# @author Adrian Gonzalez Pardo
JVC=javac
SRCC=$(wildcard *.java)
OBJS=$(SRCC:.java=.class)
all: ${OBJS}

%.class: %.java
	${JVC} $<

.PHONY: clean

clean:
	rm *.class
