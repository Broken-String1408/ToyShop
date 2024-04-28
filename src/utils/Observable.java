package utils;

public interface Observable<T>{
    void subscribe(Observer<T> observer);


}
