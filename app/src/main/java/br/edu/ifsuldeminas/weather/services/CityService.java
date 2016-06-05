package br.edu.ifsuldeminas.weather.services;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.weather.domain.City;
import br.edu.ifsuldeminas.weather.parsers.CityParser;

/**
 * Created by emerson on 5/22/16.
 */
public class CityService extends AsyncTask<String, Integer, String>{
    private String serviceURL = "http://servicos.cptec.inpe.br/XML/listaCidades?city=city_name";
    private List<CityServiceObserver> observers = new ArrayList<CityServiceObserver>();

    @Override
    protected String doInBackground(String... strings) {
        // Uma cidade padrão se não receber nenhuma
        String city = "Sao Paulo";

        for(String attr : strings){
            city = attr;
        }

        serviceURL = serviceURL.replace("city_name", city);
        serviceURL = serviceURL.replace(" ", "%20");

        try {
            return HttpHelper.doGet(serviceURL);
        } catch (IOException e) {
            // TODO tratar adequadamente erro de rede
            return "";
        }
    }

    public boolean register (CityServiceObserver observer){

        if (observer != null){
            observers.add(observer);
            return true;
        }

        return false;
    }

    @Override
    protected void onPostExecute(String s) {
        List<City> cities = CityParser.getCities(s);

        for(CityServiceObserver observer : observers){
            observer.serviceDone(cities);
        }
    }
}
