package com.mlx.android.learnandroid.utils;

import android.util.Log;
import android.view.View;

import com.mlx.android.learnandroid.hook.InstrumentationHook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HookManager {
    private static final String TAG = "HookManager";

    static Object activityThreadInstance;

    public static void init() throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> activityThread = Class.forName("android.app.ActivityThread");
        Method currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
        activityThreadInstance = currentActivityThread.invoke(null);
    }

    public static void injectInstrumentation() throws NoSuchFieldException,
            IllegalAccessException, IllegalArgumentException {
        Log.i(TAG, "injectInstrumentation: start injectInstrumentation");
        Field field_instrumentation = activityThreadInstance.getClass().getDeclaredField("mInstrumentation");
        field_instrumentation.setAccessible(true);
        InstrumentationHook instrumentationHook = new InstrumentationHook();
        field_instrumentation.set(activityThreadInstance, instrumentationHook);
    }

    public void hookOnClickListener(View view){
        try {
            Method method=View.class.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfo=method.invoke(view);

            Class<?> listenerInfoClass=Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener=listenerInfoClass.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener origonClickListener= (View.OnClickListener) mOnClickListener.get(listenerInfo);

            MyOnClickListener newOnClickListener=new MyOnClickListener(origonClickListener);
            mOnClickListener.set(listenerInfo,newOnClickListener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class MyOnClickListener implements View.OnClickListener{

        private View.OnClickListener origonClickListener;

        public MyOnClickListener(View.OnClickListener origonClickListener) {
            this.origonClickListener = origonClickListener;
        }

        @Override
        public void onClick(View v) {

            Log.d(TAG, "onClick: 点击事件拦截之前0000000000");

            if (null!=origonClickListener) {
                origonClickListener.onClick(v);
            }

            Log.d(TAG, "onClick: 点击事件拦截之后111111111111111");

        }
    }
}
