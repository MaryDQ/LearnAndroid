package com.mlx.android.learnandroid.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class LearnApplication extends Application {

    private Context mContext;

    private static final String TAG = "LearnApplication";

    @Override
    public void onCreate() {


        try {
            Log.d(TAG, "onCreate:starting init ");
//            HookManager.init();
//            HookManager.injectInstrumentation();
        }catch (Exception e){
            Log.d(TAG, "onCreate: "+e.toString());
        }

        super.onCreate();

        mContext=this;
    }


}
