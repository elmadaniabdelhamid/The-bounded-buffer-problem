# Producer-Consumer with JAVA

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation and Setup](#installation-and-setup)
  - [Cloning the Repository](#cloning-the-repository)
  - [Install JDK if u do not have one.](#install-JDK)
  - [Compiling the Project](#compiling-the-project)
  - [Running the Project](#running-the-project)
  - [Cleaning the project](#cleaning_the_project)
- [Troubleshooting](#troubleshooting)
  - [Common Issues](#common-issues)
- [Contact](#contact)

## Overview

This project demonstrates the classic Producer-Consumer problem using Java.
The Producer-Consumer problem is a synchronization problem that involves ensuring that producers (who produce data and add it to a buffer)
and consumers (who take data from the buffer) operate without running into race conditions.
This project uses Java's synchronized methods. It features a graphical user interface (GUI) to simulate the production and consumption of products.

## Features

- **Producers** can add products to the buffer while there is an empty scpace .
- **Consumers** can consume products from the buffer if they exist.
- The GUI allows users to:
  - Add new products with a name and price.
  - Consume products by specifying the product name.
  - View the list of available products after each consumption.

## Installation and Setup

### Cloning the Repository

1. Open a terminal or command prompt.
2. Clone the repository using the following command:

   ```bash
   git clone https://github.com/yourusername/producer-consumer-java.git
   cd producer-consumer-java
   ```
### install JDK
```bash
   sudo apt update
   sudo apt install default-jdk
   ```

### Compiling the Project

1. Navigate to the project directory:

   ```bash
   cd producer-consumer-java
   ```

2. Compile the project:

   ```bash
   make
   ```

### Running the Project

1. Run the project:

   ```bash
   make run
   ```
### Cleanin the project
 ```bash
   make clean
   ```

## Troubleshooting

### Common Issues

1. **Buffer Full or Empty**:
   - If the buffer is full, producing a new data will fail, and a message will be displayed.
   - If the buffer is empty, consuming a data will fail, and a message will be displayed.

## Contact

For further assistance, please contact:

- **Emails**: abdelhamidelmadani45@gmail.com
boutainaotaku2003@gmail.com
