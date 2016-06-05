package br.edu.ifsuldeminas.weather.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by emerson on 5/21/16.
 */

public class City implements Serializable {
    private int id;
    private String name;
    private String uf;
    private String dateAAAAMMDD;
    private List<Forecast> forecasts;

    // Getters e Setter ...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDateAAAAMMDD() {
        return dateAAAAMMDD;
    }

    public void setDateAAAAMMDD(String dateAAAAMMDD) {
        this.dateAAAAMMDD = dateAAAAMMDD;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
