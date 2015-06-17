package org.jpereda.sms;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony.Sms.Intents;
import android.telephony.SmsManager;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafxports.android.FXActivity;

/**
 *
 * @author jpereda
 */
public class AndroidPlatformProvider implements PlatformProvider {

    public static final Uri SMS_CONTENT_URI = Uri.parse("content://sms");
    public static final Uri SMS_INBOX_CONTENT_URI = Uri.withAppendedPath(SMS_CONTENT_URI, "inbox");
 
    private final boolean unreadOnly=false;
    
    @Override
    public List<SMSMessage> readSMSs() {
        List<SMSMessage> list = new ArrayList<>();

        String SMS_READ_COLUMN = "read";
        String WHERE_CONDITION = unreadOnly ? SMS_READ_COLUMN + " = 0" : null;
        String SORT_ORDER = "date DESC";
        
        try (Cursor cursor = FXActivity.getInstance().getContentResolver().query(SMS_INBOX_CONTENT_URI,
                new String[] { "_id", "address", "read", "date", "body", "type" },
                WHERE_CONDITION,
                null,
                SORT_ORDER)) {
            int totalSMS = cursor.getCount();
            
            if (cursor.moveToFirst()) {
                for(int i=0; i<totalSMS; i++){
                    String messageId = cursor.getString(0);
                    String address = cursor.getString(1);
                    String readState = cursor.getString(2);
                    String timestamp = cursor.getString(3);
                    String body = cursor.getString(4);
                    String folder = cursor.getString(5);
                    
                    SMSMessage smsMessage = new SMSMessage(messageId, address, body, readState, timestamp, folder);
                    list.add(smsMessage);
                    
                    cursor.moveToNext();
                }
            }
        }
        
        return list;
    }

    @Override
    public void sendSMS(String number, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
    }
    
    private final SmsListener receiver = new SmsListener();

    @Override
    public ObjectProperty<SMSMessage> messagesProperty() {
        return receiver.messagesProperty();
    }

    @Override
    public void listenToIncomingSMS() {
        FXActivity.getInstance().registerReceiver(receiver, new IntentFilter(Intents.SMS_RECEIVED_ACTION));

        FXActivity.getInstance().getApplication().registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity actvt, Bundle bundle) {
            }

            @Override
            public void onActivityStarted(Activity actvt) {
            }

            @Override
            public void onActivityResumed(Activity actvt) {
            }

            @Override
            public void onActivityPaused(Activity actvt) {
            }

            @Override
            public void onActivityStopped(Activity actvt) {
                
            }

            @Override
            public void onActivitySaveInstanceState(Activity actvt, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(Activity actvt) {
                System.out.println("Activity destroyed");
                try {
                    FXActivity.getInstance().unregisterReceiver(receiver);
                } catch(IllegalArgumentException e){
                    System.out.println("Receiver not registered");
                }
            }
           
       });
    }

}
