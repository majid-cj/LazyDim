package majid.cj.lazydim;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;


public class LazyDimWidget extends AppWidgetProvider {

    private static final String ACTION_CLICK = "MY_WIDGET_ACTION_CLICK";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.lazy_dim_widget);

        views.setRemoteAdapter(R.id.appwidget_button, new Intent(context, LazyDimWidget.class));

        Intent launchMain = new Intent(context, MainActivity.class);
        launchMain.setAction(ACTION_CLICK);
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, 0);
        views.setOnClickPendingIntent(R.id.appwidget_button, pendingMainIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_CLICK)){
            new ChangeSettings(context).ChangeSettings(Integer.MAX_VALUE, 255);
        }
        super.onReceive(context, intent);
    }
}