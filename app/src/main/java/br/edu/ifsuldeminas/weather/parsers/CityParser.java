package br.edu.ifsuldeminas.weather.parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.util.List;

import br.edu.ifsuldeminas.weather.domain.City;

/**
 * Created by emerson on 5/22/16.
 */

public class CityParser {

    public static List<City> getCities(String s) {
        List<City> cities = new ArrayList<City>();

        try{
            XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactory.newPullParser();
            InputStream is = new ByteArrayInputStream(s.getBytes("ISO-8859-1"));
            parser.setInput(is, null);

            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT)
            {
                if (event == XmlPullParser.START_TAG) {
                    City c = new City();

                    if (parser.getName().equals("cidade")){
                        event = parser.next();

                        while (!(event == XmlPullParser.END_TAG && parser.getName().equals("cidade"))){
                            if (event == XmlPullParser.START_TAG){
                                String tag = parser.getName();

                                event = parser.next();
                                if (event == XmlPullParser.TEXT){
                                    if (tag.equals("nome")){
                                        String name = new String(parser.getText().getBytes(), "UTF-8");
                                        c.setName(name);
                                    }

                                    if (tag.equals("uf"))
                                        c.setUf(parser.getText());

                                    if (tag.equals("id"))
                                        c.setId(Integer.parseInt(parser.getText()));
                                }
                            }

                            event = parser.next();
                        }

                        cities.add(c);
                    }
                }

                event = parser.next();
            }

        }catch (Exception e) { /* TODO: fazer algo se der erro de parser */ }
        finally {
            return cities;
        }
    }
}
