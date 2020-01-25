# This file currently assumes that 'make' is being called 
# from the root directory of the project.

all: build

jar: clean
	./gradlew jar

build: clean
	./gradlew build

clean:
	./gradlew clean
