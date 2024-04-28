package utils;

import java.util.List;

public class ObservableData<T> implements Observable<T> {
    private final T dataHolder;
    private final List<Observer<T>> observers;

    @Override
    public void subscribe(Observer<T> observer){
        observers.add(observer);
        if(dataHolder != null) {
            observer.onNewValue(dataHolder);
        }
    }
    ObservableData(T dataHolder, List<Observer<T>> observers){
        this.dataHolder = dataHolder;
        this.observers = observers;
    }

}
