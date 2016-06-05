package br.edu.ifsuldeminas.weather.services;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.weather.domain.City;
import br.edu.ifsuldeminas.weather.domain.Forecast;
import br.edu.ifsuldeminas.weather.parsers.CityParser;
import br.edu.ifsuldeminas.weather.parsers.ForecastParser;

/**
 * Created by emerson on 5/22/16.
 */
public class ForecastService extends AsyncTask<String, Integer, String>{
    private String serviceURL = "http://servicos.cptec.inpe.br/XML/cidade/7dias/city_id/previsao.xml";
    private List<ForecastServiceObserver> observers = new ArrayList<ForecastServiceObserver>();

    @Override
    protected String doInBackground(String... strings) {
        String cityId = "";

        for(String attr : strings){
            cityId = attr;
        }

        serviceURL = serviceURL.replace("city_id", cityId);

        try {
            return HttpHelper.doGet(serviceURL);
        } catch (IOException e) {
            // TODO tratar adequadamente erro de rede
            return "";
        }
    }

    public boolean register (ForecastServiceObserver observer){

        if (observer != null){
            observers.add(observer);
            return true;
        }

        return false;
    }

    @Override
    protected void onPostExecute(String s) {
        List<Forecast> forecasts = ForecastParser.getForecast(s);

        for(ForecastServiceObserver observer : observers){
            observer.serviceDone(forecasts);
        }
    }
}
