package br.edu.ifsuldeminas.weather.parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.weather.domain.Forecast;

/**
 * Created by emerson on 5/22/16.
 */
public class ForecastParser {

    public static List<Forecast> getForecast(String s) {
        List<Forecast> forecasts = new ArrayList<Forecast>();

        try{
            XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactory.newPullParser();
            InputStream is = new ByteArrayInputStream(s.getBytes("ISO-8859-1"));
            parser.setInput(is, null);

            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT)
            {
                if (event == XmlPullParser.START_TAG) {
                    Forecast f = new Forecast();

                    if (parser.getName().equals("previsao")){
                        event = parser.next();

                        while (!(event == XmlPullParser.END_TAG && parser.getName().equals("previsao"))){
                            if (event == XmlPullParser.START_TAG){
                                String tag = parser.getName();

                                event = parser.next();
                                if (event == XmlPullParser.TEXT){
                                    if (tag.equals("dia"))
                                        f.setDayAAAAMMDD(parser.getText());

                                    if (tag.equals("tempo"))
                                        f.setWeather(parser.getText());

                                    if (tag.equals("maxima"))
                                        f.setMaxTemperature(parser.getText());

                                    if (tag.equals("minima"))
                                        f.setMinTemperature(parser.getText());

                                    if (tag.equals("minima"))
                                        f.setMinTemperature(parser.getText());

                                    if (tag.equals("iuv"))
                                        f.setIuv(parser.getText());
                                }
                            }

                            event = parser.next();
                        }

                        forecasts.add(f);
                    }
                }

                event = parser.next();
            }

        }catch (Exception e) { /* TODO: fazer algo se der erro de parser */ }
        finally {
            return forecasts;
        }
    }
}
