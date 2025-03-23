# Sales Report Generator

This project is part of a Java programming course assignment. It consists of two main parts:

1. GenerateInfoFiles: A program that generates random test data files
2. Main: A program that processes these files to generate sales reports (to be implemented)

## Project Structure

The project generates the following files:

- `sales/`: Directory containing individual sales files for each salesman
- `products.txt`: File containing product information
- `salesmen.txt`: File containing salesman information

## File Formats

### Sales Files (in sales/ directory)
```
CC;1001
1;5;
2;3;
3;1;
```

### Products File (products.txt)
```
1;Product1;150.00
2;Product2;200.00
3;Product3;300.00
```

### Salesmen File (salesmen.txt)
```
CC;1001;John;Smith
CC;1002;Jane;Johnson
CC;1003;Michael;Williams
```

## How to Run

1. Compile the Java files:
```bash
javac GenerateInfoFiles.java
```

2. Run the program:
```bash
java GenerateInfoFiles
```

The program will generate all necessary test files in the current directory.

## Requirements

- Java 8 or higher

## Features

- Generates random but realistic test data
- Creates all required file formats
- Includes proper error handling
- Follows Java coding standards and best practices 