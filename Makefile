# Define variables
SRC_DIR = src
BIN_DIR = bin
MAIN_CLASS = MainBlockingQueue

# Find all .java files in the SRC_DIR
SOURCES = $(wildcard $(SRC_DIR)/*.java)

# Convert .java file paths in SRC_DIR to .class file paths in BIN_DIR
CLASSES = $(SOURCES:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

# Default target
all: $(CLASSES)

# Rule to compile .java files to .class files
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $<

# Run the main class
run: all
	java -cp $(BIN_DIR) $(MAIN_CLASS)

# Clean up .class files
clean:
	rm -rf $(BIN_DIR)/*.class

# Phony targets
.PHONY: all run clean
