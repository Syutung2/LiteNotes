package cn.syutung.litenotes.util;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import cn.syutung.litenotes.bigwidget.CountdownDaysBigWidget;
import cn.syutung.litenotes.empty.TypeEmpty;
import cn.syutung.litenotes.widgets.CountdownDaysWidget;

public class Global {
    public static List<TypeEmpty> typeEmpties = null;
    public static String SHARE_TAG = "cn.syutung.litenotes";
    //
    public static TypeEmpty typeEmpty = new TypeEmpty("a");
    public static int now = 0;
    public static String TESTID = "ca-app-pub-3940256099942544/3419835294";
    public static final String RELEASEID = "ca-app-pub-2782613523833741/8941547256";

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startBigAppWidgetPage(Context context){
        AppWidgetManager appWidgetManager =
                AppWidgetManager.getInstance(context);
        ComponentName myProvider = new ComponentName(context,
                CountdownDaysBigWidget.class);
        if (appWidgetManager.isRequestPinAppWidgetSupported()) {
            Bundle extras = new Bundle();
            extras.putString("addType","appWidgetDetail");
            // packageName 为应用真实包名
        extras.putString("widgetName",
                    "packageName/cn.syutung.litenotes");
            Bundle dataBundle = new Bundle();
            extras.putBundle("widgetExtraData", dataBundle);
            appWidgetManager.requestPinAppWidget(myProvider, extras, null);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startSmallAppWidgetPage(Context context){
        AppWidgetManager appWidgetManager =
                AppWidgetManager.getInstance(context);
        ComponentName myProvider = new ComponentName(context,
                CountdownDaysWidget.class);
        if (appWidgetManager.isRequestPinAppWidgetSupported()) {
            Bundle extras = new Bundle();
            extras.putString("addType","appWidgetDetail");
            // packageName 为应用真实包名
            extras.putString("widgetName",
                    "packageName/cn.syutung.litenotes");
            Bundle dataBundle = new Bundle();
            extras.putBundle("widgetExtraData", dataBundle);
            appWidgetManager.requestPinAppWidget(myProvider, extras, null);
        }
    }
}
