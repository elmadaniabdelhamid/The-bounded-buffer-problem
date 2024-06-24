# Define variables
SRC_DIR = The-bounded-buffer-problem 
SRC_FILES = $(wildcard *.java)
CLASS_FILES = $(SRC_FILES:.java=.class)
MAIN_CLASS = MainSemaphores
# MAIN_CLASS = MainBlockingQueue

# Default target
all: $(CLASS_FILES)
        @echo "Done compiling."
# Rule to compile all .java files to .class files
$(CLASS_FILES): $(SRC_FILES)
        javac $(SRC_FILES)

# Run the main class
run: all
        @echo "Program runing....."
        java $(MAIN_CLASS)
        @echo "Program finished."

# Clean up .class files
clean:
        rm -f *.class
        @echo "Done Cleaning."
# Phony targets
.PHONY: all run clean
