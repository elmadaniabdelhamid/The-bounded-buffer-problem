# Producer-Consumer with JAVA

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation and Setup](#installation-and-setup)
  - [Cloning the Repository](#cloning-the-repository)
  - [Compiling the Project](#compiling-the-project)
  - [Running the Project](#running-the-project)
- [Usage](#usage)
  - [Producing a Product](#producing-a-product)
  - [Consuming a Product](#consuming-a-product)
  - [Viewing Available Products](#viewing-available-products)
  - [Exiting the Application](#exiting-the-application)
- [Classes](#classes)
  - [Main](#main)
  - [Producteur](#producteur)
  - [Consommateur](#consommateur)
  - [Produit](#produit)
- [Troubleshooting](#troubleshooting)
  - [Common Issues](#common-issues)
- [License](#license)
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

### Compiling the Project

1. Navigate to the project directory:

   ```bash
   cd producer-consumer-java
   ```

2. Compile the project:

   ```bash
   javac -d bin src/JAVA_projects/*.java
   ```

### Running the Project

1. Run the project:

   ```bash
   java -cp bin JAVA_projects.Main
   ```

## Usage

### Producing a Product

1. **Select "Produce"** from the dropdown menu.
2. **Enter the product name** in the "Product Name" field.
3. **Enter the product price** in the "Product Price" field.
4. Click the **"Perform Action"** button.
5. The log area will display a message indicating the product was produced.

### Consuming a Product

1. **Select "Consume"** from the dropdown menu.
2. **Enter the product name** in the "Consume Product Name" field.
3. Click the **"Perform Action"** button.
4. The log area will display a message indicating whether the product was consumed or  does not exist.
5. After consumption, the remaining products and their prices will be displayed in the log area.

### Exiting the Application

- Click the **"Exit"** button to close the application.

## Classes

### [Main](src/JAVA_projects/Main.java)

The `Main` class sets up the GUI and handles user interactions.

### [Producteur](src/JAVA_projects/Producteur.java)

The `Producteur` class is responsible for producing products and adding them to the buffer.

### [Consommateur](src/JAVA_projects/Consommateur.java)

The `Consommateur` class is responsible for consuming products from the buffer.

### [Produit](src/JAVA_projects/Produit.java)

The `Produit` class represents a product with a name and a price.

## Troubleshooting

### Common Issues

1. **Invalid Price Input**:
   - Ensure that the price entered is a valid number. If not, the log area will display "Prix invalide".

2. **Buffer Full or Empty**:
   - If the buffer is full, producing a new product will fail, and a message will be displayed.
   - If the buffer is empty, consuming a product will fail, and a message will be displayed.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For further assistance, please contact:

- **Email**: abdelhamidelmadani45@gmail.com
