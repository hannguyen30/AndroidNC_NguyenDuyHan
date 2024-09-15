package com.example.bai_3;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean vibrate = intent.getBooleanExtra("vibrate", false);

        // Xử lý rung nếu cần
        if (vibrate) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Sử dụng VibrationEffect cho các phiên bản Android Oreo trở lên
                    VibrationEffect effect = VibrationEffect.createWaveform(new long[]{0, 500, 1000}, new int[]{0, 255, 0}, -1); // Để rung 500ms, nghỉ 1000ms
                    vibrator.vibrate(effect);
                } else {
                    // Sử dụng phương thức cũ cho các phiên bản Android dưới Oreo
                    vibrator.vibrate(new long[]{0, 500, 1000}, -1); // Rung 500ms, nghỉ 1000ms
                }
            }
        }

        // Tạo Intent để mở MainActivity khi nhấn vào thông báo
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "foxandroid")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(" Alarm Manager")
                .setContentText("Tap to stop vibration")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent); // Đặt pendingIntent để mở MainActivity

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}

