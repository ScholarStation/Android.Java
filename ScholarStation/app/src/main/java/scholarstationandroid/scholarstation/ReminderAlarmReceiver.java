package scholarstationandroid.scholarstation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

/**
 * Created by Jason on 4/15/2016.
 */
public class ReminderAlarmReceiver extends BroadcastReceiver {
    public static final String reminder_text = "Feedback Form";

    @Override
    public void onReceive(Context context,Intent intent){
        String reminderText = intent.getStringExtra(reminder_text);
        Intent intentAction = new Intent(context,FeedbackList.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intentAction,0);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.mriknow2)
                .setTicker("Reminder")
                .setWhen(new Date().getTime())
                .setContentText(reminderText)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);
    }
}
