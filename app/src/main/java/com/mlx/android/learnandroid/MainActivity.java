package com.mlx.android.learnandroid;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mlx.android.learnandroid.utils.HookManager;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private Button mButtonTest;

    private Context mContext;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        mContext = this;

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mButtonTest = findViewById(R.id.btn_test);

        mButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Hello from mlx", Toast.LENGTH_SHORT).show();
            }
        });

        HookManager hookManager=new HookManager();
        hookManager.hookOnClickListener(mButtonTest);
    }

    /**
     * 在耳机孔插入的情况下，选择用扬声器播放
     */
    private void playMusicWhileEarPhoneEnableBySpeaker() {
        AssetFileDescriptor assetFileDescriptor;
        try {
            assetFileDescriptor = mContext.getAssets().openFd("shuosanjiusan.mp3");
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    AudioManager audioManager = (AudioManager) mContext.getSystemService(AUDIO_SERVICE);
                    audioManager.setMicrophoneMute(false);
                    audioManager.setSpeakerphoneOn(true);
                    audioManager.setMode(AudioManager.STREAM_MUSIC);

                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送一个最简单的通知
     */
    private void sendSimplestNotification() {

        NotificationCompat.Builder builer = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("最简单的Notification")
                .setContentText("只有3个地方要设置");
        manager.notify(1, builer.build());
    }

    /**
     * 发送一个可以点击的通知
     */
    private void sendSimplestNotificationWithAction() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle("带点击效果的Notification")
                .setContentText("点击可以跳转到MainActivity")
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL);
        manager.notify(3, builder.build());
    }
}
