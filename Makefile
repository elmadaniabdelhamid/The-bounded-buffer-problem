# Define variables
SRC_DIR = The-bounded-buffer-problem 
SRC_FILES = $(wildcard *.java)
CLASS_FILES = $(SRC_FILES:.java=.class)
MAIN_CLASS = MainBlockingQueue

# Default target
all: $(CLASS_FILES)

# Rule to compile all .java files to .class files
$(CLASS_FILES): $(SRC_FILES)
        javac $(SRC_FILES)

# Run the main class
run: all
        java $(MAIN_CLASS)

# Clean up .class files
clean:
        rm -f *.class

# Phony targets
.PHONY: all run clean
