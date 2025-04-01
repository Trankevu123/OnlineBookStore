package Coursework;

public interface ServiceArraylist<T> {
    void add(T item);
    void viewAll();
    T search(int id);

//    T search(String keyword);
    void update(int id, T item);
    void delete(int id);
}
