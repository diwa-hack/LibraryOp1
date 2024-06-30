import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LibraryManagement {
    static Scanner input;
    static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static Calendar cal = Calendar.getInstance();
    static Book root;
    static HashMap<String, Integer> hashmapping = new HashMap<>();
    static Student[] array = new Student[3];
    static int[][] arr = new int[100][2];

    public static void main(String[] args) throws Exception {
        input = new Scanner(System.in);

        array[0] = new Student("Sunny", 1807, "B.Tech");
        array[1] = new Student("Akshat", 2021, "B.Tech");
        array[2] = new Student("Akshit", 1205, "B.Tech");

        root = null;

        initializeResources();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n.....................................");
            System.out.println("1. Librarian Login.");
            System.out.println("2. User Login.");
            System.out.println("3. Exit.");
            System.out.println("\n.....................................");

            System.out.println("\nEnter Your choice:");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    librarianLogin();
                    break;
                case 2:
                    userLogin();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }

        closeResources();
    }

    static void initializeResources() throws IOException {
        FileWriter fr = new FileWriter("append.txt", true);
        BufferedWriter br = new BufferedWriter(fr);

        FileReader file = new FileReader("book.txt");
        BufferedReader reader = new BufferedReader(file);

        FileReader file2 = new FileReader("num.txt");
        BufferedReader reader2 = new BufferedReader(file2);

        FileReader file3 = new FileReader("num1.txt");
        BufferedReader reader3 = new BufferedReader(file3);
    }

    static void librarianLogin() throws IOException {
        String pwd1 = "abc123";
        String id1 = "dsa@1";

        System.out.println("\nEnter UserId:");
        String id2 = input.next();

        System.out.println("\nEnter Password:");
        String pwd2 = input.next();

        if (!id1.equals(id2))
            System.out.println("Invalid Userid.");
        else if (!pwd1.equals(pwd2))
            System.out.println("Invalid Password.");
        else {
            System.out.println("Login successful.");
            librarianOperations();
        }
    }

    static void librarianOperations() throws IOException {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n.....................................");
            System.out.println("1. Add book.");
            System.out.println("2. Delete book.");
            System.out.println("3. Update book.");
            System.out.println("4. Print Books Details.");
            System.out.println("5. Print Books in-order.");
            System.out.println("6. Print tree.");
            System.out.println("7. Exit");
            System.out.println("\n.....................................");

            System.out.println("\nEnter Your choice:");
            int ch2 = input.nextInt();

            switch (ch2) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    printBooksDetails();
                    break;
                case 5:
                    printBooksInOrder(root);
                    break;
                case 6:
                    printTree(root, 0);
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    static void addBook() throws IOException {
        FileReader file = new FileReader("book.txt");
        BufferedReader reader = new BufferedReader(file);

        String line;
        while ((line = reader.readLine()) != null) {
            root = insert(root, line);
            hashmapping.put(line, hashmapping.size());
            arr[hashmapping.size()][0]++;
        }

        reader.close();

        FileReader file2 = new FileReader("num.txt");
        BufferedReader reader2 = new BufferedReader(file2);

        int index = 0;
        String number;
        while ((number = reader2.readLine()) != null) {
            int result = Integer.parseInt(number);
            arr[index++][1] = result;
        }

        reader2.close();

        FileReader file3 = new FileReader("num1.txt");
        BufferedReader reader3 = new BufferedReader(file3);

        index = 0;
        String number1;
        while ((number1 = reader3.readLine()) != null) {
            int result = Integer.parseInt(number1);
            arr[index++][1] += result;
        }

        reader3.close();

        System.out.println("\nEnter name of book:");
        String name = input.next();

        boolean exists = containsNode(root, name);
        if (exists) {
            System.out.println("\nThe book already exists.");
        } else {
            System.out.println("\nEnter quantity of book:");
            int quantity = input.nextInt();

            FileWriter fr = new FileWriter("append.txt", true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(name + "\n");
            br.write(quantity + "\n");

            br.close();

            root = insert(root, name);
            hashmapping.put(name, hashmapping.size());
            arr[hashmapping.size()][0] += quantity;
            arr[hashmapping.size()][1] += quantity;
        }
    }

    static void deleteBook() throws IOException {
        System.out.println("\nEnter name of book:");
        String b1 = input.next();

        boolean exists = containsNode(root, b1);
        if (exists) {
            root = deleteKey(root, b1);
            hashmapping.remove(b1);
        } else {
            System.out.println("\nThe book does not exist.");
        }
    }

    static void updateBook() {
        System.out.println("\nEnter name of book:");
        String b2 = input.next();

        boolean exists = containsNode(root, b2);
        if (exists) {
            int index = hashmapping.get(b2);
            System.out.println("\nEnter quantity of book to add more:");
            int q = input.nextInt();
            arr[index][0] += q;
        } else {
            System.out.println("\nThe book does not exist.");
        }
    }

    static void printBooksDetails() {
        Set<Map.Entry<String, Integer>> setOfEntries = hashmapping.entrySet();

        for (Map.Entry<String, Integer> entry : setOfEntries) {
            int r = entry.getValue();
            System.out.println("Name of book: " + entry.getKey());
            System.out.println("Total Quantity of book: " + arr[r][0]);
            System.out.println("Available Quantity of book: " + arr[r][1]);
            System.out.println();
        }
    }

    static void userLogin() throws IOException {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n.....................................");
            System.out.println("1. Issue book.");
            System.out.println("2. Return book.");
            System.out.println("3. Exit");
            System.out.println("\n.....................................");

            System.out.println("\nEnter Your choice:");
            int ch3 = input.nextInt();

            switch (ch3) {
                case 1:
                    issueBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    static void issueBook() {
        System.out.println("\nEnter name of book to issue:");
        String n1 = input.next();

        boolean exists = containsNode(root, n1);
        if (exists) {
            int index = hashmapping.get(n1);
            if (arr[index][1] > 0) {
                System.out.println("\nEnter id number of student to whom the book will be issued:");
                int id1 = input.nextInt();

                for (Student s : array) {
                    if (s.id_no == id1) {
                        System.out.println("\nName of student: " + s.name);
                        System.out.println("\nStream of student: " + s.Stream);

                        if (s.book_no == 0) {
                            s.book1 = n1;
                            s.book_no++;
                            arr[index][1]--;
                            arr[index][0]--;
                            cal.add(Calendar.DATE, 15);
                            System.out.println("\nBook issued successfully.");
                            System.out.println("Return Date: " + formatter.format(cal.getTime()));
                            return;
                        } else if (s.book_no == 1) {
                            s.book2 = n1;
                            s.book_no++;
                            arr[index][1]--;
                            arr[index][0]--;
                            cal.add(Calendar.DATE, 15);
                            System.out.println("\nBook issued successfully.");
                            System.out.println("Return Date: " + formatter.format(cal.getTime()));
                            return;
                        } else
                            System.out.println("\nStudent can take only two books.");
                        return;
                    }
                }
                System.out.println("\nStudent not found.");
            } else
                System.out.println("\nBook not available.");
        } else
            System.out.println("\nBook not available.");
    }

    static void returnBook() {
        System.out.println("\nEnter name of book to return:");
        String n2 = input.next();

        for (Student s : array) {
            if (s.book1 != null && s.book1.equals(n2)) {
                System.out.println("\nName of student: " + s.name);
                System.out.println("\nStream of student: " + s.Stream);

                s.book1 = null;
                s.book_no--;
                System.out.println("\nBook returned successfully.");
                arr[hashmapping.get(n2)][1]++;
                return;
            } else if (s.book2 != null && s.book2.equals(n2)) {
                System.out.println("\nName of student: " + s.name);
                System.out.println("\nStream of student: " + s.Stream);

                s.book2 = null;
                s.book_no--;
                System.out.println("\nBook returned successfully.");
                arr[hashmapping.get(n2)][1]++;
                return;
            }
        }
        System.out.println("\nBook not found.");
    }

    static Book insert(Book root, String key) {
        if (root == null) {
            root = new Book(key);
            return root;
        }

        if (key.compareTo(root.key) < 0)
            root.left = insert(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = insert(root.right, key);

        return root;
    }

    static Book deleteKey(Book root, String key) {
        if (root == null)
            return root;

        if (key.compareTo(root.key) < 0)
            root.left = deleteKey(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = deleteKey(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.key = minValue(root.right);
            root.right = deleteKey(root.right, root.key);
        }

        return root;
    }

    static String minValue(Book root) {
        String minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    static boolean containsNode(Book root, String key) {
        if (root == null)
            return false;

        if (key.compareTo(root.key) < 0)
            return containsNode(root.left, key);
        else if (key.compareTo(root.key) > 0)
            return containsNode(root.right, key);
        else
            return true;
    }

    static void printBooksInOrder(Book root) {
        if (root != null) {
            printBooksInOrder(root.left);
            System.out.println(root.key);
            printBooksInOrder(root.right);
        }
    }

    static void printTree(Book root, int space) {
        final int COUNT = 10;
        if (root != null) {
            printTree(root.right, space + COUNT);
            System.out.print("\n");
            for (int i = COUNT; i < space; i++)
                System.out.print(" ");
            System.out.print(root.key + "\n");
            printTree(root.left, space + COUNT);
        }
    }

    static void closeResources() throws IOException {
        input.close();
    }
}
