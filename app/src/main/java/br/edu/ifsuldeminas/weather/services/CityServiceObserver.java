package br.edu.ifsuldeminas.weather.services;

import java.util.List;

import br.edu.ifsuldeminas.weather.domain.City;

/**
 * Created by emerson on 5/22/16.
 */
public interface CityServiceObserver {

    void serviceDone(List<City> cities);
}
