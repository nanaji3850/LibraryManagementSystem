package LibraryManagementSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        boolean running = true;
        String currentUserId = null; // To keep track of the logged-in user

        // Pre-register some users for testing purposes
        library.registerUser("U001", "Alice", "password"); // User ID: U001, Password: password
        library.registerUser("U002", "Bob", "password123"); // User ID: U002, Password: password123

        while (running) {
            System.out.println("\n--- Library Management System ---");
            if (currentUserId == null) {
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.println("3. Register"); // Option to register new users

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Login
                        System.out.print("Enter user ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();

                        // Check user credentials
                        User user = library.findUserById(userId);
                        if (user != null && user.authenticate(password)) {
                            currentUserId = userId;
                            System.out.println("Login successful!");
                        } else {
                            System.out.println("Invalid user ID or password.");
                        }
                        break;
                    case 2:
                        running = false;
                        break;
                    case 3:
                        // User registration
                        System.out.print("Enter new user ID: ");
                        String newUserId = scanner.nextLine();
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String newPassword = scanner.nextLine();
                        if (library.registerUser(newUserId, name, newPassword)) {
                            System.out.println("User registered successfully!");
                        } else {
                            System.out.println("Registration failed. User ID may already be taken.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book");
                System.out.println("3. Search Book");
                System.out.println("4. Borrow Book");
                System.out.println("5. Return Book");
                System.out.println("6. View Borrowed Books");
                System.out.println("7. Display All Books");
                System.out.println("8. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Add book
                        System.out.print("Enter book ID: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter available copies: ");
                        int availableCopies = scanner.nextInt();
                        library.addBook(bookId, title, author, availableCopies);
                        break;
                    case 2:
                        // Remove book
                        System.out.print("Enter book ID to remove: ");
                        int removeId = scanner.nextInt();
                        library.removeBook(removeId);
                        break;
                    case 3:
                        // Search book
                        System.out.print("Enter title to search: ");
                        String searchTitle = scanner.nextLine();
                        library.searchBook(searchTitle);
                        break;
                    case 4:
                        // Borrow book
                        System.out.print("Enter book ID to borrow: ");
                        int borrowId = scanner.nextInt();
                        library.borrowBook(currentUserId, borrowId);
                        break;
                    case 5:
                        // Return book
                        System.out.print("Enter book ID to return: ");
                        int returnId = scanner.nextInt();
                        library.returnBook(currentUserId, returnId);
                        break;
                    case 6:
                        // View borrowed books
                        User currentUser = library.findUserById(currentUserId);
                        if (currentUser != null) {
                            currentUser.viewBorrowedBooks(); // Assuming you have a method to view borrowed books
                        }
                        break;
                    case 7:
                        // Display all books
                        library.displayBooks();
                        break;
                    case 8:
                        // Logout
                        currentUserId = null;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        scanner.close();
    }
}
