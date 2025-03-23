import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Unit tests for the GenerateInfoFiles class.
 * Tests the generation of sales files, products file, and salesmen information file.
 */
public class GenerateInfoFilesTest {
    @TempDir
    Path tempDir;
    
    private String salesDir;
    private String productsFile;
    private String salesmenFile;
    
    @BeforeEach
    void setUp() {
        // Set up test directory paths
        salesDir = tempDir.resolve("sales").toString();
        productsFile = tempDir.resolve("products.txt").toString();
        salesmenFile = tempDir.resolve("salesmen.txt").toString();
        
        // Create sales directory
        new File(salesDir).mkdirs();
    }
    
    /**
     * Test the creation of a sales file for a salesman
     */
    @Test
    void testCreateSalesMenFile() throws IOException {
        // Test parameters
        int salesCount = 3;
        String name = "TestSalesman";
        long id = 1001;
        
        // Create the sales file
        GenerateInfoFiles.createSalesMenFile(salesCount, name, id);
        
        // Verify file exists
        File salesFile = new File(salesDir, name + "_" + id + ".txt");
        assertTrue(salesFile.exists(), "Sales file should be created");
        
        // Read and verify file contents
        List<String> lines = Files.readAllLines(salesFile.toPath());
        assertEquals(salesCount + 1, lines.size(), "File should have header plus sales count lines");
        
        // Verify header format
        String header = lines.get(0);
        assertEquals("CC;" + id, header, "Header should match expected format");
        
        // Verify sales lines format
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(";");
            assertEquals(2, parts.length, "Each sales line should have 2 parts");
            assertTrue(Integer.parseInt(parts[0]) > 0, "Product ID should be positive");
            assertTrue(Integer.parseInt(parts[1]) > 0, "Quantity should be positive");
        }
    }
    
    /**
     * Test the creation of the products file
     */
    @Test
    void testCreateProductsFile() throws IOException {
        // Test parameters
        int productsCount = 5;
        
        // Create the products file
        GenerateInfoFiles.createProductsFile(productsCount);
        
        // Verify file exists
        File file = new File(productsFile);
        assertTrue(file.exists(), "Products file should be created");
        
        // Read and verify file contents
        List<String> lines = Files.readAllLines(file.toPath());
        assertEquals(productsCount, lines.size(), "File should have correct number of products");
        
        // Verify each product line format
        for (String line : lines) {
            String[] parts = line.split(";");
            assertEquals(3, parts.length, "Each product line should have 3 parts");
            assertTrue(Integer.parseInt(parts[0]) > 0, "Product ID should be positive");
            assertTrue(parts[1].startsWith("Product"), "Product name should start with 'Product'");
            assertTrue(Double.parseDouble(parts[2]) >= 100 && Double.parseDouble(parts[2]) <= 1100, 
                      "Price should be between 100 and 1100");
        }
    }
    
    /**
     * Test the creation of the salesmen information file
     */
    @Test
    void testCreateSalesManInfoFile() throws IOException {
        // Test parameters
        int salesmanCount = 3;
        
        // Create the salesmen file
        GenerateInfoFiles.createSalesManInfoFile(salesmanCount);
        
        // Verify file exists
        File file = new File(salesmenFile);
        assertTrue(file.exists(), "Salesmen file should be created");
        
        // Read and verify file contents
        List<String> lines = Files.readAllLines(file.toPath());
        assertEquals(salesmanCount, lines.size(), "File should have correct number of salesmen");
        
        // Verify each salesman line format
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(";");
            assertEquals(4, parts.length, "Each salesman line should have 4 parts");
            assertEquals("CC", parts[0], "Document type should be CC");
            assertEquals(String.valueOf(1001 + i), parts[1], "Document number should increment");
            assertTrue(parts[2].length() > 0, "First name should not be empty");
            assertTrue(parts[3].length() > 0, "Last name should not be empty");
        }
    }
    
    /**
     * Test error handling for invalid parameters
     */
    @Test
    void testInvalidParameters() {
        // Test with negative sales count
        assertThrows(IllegalArgumentException.class, () -> {
            GenerateInfoFiles.createSalesMenFile(-1, "Test", 1001);
        });
        
        // Test with negative products count
        assertThrows(IllegalArgumentException.class, () -> {
            GenerateInfoFiles.createProductsFile(-1);
        });
        
        // Test with negative salesman count
        assertThrows(IllegalArgumentException.class, () -> {
            GenerateInfoFiles.createSalesManInfoFile(-1);
        });
    }
} 