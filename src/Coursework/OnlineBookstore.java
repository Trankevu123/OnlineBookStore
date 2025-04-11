package Coursework;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class ArraylistADT<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] elements;
    private int nextIndex;

    @SuppressWarnings("unchecked")
    public ArraylistADT() {
        elements = (T[]) new Object[DEFAULT_CAPACITY];
        nextIndex = 0;
    }

    public boolean add(T element) {
        if (nextIndex == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[nextIndex] = element;
        nextIndex++;
        return true;
    }

//    public boolean add(int index, T element) {
//        if (index < 0 || index >= nextIndex) {
//            throw new IndexOutOfBoundsException("Index out of Bound");
//        }
//
//        if (nextIndex == elements.length) {
//            elements = Arrays.copyOf(elements, elements.length * 2);
//        }
//
//        // Dịch phần tử sang phải để chèn phần tử mới
//        for (int i = nextIndex; i >= index; i--) {
//            elements[i] = elements[i - 1];
//        }
//
//        elements[index] = element;
//        nextIndex++;
//
//        return true;
//    }

    public T get(int index) {
        if (index < 0 || index >= nextIndex) {
            throw new IndexOutOfBoundsException("Index out of Bound");
        }
        return elements[index];
    }

    public T set(int index, T element) {
        if (index < 0 || index >= nextIndex) {
            throw new IndexOutOfBoundsException("Index out of Bound");
        }

        T oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    public T remove(int index) {
        if (index < 0 || index >= nextIndex) {
            throw new IndexOutOfBoundsException("Index out of Bound");
        }

        T oldElement = elements[index];

        // Dịch phần tử sang trái để lấp khoảng trống
        for (int i = index; i < nextIndex - 1; i++) {
            elements[i] = elements[i + 1];
        }

        nextIndex--; // Giảm size
        elements[nextIndex] = null; // Xóa phần tử cuối cùng

        // Nếu số phần tử còn lại ít hơn 1/3 kích thước, giảm kích thước mảng
        if (nextIndex < elements.length / 3) {
            elements = Arrays.copyOf(elements, elements.length / 2);
        }

        return oldElement;
    }


    public int size() {
        return nextIndex;
    }

//    public int indexOf(T element) {
//        for (int i = 0; i < nextIndex; i++) {
//            if (elements[i] == element) {
//                return i;
//            }
//        }
//        return -1;
//    }

//    public boolean contains(T element) {
//        for (var item : elements) {
//            if (item == element) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isEmpty() {
        return nextIndex == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < nextIndex; i++) {
            result.append(elements[i]);
            if (i < nextIndex - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}
class QueueADT<T> implements ServicequeueADT<T> {
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> front; // Điểm đầu của Queue (FIFO)
    private Node<T> rear;  // Điểm cuối của Queue
    private int size;

    public QueueADT() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    @Override
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (front == null) {
            System.out.println("️ The queue is empty, no elements..");
            return null;
        }
        T removedData = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return removedData;
    }

    @Override
    public T peek() {
        if (front == null) {
            System.out.println("️ The queue is empty");
            return null;
        }
        return front.data;
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T[] getAllOrders(T[] sampleArray) {
        if (front == null) {
            return Arrays.copyOf(sampleArray, 0); // Trả về mảng rỗng đúng kiểu
        }
        T[] orders = Arrays.copyOf(sampleArray, size); //  Dùng sampleArray làm mẫu
        Node<T> current = front;
        int index = 0;
        while (current != null) {
            orders[index++] = current.data;
            current = current.next;
        }
        return orders;
    }


    //  Duyệt qua tất cả đơn hàng trong Queue mà không dùng List
//    public void displayOrders() {
//        if (front == null) {
//            System.out.println(" Hàng đợi trống.");
//            return;
//        }
//        System.out.println("\n Danh sách đơn hàng trong hàng đợi:");
//        Node<T> current = front;
//        while (current != null) {
//            System.out.println(current.data); // In trực tiếp từng đơn hàng
//            current = current.next;
//        }
//    }

    //  Trả về đơn hàng đầu tiên mà không xóa nó
//    public T getFirstOrder() {
//        return peek();
//    }
}

class QueueCustomerOrder {
    private QueueADT<CustomerOrder> orderQueue;

    public QueueCustomerOrder() {
        this.orderQueue = new QueueADT<>();
    }



    //  Thêm đơn hàng vào hàng đợi
    public void addOrder(CustomerOrder order) {
        orderQueue.enqueue(order);
        System.out.println(" Order added #" + order.getOrderId() + " get in the queue.");
    }

    //  Xử lý đơn hàng (FIFO - First In First Out)


    //  Xem đơn hàng tiếp theo mà không xóa
//    public void viewNextOrder() {
//        CustomerOrder nextOrder = orderQueue.peek();
//        if (nextOrder != null) {
//            System.out.println(" Đơn hàng tiếp theo: #" + nextOrder.getOrderId());
//        }
//    }

    //  Hiển thị tất cả đơn hàng trong hàng đợi (duyệt Queue trực tiếp)
    public void displayOrders() {
        if (orderQueue.isEmpty()) {
            System.out.println("️ No orders.");
            return;
        }

        System.out.println("\n List of orders:");
        CustomerOrder[] orders = orderQueue.getAllOrders(new CustomerOrder[0]);

        for (CustomerOrder order : orders) {
            if (order.getStatus() == CustomerOrder.OrderStatus.CANCELLED_BY_CUSTOMER) {
                System.out.println(order + "  CUSTOMER CANCELED)");
            } else {
                System.out.println(order);
            }
        }
    }


    //  Trả về số lượng đơn hàng
//    public int getOrderCount() {
//        return orderQueue.size();
//    }

    public CustomerOrder searchOrder(String keyword) {
        CustomerOrder[] orders = orderQueue.getAllOrders(new CustomerOrder[0]); //  Truyền mẫu mảng rỗng

        for (CustomerOrder order : orders) {
            if (String.valueOf(order.getOrderId()).equals(keyword) || order.getCustomerName().equalsIgnoreCase(keyword)) {
                return order; //  Trả về đơn hàng tìm thấy
            }
        }
        return null;
    }

    //  Thêm phương thức dequeue() để lấy đơn hàng đầu tiên và xóa nó khỏi hàng đợi
    public CustomerOrder dequeue() {
        return orderQueue.dequeue();
    }

    //  Nếu cần lấy đơn hàng đầu tiên nhưng không xóa, dùng hàm này
    public CustomerOrder getFirstOrder() {
        return orderQueue.peek();
    }


}

class CustomerOrder {
    private int orderId;
    private String customerName;
    private String shippingAddress;
    private ArraylistADT<BookItem> bookItems; // Tự động dùng DEFAULT_CAPACITY = 5 từ ArraylistADT<T>
    private OrderStatus status;
    public enum OrderStatus {
        PENDING, PROCESSING, SHIPPED, DELIVERED,CANCELLED_BY_CUSTOMER
    }



    public CustomerOrder(int orderId, String customerName, String shippingAddress) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.bookItems = new ArraylistADT<>();
        this.status = OrderStatus.PENDING;
    }

    public int getOrderId() { return orderId; } //  Lấy ID đơn hàng

    public String getCustomerName() {
        return customerName;
    }


    public void addBook(BookItem book) {
        for (int i = 0; i < bookItems.size(); i++) {
            BookItem existing = bookItems.get(i);

            // So sánh theo bookId (có thể dùng title + author nếu muốn kỹ hơn)
            if (existing.getBookId() == book.getBookId()) {
                existing.setStock(existing.getStock() + book.getStock()); // Cộng dồn số lượng
                return; // Không cần thêm mới nữa
            }
        }
        bookItems.add(book);
    }



    public void sortBooks(String criteria) {
        int n = bookItems.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean swap = false;
                if (criteria.equalsIgnoreCase("title")) {
                    if (bookItems.get(j).getTitle().compareToIgnoreCase(bookItems.get(j + 1).getTitle()) > 0) {
                        swap = true;
                    }
                } else if (criteria.equalsIgnoreCase("price")) {
                    if (bookItems.get(j).getPrice() > bookItems.get(j + 1).getPrice()) {
                        swap = true;
                    }
                }

                if (swap) {
                    // Hoán đổi hai phần tử
                    BookItem temp = bookItems.get(j);
                    bookItems.set(j, bookItems.get(j + 1));
                    bookItems.set(j + 1, temp);
                }
            }
        }
        System.out.println("The list of books in the order has been sorted by " + criteria + ".");
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (int i = 0; i < bookItems.size(); i++) {
            BookItem book = bookItems.get(i);
            total += book.getPrice() * book.getStock();
        }
        return total;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Order #").append(orderId)
                .append(" - Customer: ").append(customerName)
                .append(" - Address: ").append(shippingAddress)
                .append(" - Status: ").append(status)
                .append("\n ListBook:\n");

        if (bookItems.isEmpty()) {
            result.append("️ No books in order.");
        } else {
            for (int i = 0; i < bookItems.size(); i++) {
                result.append("- ").append(bookItems.get(i)).append("\n");
            }
            // 👉 Thêm dòng hiển thị tổng tiền ở cuối
            result.append("Total ").append(calculateTotalPrice()).append(" VND");

        }

        return result.toString();
    }
    public ArraylistADT<BookItem> getBookItems() {
        return bookItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}

class BookItem{
    private int bookId;
    private String title;
    private double price;
    private String author;
    private int stock;


    public BookItem(int bookId, String title,String author, double price, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }
    public int getBookId() { return bookId; }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public void setTitle(String title) {this.title = title;}

    public void setPrice(double price) {this.price = price;}

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }



    @Override
    public String toString() {
        return bookId + ". " + title + " - Tác giả: " + author + " | " + price + " VND | Số lượng: " + stock;
    }
}

class BookManager implements ServiceArraylist<BookItem> {

    private ArraylistADT<BookItem> books = new ArraylistADT<>();
    private int nextId = 1; // ID bắt đầu từ 1

    @Override
    public void add(BookItem book) {
        // Làm sạch tiêu đề và tác giả (viết thường, loại bỏ khoảng trắng)
        String newTitle = book.getTitle().toLowerCase().replaceAll("\\s+", "");
        String newAuthor = book.getAuthor().toLowerCase().replaceAll("\\s+", "");

        for (int i = 0; i < books.size(); i++) {
            BookItem existingBook = books.get(i);
            String existingTitle = existingBook.getTitle().toLowerCase().replaceAll("\\s+", "");
            String existingAuthor = existingBook.getAuthor().toLowerCase().replaceAll("\\s+", "");

            if (newTitle.equals(existingTitle) && newAuthor.equals(existingAuthor)) {
                System.out.println(" Book already exists with same title and author. Cannot add duplicate.");
                return; // Không thêm nữa
            }
        }
        book.setBookId(nextId++);  // Gán ID tự động
        books.add(book);
        System.out.println(" Books added: " + book.getTitle() + " (ID: " + book.getBookId() + ")");
    }

    public void addSilently(BookItem book) {
        // Giống hệt add() nhưng không in System.out.println()
        String newTitle = book.getTitle().toLowerCase().replaceAll("\\s+", "");
        String newAuthor = book.getAuthor().toLowerCase().replaceAll("\\s+", "");

        for (int i = 0; i < books.size(); i++) {
            BookItem existingBook = books.get(i);
            String existingTitle = existingBook.getTitle().toLowerCase().replaceAll("\\s+", "");
            String existingAuthor = existingBook.getAuthor().toLowerCase().replaceAll("\\s+", "");

            if (newTitle.equals(existingTitle) && newAuthor.equals(existingAuthor)) {
                return; // Không thêm nếu trùng
            }
        }

        book.setBookId(nextId++);
        books.add(book);
    }




    @Override
    public void viewAll() {
        System.out.println("\n=== LIST OF BOOKS ===");
        if (books.isEmpty()) {
            System.out.println("The book list is empty.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i)); // Gọi toString() của BookItem
        }
    }

    @Override
    public BookItem search(int id) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() == id) return books.get(i);
        }
        return null;
    }

//    @Override
//    public BookItem search(String keyword) {
//        String cleanKeyword = keyword.toLowerCase().replaceAll("\\s+", "");
//
//        for (int i = 0; i < books.size(); i++) {
//            BookItem book = books.get(i);
//            String cleanTitle = book.getTitle().toLowerCase().replaceAll("\\s+", "");
//
//            if (cleanTitle.contains(cleanKeyword)) {
//                return book; //  Trả về sách đầu tiên tìm thấy
//            }
//        }
//        return null; //  Không tìm thấy sách
//    }

    public ArraylistADT<BookItem> searchAll(String keyword) {
        ArraylistADT<BookItem> results = new ArraylistADT<>();
        String cleanKeyword = keyword.toLowerCase().replaceAll("\\s+", "");

        for (int i = 0; i < books.size(); i++) {
            BookItem book = books.get(i);
            String cleanTitle = book.getTitle().toLowerCase().replaceAll("\\s+", "");
            String cleanAuthor = book.getAuthor().toLowerCase().replaceAll("\\s+", "");

            if (cleanTitle.contains(cleanKeyword) || cleanAuthor.contains(cleanKeyword)) {
                results.add(book); // thêm tất cả sách có tiêu đề khớp
            }
        }
        return results;
    }

    @Override
    public void update(int id, BookItem book) {
        BookItem found = search(id);
        if (found != null) {
            found.setStock(book.getStock());
            System.out.println("Update number of books successfully.");
        } else {
            System.out.println("No books found.");
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() == id) {
                books.remove(i);
                System.out.println("Deleted book with ID: " + id);
                reassignBookIds();
                return;
            }
        }
        System.out.println("No books found.");
    }

    public void reassignBookIds() {
        for (int i = 0; i < books.size(); i++) {
            books.get(i).setBookId(i + 1);
        }
        nextId = books.size() + 1;
    }
    public ArraylistADT<BookItem> getBooks() {
        return books;
    }
}


class LinkedStackADT<T>implements ServiceStackADT<T> {
    @Override
    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.next = top;
        top = newNode;
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack empty.");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack empty.");
        }
        return top.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    private Node<T> top;
    private int size;

    public LinkedStackADT() {
        this.top = null;
        this.size = 0;
    }
}
class Transaction {
    private String action;
    private String details;

    public Transaction(String action, String details) {
        this.action = action;
        this.details = details;
    }

    @Override
    public String toString() {
        return action + ": " + details;
    }
}



public class OnlineBookstore {
    private static Scanner scanner = new Scanner(System.in);
    private static QueueCustomerOrder orderManager = new QueueCustomerOrder();

    private static LinkedStackADT<Transaction> transactionHistory = new LinkedStackADT<>();

    private static BookManager bookManager = new BookManager();



    private static void addDefaultBooks() {
        bookManager.addSilently(new BookItem(1, "Java", "James Gosling", 250000, 5));
        bookManager.addSilently(new BookItem(2, "Data", "Hadoop Expert", 300000, 3));
        bookManager.addSilently(new BookItem(3, "Python", "Guido van Rossum", 280000, 4));
    }


    public static void main(String[] args) {
        //  Thêm sách mặc định
          addDefaultBooks();
//


        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== ONLINE BOOK SALES SYSTEM =====");
            System.out.println("1. Customer");
            System.out.println("2. Administrator");
            System.out.println("0. Exit");
            System.out.print("Select function: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    customerMenu();
                    break;
                case "2":
                    adminMenu();
                    break;
                case "0":
                    exit = true;
                    System.out.println(" Exit the program...");
                    break;
                default:
                    System.out.println(" Invalid selection.");
            }
        }
    }

    private static void customerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Order");
            System.out.println("2. Look up orders");
            System.out.println("3. Cancel order");
            System.out.println("0. Back");
            System.out.print("Select function: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    placeOrder();
                    break;
                case "2":
                    searchOrder();
                    break;
                case "3":
                    cancelOrder(); // chưa hỗ trợ xoá thực tế
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("️ Invalid selection.");
            }
        }
    }

    private static void adminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View order list");
            System.out.println("2. Order approval (processing)");
            System.out.println("3. Book management");
            System.out.println("4. View transaction history");
            System.out.println("0. Back");
            System.out.print("Select function: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    orderManager.displayOrders();
                    break;
                case "2":
                    processOrder();;
                    break;
                case "3":
                    manageBooks();
                    break;
                case "4":
                    viewTransactionHistory();
                    break;

                case "0":
                    back = true;
                    break;
                default:
                    System.out.println(" Invalid selection.");
            }
        }
    }

    private static void placeOrder() {
        String name;
        while (true) {
            System.out.print("Enter the customer's name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty() && !name.matches("\\d+")) break;
            System.out.println(" Customer name cannot be blank or a number!");
        }
        String address;
        while (true) {
            System.out.print("Enter shipping address: ");
            address = scanner.nextLine().trim();
            if (!address.isEmpty() && !address.matches("\\d+")) break;
            System.out.println(" Address cannot be blank or a number!");
        }
        int orderId = new Random().nextInt(900) + 100; // tạo ID ngẫu nhiên
        CustomerOrder newOrder = new CustomerOrder(orderId, name, address);

        //  Lấy danh sách sách hiện có
        ArraylistADT<BookItem> books = bookManager.getBooks();
        if (books.isEmpty()) {
            System.out.println(" No books in stock!");
            return;
        }

        System.out.println("\n List of books available:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i)); // Hiển thị danh sách sách
        }

        boolean addingBooks = true;
        while (addingBooks) {
            int choice;

            // Nhập số thứ tự sách (kèm kiểm tra lỗi nhập)
            while (true) {
                System.out.print("\nEnter the book number (or 0 to end): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println(" Please do not leave blank.");
                    continue;
                }

                try {
                    choice = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter numbers only, no letters allowed.");
                }
            }

            if (choice == 0) break;

            if (choice < 1 || choice > books.size()) {
                System.out.println(" Invalid selection.");
                continue;
            }

            BookItem selectedBook = books.get(choice - 1);
            System.out.println("You have chosen: " + selectedBook.getTitle());

            int quantity;
            while (true) {
                System.out.print("Enter quantity: ");
                String inputQty = scanner.nextLine().trim();
                if (inputQty.isEmpty()) {
                    System.out.println(" Quantity cannot be left blank.");
                    continue;
                }
                try {
                    quantity = Integer.parseInt(inputQty);
                    if (quantity > 0 && quantity <= selectedBook.getStock()) {
                        break;
                    } else {
                        System.out.println(" Invalid quantity. Only left " + selectedBook.getStock() + " book.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter a valid number.");
                }
            }

            //  Cập nhật số lượng tồn kho
            selectedBook.setStock(selectedBook.getStock() - quantity);
            newOrder.addBook(new BookItem(selectedBook.getBookId(), selectedBook.getTitle(),selectedBook.getAuthor(), selectedBook.getPrice(), quantity));

            System.out.println(" Added " + quantity + " book " + selectedBook.getTitle() + " to queue.");
        }

        //  Kiểm tra nếu đơn hàng có sách thì mới thêm vào hàng đợi
        if (!newOrder.getBookItems().isEmpty()) {
            //  Sử dụng hàm sortBooks() để sắp xếp trước khi thêm vào hàng đợi
            newOrder.sortBooks("title");  // Có thể thay đổi "title" thành "price" nếu cần

            orderManager.addOrder(newOrder);
            System.out.println(" Order successful! Order has been arranged.");
            transactionHistory.push(new Transaction("Order", "Customer: " + name + ", Order #" + orderId));

        } else {
            System.out.println(" Order is empty, cannot be placed.");
        }
    }



    private static void searchOrder() {
        System.out.print("Enter order code: ");
        String keyword = scanner.nextLine();
        CustomerOrder found = orderManager.searchOrder(keyword);
        if (found != null) {
            //found.sortBooks("title");
            System.out.println("\n Order found:");
            System.out.println(found);
        } else {
            System.out.println(" Order not found.");
        }
    }

    private static void cancelOrder() {
        System.out.print("Enter order code to cancel: ");
        String keyword = scanner.nextLine();
        CustomerOrder found = orderManager.searchOrder(keyword);

        if (found != null) {
            if (found.getStatus() == CustomerOrder.OrderStatus.PROCESSING) {
                System.out.println(" Order is being processed, cannot be cancelled.");
                return;
            }

            //  Hiển thị chi tiết đơn hàng trước khi xác nhận
            System.out.println("\n Order details:");
            System.out.println(found);

            System.out.print("Are you sure you want to cancel this order? (y/n): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                // Đánh dấu trạng thái đơn hàng bị hủy
                found.setStatus(CustomerOrder.OrderStatus.CANCELLED_BY_CUSTOMER);

                // Hoàn sách về kho
                restoreBookStock(found);

                System.out.println(" Order #" + found.getOrderId() + " has been canceled and the book has been returned to stock..");
                transactionHistory.push(new Transaction("Cancel order", "Order #" + found.getOrderId() + ", Customer: " + found.getCustomerName()));
            } else {
                System.out.println("Cancel order cancellation.");
            }
        } else {
            System.out.println(" Order not found.");
        }
    }




    private static void manageBooks() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== BOOK MANAGEMENT =====");
            System.out.println("1. Add Book");
            System.out.println("2. View List Books");
            System.out.println("3. Find Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("0. Back");
            System.out.print("Select function: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    String title;
                    while (true) {
                        System.out.print("Title: ");
                        title = scanner.nextLine().trim();
                        if (!title.isEmpty() && !title.matches("\\d+")) break;
                        System.out.println(" Title cannot be blank or start with a number!");
                    }
                    String author;
                    while (true) {
                        System.out.print("Author: ");
                        author = scanner.nextLine().trim();
                        if (!author.isEmpty() && !author.matches("\\d+")) break;
                        System.out.println(" Author name cannot be blank Or start with a number!");
                    }
                    double price;
                    while (true) {
                        System.out.print("Price: ");
                        String priceInput = scanner.nextLine().trim();
                        if (priceInput.isEmpty()) {
                            System.out.println(" Price cannot be left blank!");
                            continue;
                        }
                        try {
                            price = Double.parseDouble(priceInput);
                            if (price >= 0) break;
                            System.out.println(" Price must be greater than or equal to 0.");
                        } catch (NumberFormatException e) {
                            System.out.println(" Price is not valid.");
                        }
                    }
                    int stock;
                    while (true) {
                        System.out.print("Quantity: ");
                        String stockInput = scanner.nextLine().trim();
                        if (stockInput.isEmpty()) {
                            System.out.println(" Quantity cannot be left blank!");
                            continue;
                        }
                        try {
                            stock = Integer.parseInt(stockInput);
                            if (stock > 0) break;
                            System.out.println(" Quantity must be greater than 0.");
                        } catch (NumberFormatException e) {
                            System.out.println(" Invalid quantity.");
                        }
                    }

                    //  ID sẽ tự gán trong BookManager
                    bookManager.add(new BookItem(0, title,author, price, stock));
                    break;
                case "2":
                    bookManager.viewAll();
                    break;
                case "3":
                    System.out.print("Enter ID or book name: ");
                    String keyword = scanner.nextLine();

                    if (keyword.matches("\\d+")) {
                        // Tìm theo ID
                        BookItem book = bookManager.search(Integer.parseInt(keyword));
                        if (book != null) {
                            System.out.println(" Found books: " + book);
                        } else {
                            System.out.println(" No books found.");
                        }
                    } else {
                        // Tìm tất cả sách theo tên
                        ArraylistADT<BookItem> foundBooks = bookManager.searchAll(keyword);
                        if (foundBooks.isEmpty()) {
                            System.out.println(" No books found.");
                        } else {
                            System.out.println(" Books found:");
                            for (int i = 0; i < foundBooks.size(); i++) {
                                System.out.println("- " + foundBooks.get(i));
                            }
                        }
                    }
                    break;
                case "4":
                    ArraylistADT<BookItem> booksToUpdate = bookManager.getBooks();
                    if (booksToUpdate.isEmpty()) {
                        System.out.println(" There are no books to update.");
                        break;
                    }

                    System.out.println("\n List of available books:");
                    for (int i = 0; i < booksToUpdate.size(); i++) {
                        System.out.println((i + 1) + ". " + booksToUpdate.get(i));
                    }

                    int choiceToUpdate = -1;
                    while (true) {
                        System.out.print("Select the book number you want to update (or 0 to cancel): ");
                        String input = scanner.nextLine();
                        try {
                            choiceToUpdate = Integer.parseInt(input);
                            if (choiceToUpdate == 0) {
                                System.out.println(" Update operation cancelled.");
                                break;
                            }
                            if (choiceToUpdate < 1 || choiceToUpdate > booksToUpdate.size()) {
                                System.out.println(" Invalid selection.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(" Please enter a valid number.");
                        }
                    }

                    if (choiceToUpdate == 0) break;

                    BookItem bookToUpdate = booksToUpdate.get(choiceToUpdate - 1);
                    System.out.println(" Books are selected: " + bookToUpdate);

                    //  Cập nhật tiêu đề
                    String newTitle = "";
                    while (true) {
                        System.out.print("Enter new title (leave blank if not changed): ");
                        newTitle = scanner.nextLine().trim();

                        // Nếu bỏ trống thì giữ nguyên
                        if (newTitle.isEmpty()) break;

                        // Nếu nhập toàn số → không hợp lệ
                        if (newTitle.matches("\\d+")) {
                            System.out.println(" Title cannot contain only numbers. Please re-enter!");
                        } else {
                            bookToUpdate.setTitle(newTitle);
                            break; // Hợp lệ → thoát vòng lặp
                        }
                    }


                    // Cập nhật tác giả
                    String newAuthor = "";
                    while (true) {
                        System.out.print("Enter new author name (leave blank if not changed): ");
                        newAuthor = scanner.nextLine().trim();

                        if (newAuthor.isEmpty()) break;

                        if (newAuthor.matches("\\d+")) {
                            System.out.println(" Author name cannot contain only numbers. Please re-enter!");
                        } else {
                            bookToUpdate.setAuthor(newAuthor);
                            break;
                        }
                    }



                    //  Cập nhật giá
                    System.out.print("Enter new price (leave blank if not changed): ");
                    String priceInput = scanner.nextLine();
                    if (!priceInput.trim().isEmpty()) {
                        try {
                            double newPrice = Double.parseDouble(priceInput);
                            bookToUpdate.setPrice(newPrice);
                        } catch (NumberFormatException e) {
                            System.out.println(" Price is not valid.");
                        }
                    }

                    //  Cập nhật số lượng
                    System.out.print("Enter new quantity (leave blank if unchanged): ");
                    String stockInput = scanner.nextLine();
                    if (!stockInput.trim().isEmpty()) {
                        try {
                            int newStock = Integer.parseInt(stockInput);
                            bookToUpdate.setStock(newStock);
                        } catch (NumberFormatException e) {
                            System.out.println(" Invalid quantity.");
                        }
                    }

                    System.out.println("  Book update successful!");
                    break;

                case "5":
                    if (bookManager.getBooks().isEmpty()) {
                        System.out.println(" There are no books to delete.");
                        break;
                    }

                    System.out.println("\n Danh sách sách hiện có:");
                    ArraylistADT<BookItem> allBooks = bookManager.getBooks();
                    for (int i = 0; i < allBooks.size(); i++) {
                        System.out.println((i + 1) + ". " + allBooks.get(i));
                    }

                    int choiceToDelete = -1;
                    while (true) {
                        System.out.print("Select the book number you want to delete (or 0 to cancel): ");
                        String input = scanner.nextLine();
                        try {
                            choiceToDelete = Integer.parseInt(input);
                            if (choiceToDelete == 0) {
                                System.out.println(" Delete operation canceled.");
                                break;
                            }
                            if (choiceToDelete < 1 || choiceToDelete > allBooks.size()) {
                                System.out.println(" Invalid selection.");
                            } else {
                                BookItem bookToDelete = allBooks.get(choiceToDelete - 1);
                                System.out.println(" Are you sure you want to delete the book: " + bookToDelete + " ? (y/n)");
                                String confirm = scanner.nextLine();
                                if (confirm.equalsIgnoreCase("y")) {
                                    bookManager.delete(bookToDelete.getBookId());
                                } else {
                                    System.out.println(" Cancel the delete operation.");
                                }
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(" Please enter a valid number.");
                        }
                    }
                    break;

                case "0":
                    back = true;
                    break;
                default:
                    System.out.println(" Invalid selection.");
            }
        }
    }
    private static void processOrder() {
        CustomerOrder nextOrder = orderManager.getFirstOrder(); // Lấy đơn hàng đầu tiên nhưng không xóa
        if (nextOrder == null) {
            System.out.println("️ There are no orders to process..");
            return;
        }

        boolean processing = true;
        while (processing) {
            System.out.println("\n Order details to be processed:");
            System.out.println(nextOrder);

            System.out.println("\nAction:");
            System.out.println("1. Change order status");
            System.out.println("2. Delete order (if customer has canceled)");
            System.out.println("3. Back");
            System.out.print("Select: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    updateOrderStatus(nextOrder);
                    if (nextOrder.getStatus() == CustomerOrder.OrderStatus.DELIVERED) {
                        orderManager.dequeue();
                        System.out.println(" The order has been delivered and deleted from the system..");
                        processing = false;
                    }
                    break;
                case "2":
                    System.out.print("Are you sure you want to delete this order? (y/n): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("y")) {
                        //  Trả sách về kho nếu đơn chưa bị khách huỷ
                        if (nextOrder.getStatus() != CustomerOrder.OrderStatus.CANCELLED_BY_CUSTOMER) {
                            restoreBookStock(nextOrder);
                        }

                        orderManager.dequeue();
                        System.out.println(" The order has been deleted from the system..");
                        transactionHistory.push(new Transaction("Admin deletes the order", "Order #" + nextOrder.getOrderId()));
                    } else {
                        System.out.println(" Cancel the delete operation.");
                    }
                    processing = false;
                    break;

                case "3":
                    processing = false;
                    break;
                default:
                    System.out.println(" Invalid selection.");
            }
        }
    }

    private static void restoreBookStock(CustomerOrder order) {
        for (int i = 0; i < order.getBookItems().size(); i++) {
            BookItem orderedBook = order.getBookItems().get(i);

            //  Lấy sách từ kho theo ID
            BookItem stockBook = bookManager.search(orderedBook.getBookId());

            if (stockBook != null) {
                //  Cộng lại số lượng sách đã mua
                stockBook.setStock(stockBook.getStock() + orderedBook.getStock());
                System.out.println(" Refunded " + orderedBook.getStock() + " Book " + orderedBook.getTitle() + " into the warehouse.");
            }
        }
    }



    private static void updateOrderStatus(CustomerOrder order) {
        switch (order.getStatus()) {
            case PENDING:
                order.setStatus(CustomerOrder.OrderStatus.PROCESSING);
                System.out.println(" Order status changed to: PROCESSING");
                transactionHistory.push(new Transaction("Update status", "Order #" + order.getOrderId() + " → " + order.getStatus()));

                break;
            case PROCESSING:
                order.setStatus(CustomerOrder.OrderStatus.SHIPPED);
                System.out.println(" Order status changed to: SHIPPED");
                transactionHistory.push(new Transaction("Update status", "Order #" + order.getOrderId() + " → " + order.getStatus()));

                break;
            case SHIPPED:
                order.setStatus(CustomerOrder.OrderStatus.DELIVERED);
                System.out.println(" Order status changed to: DELIVERED");
                transactionHistory.push(new Transaction("Update status", "Order #" + order.getOrderId() + " → " + order.getStatus()));

                break;
            case DELIVERED:
                System.out.println(" Order Completed.");
                transactionHistory.push(new Transaction("Update status", "Order #" + order.getOrderId() + " → " + order.getStatus()));

                break;
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\n===== TRANSACTION HISTORY =====");
        if (transactionHistory.isEmpty()) {
            System.out.println(" No transactions have been recorded yet..");
            return;
        }

        // In từ mới nhất đến cũ nhất
        LinkedStackADT<Transaction> temp = new LinkedStackADT<>();

        while (!transactionHistory.isEmpty()) {
            Transaction t = transactionHistory.pop();
            System.out.println("- " + t);
            temp.push(t); // Lưu lại để khôi phục stack gốc
        }

        while (!temp.isEmpty()) {
            transactionHistory.push(temp.pop());
        }
    }

}











