import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for generating test files for the sales reporting system.
 * This class generates random test data files including:
 * - Sales files for each salesman
 * - Product information file
 * - Salesman information file
 */
public class GenerateInfoFiles {
    private static final String SALES_DIR = "sales";
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String SALESMEN_FILE = "salesmen.txt";
    private static final Random random = new Random();
    
    // Lists of names for random generation
    private static final String[] FIRST_NAMES = {
        "John", "Jane", "Michael", "Sarah", "David", "Emma", "James", "Olivia",
        "William", "Sophia", "Robert", "Isabella", "Joseph", "Mia", "Thomas"
    };
    
    private static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
        "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez"
    };

    /**
     * Main method to generate all test files
     */
    public static void main(String[] args) {
        try {
            // Create sales directory if it doesn't exist
            new File(SALES_DIR).mkdirs();
            
            // Generate files
            createProductsFile(10);  // Generate 10 random products
            createSalesManInfoFile(5);  // Generate 5 random salesmen
            
            // Generate sales files for each salesman
            for (int i = 1; i <= 5; i++) {
                createSalesMenFile(3, "Salesman" + i, 1000 + i);
            }
            
            System.out.println("Test files generated successfully!");
        } catch (IOException e) {
            System.err.println("Error generating test files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates a sales file for a specific salesman with random sales data
     * @param randomSalesCount Number of sales to generate
     * @param name Name of the salesman
     * @param id ID of the salesman
     * @throws IOException If there's an error writing the file
     * @throws IllegalArgumentException If any parameter is invalid
     */
    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
        validatePositiveNumber(randomSalesCount, "Sales count");
        validateNotNullOrEmpty(name, "Name");
        validatePositiveNumber(id, "ID");
        
        String fileName = SALES_DIR + "/" + name + "_" + id + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write salesman header
            writer.write("CC;" + id + "\n");
            
            // Generate random sales
            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(10) + 1;  // Product IDs from 1 to 10
                int quantity = random.nextInt(10) + 1;    // Quantities from 1 to 10
                writer.write(productId + ";" + quantity + ";\n");
            }
        }
    }

    /**
     * Creates a file with random product information
     * @param productsCount Number of products to generate
     * @throws IOException If there's an error writing the file
     * @throws IllegalArgumentException If productsCount is invalid
     */
    public static void createProductsFile(int productsCount) throws IOException {
        validatePositiveNumber(productsCount, "Products count");
        
        try (FileWriter writer = new FileWriter(PRODUCTS_FILE)) {
            for (int i = 1; i <= productsCount; i++) {
                String productName = "Product" + i;
                double price = random.nextDouble() * 1000 + 100;  // Random price between 100 and 1100
                writer.write(i + ";" + productName + ";" + String.format("%.2f", price) + "\n");
            }
        }
    }

    /**
     * Creates a file with random salesman information
     * @param salesmanCount Number of salesmen to generate
     * @throws IOException If there's an error writing the file
     * @throws IllegalArgumentException If salesmanCount is invalid
     */
    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        validatePositiveNumber(salesmanCount, "Salesman count");
        
        try (FileWriter writer = new FileWriter(SALESMEN_FILE)) {
            for (int i = 1; i <= salesmanCount; i++) {
                String documentType = "CC";
                long documentNumber = 1000 + i;
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
                
                writer.write(documentType + ";" + documentNumber + ";" + 
                           firstName + ";" + lastName + "\n");
            }
        }
    }
    
    /**
     * Validates that a number is positive
     * @param number The number to validate
     * @param fieldName The name of the field being validated
     * @throws IllegalArgumentException If the number is not positive
     */
    private static void validatePositiveNumber(long number, String fieldName) {
        if (number <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }
    
    /**
     * Validates that a string is not null or empty
     * @param value The string to validate
     * @param fieldName The name of the field being validated
     * @throws IllegalArgumentException If the string is null or empty
     */
    private static void validateNotNullOrEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
} 