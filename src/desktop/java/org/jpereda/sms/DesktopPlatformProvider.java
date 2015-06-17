package org.jpereda.sms;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author jpereda
 */
public class DesktopPlatformProvider implements PlatformProvider {

    @Override
    public List<SMSMessage> readSMSs() {
        SMSMessage sms1=new SMSMessage("1", "2", "3", "4", "5", "6");
        SMSMessage sms2=new SMSMessage("2", "3", "4", "5", "6", "7");
        List<SMSMessage> list = new ArrayList<>();
        list.add(sms1);
        list.add(sms2);
        return list;
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
