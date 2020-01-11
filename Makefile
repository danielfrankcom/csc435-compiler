# This file currently assumes that 'make' is being called 
# from the root directory of the project.

all: clean build

jar:
	./gradlew jar

build:
	./gradlew build

clean:
	./gradlew clean
