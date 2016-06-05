package br.edu.ifsuldeminas.weather.adapters;

/**
 * Created by emerson on 5/21/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.edu.ifsuldeminas.weather.R;
import br.edu.ifsuldeminas.weather.domain.Forecast;

public class ForecastAdapter extends
        RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private final List<Forecast> forecasts;
    private final Context context;
    private ForecastOnClickListener forecastOnClickListener;

    public static final HashMap<Integer, Integer> daysOfWeek = new HashMap(){
        {
            put(1, R.string.sunday);
            put(2, R.string.monday);
            put(3, R.string.tuesday);
            put(4, R.string.wednesday);
            put(5, R.string.thursday);
            put(6, R.string.friday);
            put(7, R.string.saturday);
        }
    };

    public static final HashMap<String, Integer> forecastImages = new HashMap(){
        {
            put("ec", R.drawable.ec);
            put("ci", R.drawable.ci);
            put("c", R.drawable.c);
            put("in", R.drawable.in);
            put("pp", R.drawable.pp);
            put("cm", R.drawable.cm);
            put("cn", R.drawable.cn);
            put("pt", R.drawable.pt);
            put("pm", R.drawable.pm);
            put("np", R.drawable.np);
            put("pc", R.drawable.pc);
            put("pn", R.drawable.pn);
            put("cv", R.drawable.cv);
            put("ch", R.drawable.ch);
            put("t", R.drawable.t);
            put("ps", R.drawable.ps);
            put("e", R.drawable.e);
            put("n", R.drawable.n);
            put("cl", R.drawable.cl);
            put("nv", R.drawable.nv);
            put("g", R.drawable.g);
            put("ne", R.drawable.ne);
            put("nd", R.drawable.nd);
            put("pnt", R.drawable.pnt);
            put("psc", R.drawable.psc);
            put("pcm", R.drawable.pcm);
            put("pct", R.drawable.pct);
            put("pcn", R.drawable.pcn);
            put("npt", R.drawable.npt);
            put("npn", R.drawable.npn);
            put("ncn", R.drawable.ncn);
            put("nct", R.drawable.nct);
            put("ncm", R.drawable.ncm);
            put("npm", R.drawable.npm);
            put("npp", R.drawable.npp);
            put("vn", R.drawable.vn);
            put("ct", R.drawable.ct);
            put("ppn", R.drawable.ppn);
            put("ppt", R.drawable.ppt);
            put("ppm", R.drawable.ppm);
        }
    };

    public static final HashMap<String, Integer> forecastText = new HashMap(){
        {
            put("ec", R.string.ec);
            put("ci", R.string.ci);
            put("c", R.string.c);
            put("in", R.string.in);
            put("pp", R.string.pp);
            put("cm", R.string.cm);
            put("cn", R.string.cn);
            put("pt", R.string.pt);
            put("pm", R.string.pm);
            put("np", R.string.np);
            put("pc", R.string.pc);
            put("pn", R.string.pn);
            put("cv", R.string.cv);
            put("ch", R.string.ch);
            put("t", R.string.t);
            put("ps", R.string.ps);
            put("e", R.string.e);
            put("n", R.string.n);
            put("cl", R.string.cl);
            put("nv", R.string.nv);
            put("g", R.string.g);
            put("ne", R.string.ne);
            put("nd", R.string.nd);
            put("pnt", R.string.pnt);
            put("psc", R.string.psc);
            put("pcm", R.string.pcm);
            put("pct", R.string.pct);
            put("pcn", R.string.pcn);
            put("npt", R.string.npt);
            put("npn", R.string.npn);
            put("ncn", R.string.ncn);
            put("nct", R.string.nct);
            put("ncm", R.string.ncm);
            put("npm", R.string.npm);
            put("npp", R.string.npp);
            put("vn", R.string.vn);
            put("ct", R.string.ct);
            put("ppn", R.string.ppn);
            put("ppt", R.string.ppt);
            put("ppm", R.string.ppm);
        }
    };

    public ForecastAdapter(Context context, List<Forecast> forecasts, ForecastOnClickListener forecastOnClickListener) {
        this.context = context;
        this.forecasts = forecasts;
        this.forecastOnClickListener = forecastOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.forecasts != null ? this.forecasts.size() : 0;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_adapter, viewGroup, false);
        ForecastViewHolder holder = new ForecastViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ForecastViewHolder forecastHolder, final int position) {

        Forecast forecast = forecasts.get(position);

        int imageId = forecastImages.get("nd");
        if (forecastImages.containsKey(forecast.getWeather()))
            imageId = forecastImages.get(forecast.getWeather());

        forecastHolder.image.setImageResource(imageId);
        forecastHolder.day.setText(forecast.getDay());
        forecastHolder.month.setText(forecast.getMonth());
        forecastHolder.year.setText(forecast.getYear());

        int textId = forecastText.get("nd");
        if (forecastText.containsKey(forecast.getWeather()))
            textId = forecastText.get(forecast.getWeather());

        forecastHolder.weather.setText(forecastHolder.view.getResources().getText(textId));
        forecastHolder.maxTemperature.setText(forecast.getMaxTemperature());
        forecastHolder.minTemperature.setText(forecast.getMinTemperature());


        int weekDay = forecast.getWeekDay();

        // Weekend
        if (weekDay == 1 || weekDay == 7)
            forecastHolder.weekDay.setTextColor(forecastHolder.view.getResources().getColor(R.color.textAccent2));

        if (daysOfWeek.containsKey(weekDay)){
            int dayOfWeekResId = daysOfWeek.get(weekDay);
            forecastHolder.weekDay.setText(forecastHolder.view.getResources().getText(dayOfWeekResId));
        }
    }

    public interface ForecastOnClickListener {
        void onClickForecast(View view, int idx);
    }

    // ViewHolder com as views
    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView day;
        TextView month;
        TextView year;
        TextView weekDay;
        ImageView image;
        TextView weather;
        TextView maxTemperature;
        TextView minTemperature;
        CardView cardView;

        public ForecastViewHolder(View view) {
            super(view);
            this.view = view;

            image = (ImageView)view.findViewById(R.id.forecast_image);
            day = (TextView)view.findViewById(R.id.forecast_day);
            month = (TextView)view.findViewById(R.id.forecast_month);
            weekDay = (TextView) view.findViewById(R.id.forecast_week_day);
            year = (TextView)view.findViewById(R.id.forecast_year);
            weather = (TextView)view.findViewById(R.id.forecast_weather);
            maxTemperature = (TextView)view.findViewById(R.id.forecast_max);
            minTemperature = (TextView)view.findViewById(R.id.forecast_min);
            cardView = (CardView)view.findViewById(R.id.forecast_card_view);
        }
    }
}

