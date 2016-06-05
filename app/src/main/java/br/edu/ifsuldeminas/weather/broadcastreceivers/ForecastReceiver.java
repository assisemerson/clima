package br.edu.ifsuldeminas.weather.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.edu.ifsuldeminas.weather.MainActivity;
import br.edu.ifsuldeminas.weather.utils.NotificationUtil;

public class ForecastReceiver extends BroadcastReceiver {

    public static final String ACTION = "br.edu.ifsuldeminas.weather.FORECAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notifIntent = new Intent(context, MainActivity.class);

        NotificationUtil.create(context, notifIntent, "Clima", "Confira o clima para sua cidade", 1);
    }
}
