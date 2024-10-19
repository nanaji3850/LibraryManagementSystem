package LibraryManagementSystem;

import java.io.*;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;

    // Constructor
    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        loadData(); // Load data when the library is created
    }

    // Load data from file
    @SuppressWarnings("unchecked") // Suppressing unchecked cast warnings
    public void loadData() {
        File file = new File("library_data.dat");
        if (!file.exists()) {
            System.out.println("No previous data found. Starting with an empty library.");
            return; // Exit the method if the file does not exist
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            books = (ArrayList<Book>) in.readObject();
            users = (ArrayList<User>) in.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    // Save data to file
    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("library_data.dat"))) {
            out.writeObject(books);
            out.writeObject(users);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Add a book
    public void addBook(int bookId, String title, String author, int availableCopies) {
        Book newBook = new Book(bookId, title, author, availableCopies);
        books.add(newBook);
        System.out.println("Added book: " + newBook);
        saveData(); // Save after adding a book
    }

    // Remove a book by ID
    public boolean removeBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                books.remove(book);
                System.out.println("Removed book: " + book);
                saveData(); // Save after removing a book
                return true;
            }
        }
        System.out.println("Book with ID " + bookId + " not found.");
        return false;
    }

    // Register a user
    public boolean registerUser(String userId, String name, String password) {
        // Check if the user ID already exists
        if (findUserById(userId) != null) {
            return false; // User ID is taken
        }
        User newUser = new User(userId, name, password);
        users.add(newUser);
        System.out.println("Registered user: " + newUser.getUserId());
        saveData(); // Save after registering a user
        return true; // Registration successful
    }

    // Borrow a book
    public void borrowBook(String userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user != null && book != null) {
            if (user.borrowBook(book)) {
                System.out.println(userId + " borrowed " + book.getTitle());
                saveData(); // Save after borrowing a book
            } else {
                System.out.println("No copies available for " + book.getTitle());
            }
        } else {
            System.out.println("Invalid user ID or book ID.");
        }
    }

    // Return a book
    public void returnBook(String userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user != null && book != null) {
            if (user.returnBook(book)) {
                System.out.println(userId + " returned " + book.getTitle());
                saveData(); // Save after returning a book
            } else {
                System.out.println(userId + " does not have " + book.getTitle() + " borrowed.");
            }
        } else {
            System.out.println("Invalid user ID or book ID.");
        }
    }

    // Search for a book by title
    public void searchBook(String title) {
        for (Book book : books) {
            if (book.searchBook(title)) {
                System.out.println("Found book: " + book);
                return;
            }
        }
        System.out.println("Book with title '" + title + "' not found.");
    }

    // Helper methods
    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private Book findBookById(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    // Display all books
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("Books in the library:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
}
