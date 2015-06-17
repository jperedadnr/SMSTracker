package org.jpereda.sms;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author jpereda
 */
public class IosPlatformProvider implements PlatformProvider {

    @Override
    public List<SMSMessage> readSMSs() {
        return new ArrayList<>();
    }
    
    private final ObjectProperty<SMSMessage> messages = new SimpleObjectProperty<>();
    
    @Override
    public ObjectProperty<SMSMessage> messagesProperty() {
        return messages;
    }
    
    @Override
    public void listenToIncomingSMS() {
    }
    
    @Override
    public void sendSMS(String number, String message){
        messages.set(new SMSMessage("1", number, message, "4", "5", "6"));
    }
}
