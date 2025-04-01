package Coursework;

public interface ServicequeueADT<T> {
    void enqueue(T element); // Thêm phần tử vào hàng đợi
    T dequeue(); // Lấy phần tử ra khỏi hàng đợi
    T peek(); // Xem phần tử đầu tiên nhưng không xóa
    boolean isEmpty(); // Kiểm tra hàng đợi có rỗng không
    int size(); // Trả về số lượng phần tử trong hàng đợi
    T[] getAllOrders(T[] sampleArray);
}
