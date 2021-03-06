package app.pillikan.kz.common.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;
import app.pillikan.kz.R;
import app.pillikan.kz.common.preference.SessionManager;
import app.pillikan.kz.common.remote.Constants;
import app.pillikan.kz.content.view.FoundationActivity;
import app.pillikan.kz.content.view.accept_order.AcceptOrderActivity;
import app.pillikan.kz.content.view.accept_order.SuccessPaymentActivity;

import static android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PillikanFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "PillikanFireBaseService";
    private SessionManager sessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("sessionManager", Context.MODE_PRIVATE);
        sessionManager = new SessionManager(sharedPreferences);
        if (remoteMessage.getData().size() > Constants.ZERO) {
            try {
                if (remoteMessage.getNotification() != null) {
                    showNotification(remoteMessage);
                } else {
                    if (remoteMessage.getData().containsKey(Constants.DATA)) {
                        JSONObject jsonObject = new JSONObject(remoteMessage.getData().get(Constants.DATA));
                        int type = jsonObject.getInt(Constants.TYPE);
                        if (type == Constants.PUSH_DELIVERY) {
                            Intent intent = new Intent(this, AcceptOrderActivity.class);
                            intent.putExtra(Constants.ACCEPT_ORDER_ID, jsonObject.getInt(Constants.ORDER_ID));
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_MULTIPLE_TASK);
                            startActivity(intent);
                        } else if (type == Constants.PUSH_BY_MYSELF) {
                            Intent intent = new Intent(this, SuccessPaymentActivity.class);
                            intent.putExtra(Constants.NAME, jsonObject.getString(Constants.NAME));
                            intent.putExtra(Constants.PHONE, jsonObject.getString(Constants.PHONE));
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_MULTIPLE_TASK);
                            startActivity(intent);
                        }
                    }
                }
            } catch (JSONException | NullPointerException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }

    private void showNotification(RemoteMessage remoteMessage) {
        if (sessionManager.getIsAuthorize()) {
            NotificationManager notificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager == null) return;
            createChannel(getApplicationContext(), notificationManager);
            Intent intent = new Intent(getApplicationContext(), FoundationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(),
                    Constants.ZERO,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT
            );

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Spanned title = null;
            Spanned text;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                title = Html.fromHtml(remoteMessage.getNotification().getTitle(), Html.FROM_HTML_MODE_COMPACT);
                text = Html.fromHtml(remoteMessage.getNotification().getBody(), Html.FROM_HTML_MODE_COMPACT);
            } else {
                title = Html.fromHtml(remoteMessage.getNotification().getTitle());
                text = Html.fromHtml(remoteMessage.getNotification().getBody());
            }

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), Constants.DEFAULT_NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setColor(Color.parseColor("#2D47A1"))
                            .setContentTitle(title)
                            .setContentText(text)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

            int notificationId = new Random().nextInt(Integer.MAX_VALUE);
            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }

    public void createChannel(Context context, NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    Constants.DEFAULT_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }
    }

}