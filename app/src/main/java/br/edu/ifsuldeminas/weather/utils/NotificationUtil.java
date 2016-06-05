package br.edu.ifsuldeminas.weather.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import br.edu.ifsuldeminas.weather.R;

public class NotificationUtil {

    private static final String TAG = "forecast";
    private static final String ACTION_VISUALIZAR = "br.edu.ifsuldeminas.weather.ACTION_VISUALIZAR";

    private static PendingIntent getPendingIntent(Context context, Intent intent, int id) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(intent.getComponent());
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public static void create(Context context, Intent intent, String contentTitle, String contentText, int id) {
        PendingIntent p = getPendingIntent(context, intent, id);

        // Cria a notificação
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setDefaults(Notification.DEFAULT_ALL); // Ativa configurações padrão
        builder.setSmallIcon(R.drawable.ic_launcher); // Ícone
        builder.setContentTitle(contentTitle); // Título
        builder.setContentText(contentText); // Mensagem
        builder.setContentIntent(p); // Intent que será chamada ao clicar na notificação.
        builder.setAutoCancel(true); // Auto cancela a notificação ao clicar nela

        builder.setColor(Color.GREEN);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, builder.build());
    }

}

