import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CustomerForm extends JFrame {
    

    private JTextField idField, nameField, ageField, cityField;

    public CustomerForm() {
        setTitle("MongoDB CRUD Operations");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10)); // Add spacing between components

        // Set margin (20px)
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Top, left, bottom, right

        // ID Field
        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        // Name Field
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        // Age Field
        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        // City Field
        panel.add(new JLabel("City:"));
        cityField = new JTextField();
        panel.add(cityField);

        // Buttons
        JButton addButton = createButton("Add");
        JButton readButton = createButton("Read");
        JButton updateButton = createButton("Update");
        JButton deleteButton = createButton("Delete");

        // Add buttons to panel
        panel.add(addButton);
        panel.add(readButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        // Add panel to the frame
        add(panel);

        // Add button functionality
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readCustomerInfo();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(80, 30)); // Width 80px, Height 20px
        return button;
    }

    private void addCustomer() {
        // Your logic for adding a customer
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("customers");
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String city = cityField.getText();
            Document document = new Document()
                    .append("id", id)
                    .append("name", name)
                    .append("age", age)
                    .append("city", city);
            collection.insertOne(document);
            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void readCustomerInfo() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("customers");
            int id = Integer.parseInt(idField.getText());
            Document document = collection.find(new Document("id", id)).first();
            JOptionPane.showMessageDialog(this, document.toJson(), "Customer Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCustomer() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("customers");
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String city = cityField.getText();
            Document document = new Document()
                    .append("id", id)
                    .append("name", name)
                    .append("age", age)
                    .append("city", city);
            collection.updateOne(new Document("id", id), new Document("$set", document));
            JOptionPane.showMessageDialog(this, "Customer updated successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("customers");
            int id = Integer.parseInt(idField.getText());
            collection.deleteOne(new Document("id", id));
            JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new CustomerForm();
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        cityField.setText("");
    }

}
