package Coursework;

public class PerformanceTest {
    public static void main(String[] args) {
        // Test ArraylistADT add
        ArraylistADT<Integer> list = new ArraylistADT<>();
        int N = 10000;
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long end = System.nanoTime();
        System.out.println("ArraylistADT add() x " + N + " = " + (end - start)/1000000 + " ms");

        // Test ArraylistADT remove
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            list.remove(0); // remove from beginning → worst-case
        }
        end = System.nanoTime();
        System.out.println("ArraylistADT remove(0) x 1000 = " + (end - start)/1000000 + " ms");

        // Test QueueADT enqueue + dequeue
        QueueADT<Integer> queue = new QueueADT<>();
        start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            queue.enqueue(i);
        }
        end = System.nanoTime();
        System.out.println("QueueADT enqueue x " + N + " = " + (end - start)/1000000 + " ms");

        start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            queue.dequeue();
        }
        end = System.nanoTime();
        System.out.println("QueueADT dequeue x " + N + " = " + (end - start)/1000000 + " ms");

        // Test Stack push + pop
        LinkedStackADT<Integer> stack = new LinkedStackADT<>();
        start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            stack.push(i);
        }
        end = System.nanoTime();
        System.out.println("LinkedStackADT push x " + N + " = " + (end - start)/1000000 + " ms");

        start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            stack.pop();
        }
        end = System.nanoTime();
        System.out.println("LinkedStackADT pop x " + N + " = " + (end - start)/1000000 + " ms");

        // Test bubble sort trong CustomerOrder
        CustomerOrder order = new CustomerOrder(1, "Test", "Test");
        for (int i = 0; i < 100; i++) {
            order.addBook(new BookItem(i, "Book " + (100 - i), "Author", 100 + i, 1));
        }

        start = System.nanoTime();
        order.sortBooks("title");
        end = System.nanoTime();
        System.out.println("Bubble Sort (title) on 100 books = " + (end - start)/1000000 + " ms");

        // Test BookManager.searchAll
        BookManager manager = new BookManager();
        for (int i = 0; i < 5000; i++) {
            manager.addSilently(new BookItem(i, "Book " + i, "Author", 100 + i, 10));
        }

        start = System.nanoTime();
        manager.searchAll("book 4999");
        end = System.nanoTime();
        System.out.println("Linear search in BookManager (5000 books) = " + (end - start)/1000000 + " ms");
    }
}
