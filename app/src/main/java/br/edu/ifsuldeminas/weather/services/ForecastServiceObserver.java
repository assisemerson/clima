package br.edu.ifsuldeminas.weather.services;

import java.util.List;

import br.edu.ifsuldeminas.weather.domain.Forecast;

/**
 * Created by emerson on 5/22/16.
 */
public interface ForecastServiceObserver {

    void serviceDone(List<Forecast> forecasts);
}
