package br.edu.ifsuldeminas.weather;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import br.edu.ifsuldeminas.weather.adapters.ForecastAdapter;
import br.edu.ifsuldeminas.weather.broadcastreceivers.ForecastReceiver;
import br.edu.ifsuldeminas.weather.domain.City;
import br.edu.ifsuldeminas.weather.domain.Forecast;
import br.edu.ifsuldeminas.weather.services.ForecastService;
import br.edu.ifsuldeminas.weather.services.ForecastServiceObserver;
import br.edu.ifsuldeminas.weather.utils.AlarmUtil;

public class MainActivity extends AppCompatActivity implements ForecastServiceObserver {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private MenuItem searchItem;
    private ProgressDialog dialog;
    private ShareActionProvider share;


    private City city;
    private List<Forecast> forecasts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.forecast_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("city_id", city.getId());
        editor.putString("city", city.getName());
        editor.putString("uf", city.getUf());
        editor.commit();

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        dialog = new ProgressDialog(this);
        this.dialog.setMessage(getString(R.string.loading_forecast));
        this.dialog.show();

        if (searchItem != null)
            MenuItemCompat.collapseActionView(searchItem);

        Intent intent = getIntent();
        city = (City) intent.getSerializableExtra("city");

        if (city == null){
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

            city = new City();
            city.setName(sharedPref.getString("city", "Alfenas"));
            city.setUf(sharedPref.getString("uf", "MG"));
            city.setId(sharedPref.getInt("city_id", 356));
        }

        getSupportActionBar().setTitle("");
        toolbar.setTitle(city.getName() + " " + city.getUf());

        ForecastService forecastService = new ForecastService();
        forecastService.register(this);
        forecastService.execute(String.valueOf(city.getId()));
    }

    private ForecastAdapter.ForecastOnClickListener onClickForecast() {
        return new ForecastAdapter.ForecastOnClickListener() {
            @Override
            public void onClickForecast(View view, int index) {

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null)
            searchView = (SearchView) searchItem.getActionView();

        if (searchView != null)
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));

        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(info);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        // ShareActionProvider
        MenuItem shareItem = menu.findItem(R.id.action_share);
        this.share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        return true;
    }

    // Intent que define o conteúdo que será compartilhado
    private Intent getDefaultIntent() {
        Intent intent = new Intent(
                Intent.ACTION_SEND);

        intent.setType("text/*");

        intent.putExtra(
                Intent.EXTRA_TEXT,
                getForecastAsText());

        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            onClickAgendar();
            return true;
        }

        if (id == R.id.action_share) {
            share.setShareIntent(getDefaultIntent());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void serviceDone(List<Forecast> forecasts) {
        this.forecasts = forecasts;

        recyclerView.setAdapter(new ForecastAdapter(this, this.forecasts, onClickForecast()));

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public String getForecastAsText() {

        String forecastWeek = "";

        forecastWeek = String.format("%s %s %s\n\n", getText(R.string.forecast_week_text).toString(), city.getName(), city.getUf()) ;

        for (Forecast f: this.forecasts) {
            forecastWeek += String.format("%s - %s/%s/%s \n", getText(ForecastAdapter.daysOfWeek.get(f.getWeekDay())), f.getDay(), f.getMonth(), f.getYear());
            forecastWeek += String.format("%s \n\n", getText(ForecastAdapter.forecastText.get(f.getWeather())));
        }

        return forecastWeek;
    }

    // Data/Tempo para agendar o alarme
    public long getTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 10);
        long time = c.getTimeInMillis();
        return time;
    }

    public void onClickAgendar() {
        Intent intent = new Intent(ForecastReceiver.ACTION);
        // Agenda para daqui a 5 seg
        AlarmUtil.schedule(this, intent, getTime());

        Snackbar.make((View) findViewById(R.id.main_layout), getString(R.string.forecast_notification), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        //Toast.makeText(this, R.string.forecast_notification, Toast.LENGTH_SHORT).show();
    }

    public void onClickAgendarComRepeat(View view) {
        Intent intent = new Intent(ForecastReceiver.ACTION);
        // Agenda para daqui a 5 seg, repete a cada 30 seg
        AlarmUtil.scheduleRepeat(this, intent, getTime(), 30 * 1000);
        Toast.makeText(this,"Alarme agendado com repetir.",Toast.LENGTH_SHORT).show();
    }

    public void onClickCancelar(View view) {
        Intent intent = new Intent(ForecastReceiver.ACTION);
        AlarmUtil.cancel(this,intent);
        Toast.makeText(this,"Alarme cancelado",Toast.LENGTH_SHORT).show();
    }
}
