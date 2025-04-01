package Coursework;

public interface ServiceStackADT<T>  {
    void push(T element);
    T pop();
    T peek();
    int size();
    boolean isEmpty();
}
