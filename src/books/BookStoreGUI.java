/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Refilwe Potele
 */
public class BookStoreGUI extends JFrame {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/book";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "P@ssword@2003";
    // GUI components
    private final JTextField titleTextField;
    private final JTextField authorTextField;
    private final JTextField priceTextField;
    private final JTextField quantityTextField;
    private final JButton insertButton;
    private final JButton viewButton;
    private final JTextArea resultTextArea;

    public BookStoreGUI() {
        // Create and set up the window
        setTitle("Book Store Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create and add the components
        titleTextField = new JTextField(20);
        authorTextField = new JTextField(20);
        priceTextField = new JTextField(20);
        quantityTextField = new JTextField(20);
        insertButton = new JButton("Insert Book");
        viewButton = new JButton("View Books");
        resultTextArea = new JTextArea(5, 20);
       
        add(new JLabel("Title:"));
        add(titleTextField);
        add(new JLabel("Author:"));
        add(authorTextField);
        add(new JLabel("Price:"));
        add(priceTextField);
        add(new JLabel("Quantity:"));
        add(quantityTextField);
        add(insertButton);
        add(viewButton);
        add(new JScrollPane(resultTextArea));

        // Add action listeners for buttons
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertBook();
            }
        });
       
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewBooks();
            }
        });

        // Display the window
        pack();
        setVisible(true);
    }

    // Method to insert a book into the database
    private void insertBook() {
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        int quantity = Integer.parseInt(quantityTextField.getText());

        // Include sales tax calculation
        price += price * 0.05;

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            String sql = "INSERT INTO Books (title, author, price, quantity) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);

            pstmt.executeUpdate();
            resultTextArea.setText("Inserted book successfully!");
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            resultTextArea.setText("Failed to insert book: " + se.getMessage());
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
            resultTextArea.setText("Failed to insert book: " + e.getMessage());
        } finally {
            // Finally block used to close resources
            try {
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // Method to view books from the database
    private void viewBooks() {
        Connection conn = null;
        Statement stmt = null;
        try {

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Books";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            StringBuilder sb = new StringBuilder();
            while(rs.next()) {
                // Retrieve by column name
                int id  = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                // Display values
                sb.append("ID: ").append(id);
                sb.append(", Title: ").append(title);
                sb.append(", Author: ").append(author);
                sb.append(", Price: ").append(price);
                sb.append(", Quantity: ").append(quantity).append("\n");
            }
            resultTextArea.setText(sb.toString());
            rs.close();
        } catch(SQLException se) {
            
            // Handle errors for JDBC
            se.printStackTrace();
            resultTextArea.setText("Failed to view books: " + se.getMessage());
        } catch(Exception e) {
            
            // Handle errors for Class.forName
            e.printStackTrace();
            resultTextArea.setText("Failed to view books: " + e.getMessage());
        } finally {
            
            // Finally block used to close resources
            try {
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BookStoreGUI();
            }
        });
    }
}
