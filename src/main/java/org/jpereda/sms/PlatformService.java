package org.jpereda.sms;

import static java.lang.String.format;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PlatformService {

    public static final String DESKTOP = "Desktop";
    public static final String ANDROID = "Android";
    public static final String IOS = "iOS";

    private static final Logger LOG = Logger.getLogger(PlatformService.class.getName());

    private static PlatformService instance;

    public static synchronized PlatformService getInstance() {
        if (instance == null) {
            instance = new PlatformService();
        }
        return instance;
    }

    private final ServiceLoader<PlatformProvider> serviceLoader;
    private PlatformProvider provider;

    private PlatformService() {
        serviceLoader = ServiceLoader.load(PlatformProvider.class);
        try {
            Iterator<PlatformProvider> iterator = serviceLoader.iterator();
            while (iterator.hasNext()) {
                if (provider == null) {
                    provider = iterator.next();
                    LOG.info(format("Using PlatformProvider: %s", provider.getClass().getName()));
                } else {
                    LOG.info(format("This PlatformProvider is ignored: %s", iterator.next().getClass().getName()));
                }
            }
        } catch (Exception e) {
            throw new ServiceConfigurationError("Failed to access + ", e);
        }
        if (provider == null) {
            LOG.severe("No PlatformProvider implementation could be found!");
        }
    }

    public List<SMSMessage> readSMSs() {
        if (provider != null) {
            return provider.readSMSs();
        }
        return new ArrayList<>();
    }
    
    public void sendSMS(String number, String message){
        if (provider != null) {
            provider.sendSMS(number, message);
        }
        
    }
    
    public void listenToIncomingSMS(){
        if (provider != null) {
            provider.listenToIncomingSMS();
        }
        
    }
    
    public ObjectProperty<SMSMessage> messageProperty(){
        if (provider != null) {
            return provider.messagesProperty();
        }
        return new SimpleObjectProperty<>();
    }

}
