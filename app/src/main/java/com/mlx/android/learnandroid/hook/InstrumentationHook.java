package com.mlx.android.learnandroid.hook;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.util.Log;

public class InstrumentationHook extends Instrumentation {

    private static final String TAG = "InstrumentationHook";

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Log.d(TAG, "newActivity: CustomInstrumentation#newActivity call 3 params className:"+className);
        Activity activity=createActivity(intent);
        if (null!=activity) {
            return activity;
        }
        return super.newActivity(cl, className, intent);
    }

    @Override
    public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent, ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance)
            throws InstantiationException, IllegalAccessException {
        Log.d(TAG, "newActivity: CustomInstrumentation#newActivity call 1");
        return super.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance);
    }

    protected Activity createActivity(Intent intent){
        String className=intent.getComponent().getClassName();
        Log.d(TAG, "createActivity: className="+className);
        if ("com.mlx.android.learnandroid.MainActivity".equals(className)) {
            try {
                Class<? extends Activity> PluginActivity= (Class<? extends Activity>) Class
                        .forName("com.mlx.android.learnandroid.Main2Activity");
                return PluginActivity.newInstance();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
