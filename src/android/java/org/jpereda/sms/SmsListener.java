package org.jpereda.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony.Sms.Intents;
import android.telephony.SmsMessage;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author jpereda
 */
public class SmsListener extends BroadcastReceiver {

    private final ObjectProperty<SMSMessage> messages = new SimpleObjectProperty<>();

    @Override
    public void onReceive(Context cntxt, Intent intent) {
        if(intent.getAction().equals(Intents.SMS_RECEIVED_ACTION)){
            for (SmsMessage smsMessage : Intents.getMessagesFromIntent(intent)) {
                SMSMessage sms = new SMSMessage("0", smsMessage.getOriginatingAddress(),
                        smsMessage.getMessageBody(), smsMessage.getStatus()==1?"read":"not read", 
                        Long.toString(smsMessage.getTimestampMillis()), "inbox");
                messages.set(sms);
            }
        }
    }

    public ObjectProperty<SMSMessage> messagesProperty() {
        return messages;
    }

}
