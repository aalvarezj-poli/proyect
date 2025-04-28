import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class for processing sales data and generating reports.
 * This class reads the generated files and creates two reports:
 * 1. Sales report by salesman (sorted by total sales)
 * 2. Product sales report (sorted by quantity sold)
 */
public class Main {
    private static final String SALES_DIR = "sales";
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String SALESMEN_FILE = "salesmen.txt";
    private static final String SALES_REPORT_FILE = "sales_report.txt";
    private static final String PRODUCTS_REPORT_FILE = "products_report.txt";

    /**
     * Main method to process files and generate reports
     */
    public static void main(String[] args) {
        try {
            // Load data
            Map<String, Product> products = loadProducts();
            Map<String, Salesman> salesmen = loadSalesmen();
            // Start the recursive processing from the base sales directory based on code review from the tutor
            processSalesFiles(new File(SALES_DIR), products, salesmen);

            // Generate reports
            generateSalesReport(salesmen);
            generateProductsReport(products);

            System.out.println("Reports generated successfully!");
        } catch (Exception e) {
            System.err.println("Error processing files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads product information from the products file
     */
    private static Map<String, Product> loadProducts() throws IOException {
        Map<String, Product> products = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                products.put(parts[0], new Product(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
        }
        return products;
    }

    /**
     * Loads salesman information from the salesmen file
     */
    private static Map<String, Salesman> loadSalesmen() throws IOException {
        Map<String, Salesman> salesmen = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SALESMEN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                salesmen.put(parts[1], new Salesman(parts[0], parts[1], parts[2], parts[3]));
            }
        }
        return salesmen;
    }

    /**
     * Recursively processes sales files in a directory and its subdirectories,
     * updating salesman and product statistics.
     * @param directory The directory to scan for sales files.
     * @param products A map of product IDs to Product objects.
     * @param salesmen A map of salesman IDs to Salesman objects.
     * @throws IOException If an I/O error occurs.
     */
    private static void processSalesFiles(File directory, Map<String, Product> products, Map<String, Salesman> salesmen) throws IOException {
        File[] files = directory.listFiles(); // List all files and directories

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // If it's a directory, recurse into it
                    processSalesFiles(file, products, salesmen);
                } else if (file.isFile() && file.getName().endsWith(".txt")) {
                    // If it's a .txt file, process it
                    processSalesFile(file, products, salesmen);
                }
            }
        }
    }

    /**
     * Processes a single sales file
     */
    private static void processSalesFile(File file, Map<String, Product> products, Map<String, Salesman> salesmen) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(";");
                String salesmanId = parts[1];
                Salesman salesman = salesmen.get(salesmanId);
                
                if (salesman != null) {
                    while ((line = reader.readLine()) != null) {
                        parts = line.split(";");
                        String productId = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        
                        Product product = products.get(productId);
                        if (product != null) {
                            product.addQuantitySold(quantity);
                            salesman.addSale(product.getPrice() * quantity);
                        }
                    }
                }
            }
        }
    }

    /**
     * Generates the sales report sorted by total sales
     */
    private static void generateSalesReport(Map<String, Salesman> salesmen) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SALES_REPORT_FILE))) {
            salesmen.values().stream()
                .sorted(Comparator.comparingDouble(Salesman::getTotalSales).reversed())
                .forEach(salesman -> writer.printf("%s;%.2f%n", 
                    salesman.getFullName(), salesman.getTotalSales()));
        }
    }

    /**
     * Generates the products report sorted by quantity sold
     */
    private static void generateProductsReport(Map<String, Product> products) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRODUCTS_REPORT_FILE))) {
            products.values().stream()
                .sorted(Comparator.comparingInt(Product::getQuantitySold).reversed())
                .forEach(product -> writer.printf("%s;%.2f%n", 
                    product.getName(), product.getPrice()));
        }
    }

    /**
     * Represents a product with its sales information
     */
    private static class Product {
        private final String id;
        private final String name;
        private final double price;
        private int quantitySold;

        public Product(String id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantitySold = 0;
        }

        public void addQuantitySold(int quantity) {
            this.quantitySold += quantity;
        }

        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getQuantitySold() { return quantitySold; }
    }

    /**
     * Represents a salesman with their sales information
     */
    private static class Salesman {
        private final String documentType;
        private final String documentNumber;
        private final String firstName;
        private final String lastName;
        private double totalSales;

        public Salesman(String documentType, String documentNumber, String firstName, String lastName) {
            this.documentType = documentType;
            this.documentNumber = documentNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.totalSales = 0;
        }

        public void addSale(double amount) {
            this.totalSales += amount;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public double getTotalSales() { return totalSales; }
    }
}
