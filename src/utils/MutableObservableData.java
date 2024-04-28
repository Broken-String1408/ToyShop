package utils;

import java.util.ArrayList;
import java.util.List;

public class MutableObservableData<T> implements Observable<T> {
    private T dataHolder;
    private final List<Observer<T>> observers = new ArrayList<>();

    @Override
    public void subscribe(Observer<T> observer){
        observers.add(observer);
        if(dataHolder != null) {
            observer.onNewValue(dataHolder);
        }
    }
    public void postValue(T value){
        dataHolder = value;
        for (Observer<T> observer : observers) {
            observer.onNewValue(value);
        }
    }

    public T getValue(){
        return dataHolder;
    }

    //Preventing casting or if no interface in use
    public ObservableData<T> asObservableData(){
        return new ObservableData<>(dataHolder, observers);
    }
}
