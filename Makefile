# This file currently assumes that 'make' is being called 
# from the root directory of the project.

GRAMMAR_NAME= ulNoActions
GRAMMAR_SRC= $(GRAMMAR_NAME).g

BUILD_DIR= build

ANTLR_JAR= antlr-3.5.2-complete.jar
ANTLR_JAR_PATH= /tmp/$(ANTLR_JAR)
ANTLR_URL= https://www.antlr3.org/download

CLASSPATH= -classpath $(ANTLR_JAR_PATH):.
JAVA= java $(CLASSPATH)
JAVAC= javac $(CLASSPATH)

all: grammar compiler test

download:
	[ -f $(ANTLR_JAR_PATH) ] || wget -N $(ANTLR_URL)/$(ANTLR_JAR) -O $(ANTLR_JAR_PATH)

make_build:
	mkdir -p $(BUILD_DIR)

grammar: download
	$(JAVA) org.antlr.Tool -fo . $(GRAMMAR_SRC) 

compiler:
	$(JAVAC) *.java

test:
	$(JAVA) Compiler lab1_should_accept.ul
	! $(JAVA) Compiler lab1_reject.ul

clean:
	rm -f $(ANTLR_JAR_PATH)
	rm -f *.class $(GRAMMAR_NAME)*.java $(GRAMMAR_NAME).tokens
