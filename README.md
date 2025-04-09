# Sales Report Generator

This project is part of a Java programming course assignment. It consists of two main programs:

1. `GenerateInfoFiles`: Generates random test data files
2. `Main`: Processes the generated files to create sales reports

## Project Structure

The project includes the following files:

- `GenerateInfoFiles.java`: Generates test data files
- `Main.java`: Processes data and generates reports
- `GenerateInfoFilesTest.java`: Unit tests for the data generation
- `pom.xml`: Maven configuration file
- `conclusion.txt`: Project summary and learning outcomes

## Generated Files

The program generates the following files:

- `sales/`: Directory containing individual sales files for each salesman
- `products.txt`: File containing product information
- `salesmen.txt`: File containing salesman information
- `sales_report.txt`: Report of sales by salesman (sorted by total sales)
- `products_report.txt`: Report of products sold (sorted by quantity)


## How to Run

1. Compile the Java files:
```bash
javac GenerateInfoFiles.java Main.java
```

2. Generate test data:
```bash
java GenerateInfoFiles
```

3. Generate reports:
```bash
java Main
```

## Running Tests

To run the unit tests:
```bash
mvn test
```

## Requirements

- Java 8 or higher
- Maven (for running tests)
- Eclipse IDE (recommended)

## Features

- Generates random but realistic test data
- Processes sales data from multiple files
- Generates sorted reports by sales and product quantities
- Includes comprehensive error handling
- Follows Java coding standards and best practices
- Includes unit tests for data generation
- Well-documented code with proper comments 