package br.edu.ifsuldeminas.weather;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import br.edu.ifsuldeminas.weather.adapters.CityAdapter;
import br.edu.ifsuldeminas.weather.domain.City;
import br.edu.ifsuldeminas.weather.services.CityService;
import br.edu.ifsuldeminas.weather.services.CityServiceObserver;

public class CityActivity extends AppCompatActivity implements CityServiceObserver {

    private RecyclerView recyclerView;
    private List<City> cities;
    private City city = null;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        recyclerView = (RecyclerView) findViewById(R.id.cidade_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            this.setTitle(query);

            CityService cityService = new CityService();
            cityService.register(this);
            cityService.execute(query);

            dialog = new ProgressDialog(this);
            this.dialog.setMessage(getString(R.string.loading_cities));
            this.dialog.show();

        }
    }

    private Context getContext(){
        return this;
    }

    private CityAdapter.CityOnClickListener onClickCity() {
        return new CityAdapter.CityOnClickListener() {
            @Override
            public void onClickCity(View view, int index) {
            city = cities.get(index);
            finish();
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = new Intent(getContext(), MainActivity.class);
        if (city != null) intent.putExtra("city", city);
        startActivity(intent);
    }

    @Override
    public void serviceDone(List<City> cities) {
        this.cities = cities;
        // Atualiza a lista
        recyclerView.setAdapter(new CityAdapter(this, cities, onClickCity()));

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
