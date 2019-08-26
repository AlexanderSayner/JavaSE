package sayner.sandbox.gruzchik.pattern.observer.impl;

import sayner.sandbox.gruzchik.pattern.observer.Observable;
import sayner.sandbox.gruzchik.pattern.observer.Observer;

import java.util.HashSet;
import java.util.Set;

public class WeatherData implements Observable {

    private Set<Observer> observers;
    private float temperature;
    private float humidity;
    private int pressure;

    public WeatherData() {
        this.observers = new HashSet<>();
    }

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(observer -> observer.update(this.temperature, this.humidity, this.pressure));
    }

    public void setMeasurements(float temperature, float humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
}
