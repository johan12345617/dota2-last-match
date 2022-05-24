package com.example.dota2lastmatch

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.example.dota2lastmatch.models.LastMatch
import com.squareup.picasso.Picasso


/**
 * Implementation of App Widget functionality.
 */
class LastMatchWidget : AppWidgetProvider() {
    val lastMatch = LastMatch()
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        lastMatch.updateInfo("336204238")
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,lastMatch)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    lastMatch: LastMatch
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName,R.layout.last_match_widget)
    Picasso.get().load("https://cdn.dota2.com/apps/dota2/images/items/magic_wand_lg.png").into(views, R.id.heroImage, intArrayOf(appWidgetId))
    //views.setTextViewText(R.id, lastMatch.heroName)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}