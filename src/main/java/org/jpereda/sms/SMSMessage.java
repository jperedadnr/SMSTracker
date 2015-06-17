package org.jpereda.sms;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jpereda
 */
public class SMSMessage {

    private final StringProperty id;
    private final StringProperty address;
    private final StringProperty msg;
    private final StringProperty readState; //"0" not read, "1" read sms
    private final StringProperty time;
    private final StringProperty folderName;

    public SMSMessage(){
        this.id = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.msg = new SimpleStringProperty("");
        this.readState = new SimpleStringProperty("");
        this.time = new SimpleStringProperty("");
        this.folderName = new SimpleStringProperty("");
    }

    public SMSMessage(String id, String address, String msg, String readState, String time, String folderName){
        this.id = new SimpleStringProperty(id);
        this.address = new SimpleStringProperty(address);
        this.msg = new SimpleStringProperty(msg);
        this.readState = new SimpleStringProperty(readState);
        this.time = new SimpleStringProperty(time);
        this.folderName = new SimpleStringProperty(folderName);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getMsg() {
        return msg.get();
    }

    public StringProperty msgProperty() {
        return msg;
    }

    public String getReadState() {
        return readState.get();
    }

    public StringProperty readStateProperty() {
        return readState;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String getFolderName() {
        return folderName.get();
    }

    public StringProperty folderNameProperty() {
        return folderName;
    }

    @Override
    public String toString(){
        return id.get()+ ": " + address.get() + ": " + msg.get();
    }
}
