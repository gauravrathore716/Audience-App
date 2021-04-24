package com.b2c.audience.firebase;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.b2c.audience.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;


@SuppressWarnings("ALL")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private static NotificationManager mNotificationManager;
    private int NOTIFICATION_ID = new Random().nextInt(543254);

    public static void cancelNotification(int NOTIFICATION_ID) {

        if (mNotificationManager != null)
            mNotificationManager.cancel(NOTIFICATION_ID);
    }

    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        SessionManager.setDeviceToken(getApplicationContext(), refreshedToken);

        /*FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MyActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

            }
        });*/

    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        // Check if message contains a data payload.
        /*if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }*/

        // Check if message contains a notification payload.
    /*    if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/


        /*Log.e( "From: ", "? "+ remoteMessage.getFrom());
        Log.e( "Message Body: " , "? "+remoteMessage.getNotification().getBody());
        Log.e( "FCM Data: " ,"? "+ remoteMessage.getData().toString());*/

//        try {
//            JSONObject json = new JSONObject(remoteMessage.getData().toString());
//            handleDataMessage(json);
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }

        Map data = remoteMessage.getData();
        Log.d("NotificationPush", "receive data: " + data);

//        sendNotification(data);

    }// [END receive_message]



  /*  public static ModelNotificationDetail getAdminSettingList(Context context) {
        Gson gson = new Gson();
        String json = getSharedPreferences(context).getString("adminSetting", null);
        if (json != null) {
            AppLogger.e("adminSetting", json);
        }
        Type type = new TypeToken<ModelNotificationDetail>() {
        }.getType();
        return gson.fromJson(json, type);
    }
*/
/*    private void sendNotification(Map data) {

        String notify_type = (String) data.get("noti_key_type");

        String loginUserID = AppPreferenceManager.getId(this);

        if (loginUserID != null && !loginUserID.isEmpty()) {

            String message = "";
            String title = "";
            ModelNotificationDetail notificationDetail =null;
            try {

                JSONObject json = new JSONObject(data.toString());

                Type type = new TypeToken<ModelNotificationDetail>() {
                }.getType();
                notificationDetail = new Gson().fromJson(""+json.getJSONObject("message"), type);

//                message = json.getJSONObject("message").getString("message");

                if (AppPreferenceManager.getCurrentLanguageCode(this).isEmpty()
                        || AppPreferenceManager.getCurrentLanguageCode(this).equals("fr")) {
                    message = notificationDetail.getMessage_fr();
                    title = notificationDetail.getTitle_fr();
                } else {
                    message = notificationDetail.getMessage();
                    title = notificationDetail.getTitle();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            (String) data.get("title");

//            message = (String) data.get("message");
//        String user_id = (String) data.get("user_id");

            Log.e("NotificationPush", "" + message);

            String lineSep = System.getProperty("line.separator");
            message = message.replaceAll(lineSep, "<br />");
            message = message.replaceAll(lineSep, "<br/>");

//            if (loginUser.getId().equals(user_id)) {
//
//                ModelEvent modelEvent = null;
//                ModelUser modelUser = null;

            Intent intent;
            switch (notify_type) {
                case AppConstants.KEY_NOTIFY_ADMIN:

                    intent = new Intent(this, HomeActivity.class);
//                    intent.putExtra("Id", "" + (String) data.get("document_id"));
                    break;
                default:
                    intent = new Intent(this, HomeActivity.class);
                    break;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("message", message);
            intent.putExtra("notification_id", NOTIFICATION_ID);

            // The id of the channel.
            String channel_id = "my_channel_01";
            // The user-visible name of the channel.
            CharSequence name = getString(R.string.app_name);
            // The user-visible description of the channel.
            String description = getString(R.string.app_name);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Bitmap bm_notify_large = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id)
                    .setLargeIcon(bm_notify_large)
                    .setSmallIcon(R.mipmap.ic_launcher)//ic_stat_notify
                    .setTicker(message)
                    .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent))
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(Html.fromHtml(message)))//Html.fromHtml(message))
                    .setContentText(Html.fromHtml(message))//Html.fromHtml(message))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (mNotificationManager != null) {

                // Configure the notification channel.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    int importance = NotificationManager.IMPORTANCE_LOW;
                    NotificationChannel mChannel = new NotificationChannel(channel_id, name, importance);
                    mChannel.setDescription(description);
                    mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
                    mChannel.setLightColor(Color.RED);

                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                    mNotificationManager.createNotificationChannel(mChannel);
                } else {
                    notificationBuilder
//                                .setOngoing(true)
                            .setSound(defaultSoundUri)
                            .setChannelId(channel_id);
                }

                switch (notify_type) {
                    case AppConstants.KEY_NOTIFY_ADMIN:
                        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                        break;
                    default:
                        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                        break;
                }
            }

            Intent intent2;
            switch (notify_type) {
                case AppConstants.KEY_NOTIFY_ADMIN:
                    intent2 = new Intent("notification");
                    intent2.putExtra("notify_type", notify_type);
                    intent2.putExtra("notification_id", NOTIFICATION_ID);
                    intent2.putExtra("data", notificationDetail);
                    //send broadcast
                    getApplicationContext().sendBroadcast(intent2);

                    break;
            }
         *//*   } else {

                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("notify_type", (String) data.get("notify_type"));
                intent.putExtra("title", (String) data.get("title"));
                intent.putExtra("message", (String) data.get("message"));
                intent.putExtra("notification_id", NOTIFICATION_ID);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Bitmap bm_notify_large = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setLargeIcon(bm_notify_large)
                        .setSmallIcon(R.mipmap.ic_stat_notify)
                        .setTicker(message)
                        .setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent))
                        .setContentTitle((String) data.get("title"))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(Html.fromHtml(message)))//Html.fromHtml(message))
                        .setContentText(Html.fromHtml(message))//Html.fromHtml(message))
                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setChannelId("12341234")
                        .setContentIntent(pendingIntent);

                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                // The id of the channel.
                String channel_id = "my_channel_01";
                // The user-visible name of the channel.
                CharSequence name = getString(R.string.app_name);
                // The user-visible description of the channel.
                String description = getString(R.string.app_name);

                if (mNotificationManager != null) {

                    // Configure the notification channel.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        int importance = NotificationManager.IMPORTANCE_LOW;
                        NotificationChannel mChannel = new NotificationChannel(channel_id, name, importance);
                        mChannel.setDescription(description);
                        mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
                        mChannel.setLightColor(Color.RED);

                        mChannel.enableVibration(true);
                        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                        mNotificationManager.createNotificationChannel(mChannel);
                    } else {
                        notificationBuilder
//                                .setOngoing(true)
                                .setSound(defaultSoundUri)
                                .setChannelId(channel_id);
                    }

                    mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                }
            }*//*


     *//*Intent intent2 = new Intent("notification");
            intent2.putExtra("notify_type", notify_type);
            intent2.putExtra("message", message);
            intent2.putExtra("notification_id", NOTIFICATION_ID);
            //send broadcast
            getApplicationContext().sendBroadcast(intent);*//*

        }
    }*/

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String timestamp = data.getString("timestamp");//timestamp
            JSONObject payload = data.getJSONObject("payload");

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();

           /* if (!NotificationUtils.isBackgroundRunning(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(ntcAppManager.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
            }*/
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
//        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

}
