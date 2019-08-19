package sayner.sandbox.gruzchik.pattern.observer.impl;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.gruzchik.pattern.observer.Observer;

@Log4j2
public class CurrentConditionsDisplay implements Observer {

    private float temperature;
    private float humidity;
    private int pressure;
    private WeatherData weatherData;

    public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }

    public void display() {
        log.info(String.format("Сейчас значения:%.1f градусов цельсия и %.1f %% влажности. Давление %d мм рт. ст.\n", temperature, humidity, pressure));
    }
}
