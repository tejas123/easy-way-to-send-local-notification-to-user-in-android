package com.tag.localnotificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int currentNotificationID = 0;
    private EditText etMainNotificationText, etMainNotificationTitle;
    private Button btnMainSendSimpleNotification, btnMainSendExpandLayoutNotification, btnMainSendNotificationActionBtn, btnMainSendMaxPriorityNotification, btnMainSendMinPriorityNotification, btnMainSendCombinedNotification, btnMainClearAllNotification;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private String notificationTitle;
    private String notificationText;
    private Bitmap icon;
    private int combinedNotificationCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllWidgetReference();
        bindWidgetWithAnEvent();
    }

    private void getAllWidgetReference() {
        etMainNotificationText = (EditText) findViewById(R.id.etMainNotificationText);
        etMainNotificationTitle = (EditText) findViewById(R.id.etMainNotificationTitle);

        btnMainSendSimpleNotification = (Button) findViewById(R.id.btnMainSendSimpleNotification);
        btnMainSendExpandLayoutNotification = (Button) findViewById(R.id.btnMainSendExpandLayoutNotification);
        btnMainSendNotificationActionBtn = (Button) findViewById(R.id.btnMainSendNotificationActionBtn);
        btnMainSendMaxPriorityNotification = (Button) findViewById(R.id.btnMainSendMaxPriorityNotification);
        btnMainSendMinPriorityNotification = (Button) findViewById(R.id.btnMainSendMinPriorityNotification);
        btnMainSendCombinedNotification = (Button) findViewById(R.id.btnMainSendCombinedNotification);
        btnMainClearAllNotification = (Button) findViewById(R.id.btnMainClearAllNotification);

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher);
    }

    private void bindWidgetWithAnEvent() {
        btnMainSendSimpleNotification.setOnClickListener(this);
        btnMainSendExpandLayoutNotification.setOnClickListener(this);
        btnMainSendNotificationActionBtn.setOnClickListener(this);
        btnMainSendMaxPriorityNotification.setOnClickListener(this);
        btnMainSendMinPriorityNotification.setOnClickListener(this);
        btnMainSendCombinedNotification.setOnClickListener(this);
        btnMainClearAllNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setNotificationData();
        switch (v.getId()) {
            case R.id.btnMainSendSimpleNotification:
                setDataForSimpleNotification();
                break;
            case R.id.btnMainSendExpandLayoutNotification:
                setDataForExpandLayoutNotification();
                break;
            case R.id.btnMainSendNotificationActionBtn:
                setDataForNotificationWithActionButton();
                break;
            case R.id.btnMainSendMaxPriorityNotification:
                setDataForMaxPriorityNotification();
                break;
            case R.id.btnMainSendMinPriorityNotification:
                setDataForMinPriorityNotification();
                break;
            case R.id.btnMainSendCombinedNotification:
                setDataForCombinedNotification();
                break;
            case R.id.btnMainClearAllNotification:
                clearAllNotifications();
                break;
        }

    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;

        notificationManager.notify(notificationId, notification);
    }

    private void clearAllNotifications() {
        if (notificationManager != null) {
            currentNotificationID = 0;
            notificationManager.cancelAll();
        }
    }

    private void setNotificationData() {
        notificationTitle = this.getString(R.string.app_name);
        notificationText = "Hello..This is a Notification Test";
        if (!etMainNotificationText.getText().toString().equals("")) {
            notificationText = etMainNotificationText.getText().toString();
        }
        if (!etMainNotificationTitle.getText().toString().equals("")) {
            notificationTitle = etMainNotificationTitle.getText().toString();
        }
    }

    private void setDataForSimpleNotification() {
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText);
        sendNotification();
    }

    private void setDataForExpandLayoutNotification() {
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setContentText(notificationText);

        sendNotification();
    }

    private void setDataForNotificationWithActionButton() {

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setContentText(notificationText);

        Intent answerIntent = new Intent(this, AnswerReceiveActivity.class);
        answerIntent.setAction("Yes");
        PendingIntent pendingIntentYes = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.thumbs_up, "Yes", pendingIntentYes);

        answerIntent.setAction("No");
        PendingIntent pendingIntentNo = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.thumbs_down, "No", pendingIntentNo);


        sendNotification();
    }

    private void setDataForMaxPriorityNotification() {
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText(notificationText);

        sendNotification();
    }

    private void setDataForMinPriorityNotification() {
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setPriority(Notification.PRIORITY_MIN)
                .setContentText(notificationText);

        sendNotification();
    }

    private void setDataForCombinedNotification() {
        combinedNotificationCounter++;
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setGroup("group_emails")
                .setGroupSummary(true)
                .setContentText(combinedNotificationCounter + " new messages");

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(notificationTitle);
        inboxStyle.setSummaryText("mehulrughani@gmail.com");
        for (int i = 0; i < combinedNotificationCounter; i++) {
            inboxStyle.addLine("This is Test" + i);
        }
        currentNotificationID = 500;
        notificationBuilder.setStyle(inboxStyle);
        sendNotification();
    }


}
