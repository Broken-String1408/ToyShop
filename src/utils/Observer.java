package utils;


public interface Observer<T> {
    void onNewValue(T value);
}
