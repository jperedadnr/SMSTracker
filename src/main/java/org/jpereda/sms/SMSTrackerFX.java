package org.jpereda.sms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author jpereda
 */
public class SMSTrackerFX extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        /*
        TOP :: Sending SMS
        Warning: This may by subjected to costs to your mobile account
        */
        Button buttonSend = new Button("Send SMS");

        TextField number = new TextField();
        number.setPromptText("Insert number");
        HBox.setHgrow(number, Priority.ALWAYS);
        HBox hbox = new HBox(10,buttonSend, number);

        TextField message = new TextField();
        message.setPromptText("Insert text");
        HBox.setHgrow(message, Priority.ALWAYS);

        VBox vboxTop = new VBox(10,hbox,message);

        buttonSend.disableProperty().bind(Bindings.createBooleanBinding(()->{
                return number.textProperty().isEmpty()
                        .or(message.textProperty().isEmpty()).get();
            }, number.textProperty(),message.textProperty()));

        vboxTop.setPadding(new Insets(10));
        root.setTop(vboxTop);

        /*
        CENTER :: Reading SMS Inbox
        */
        Button button = new Button("Read SMS Inbox");

        ListView<SMSMessage> view = new ListView<>();
        view.setCellFactory(data -> new SMSListCell());
        VBox.setVgrow(view, Priority.ALWAYS);

        VBox vboxCenter = new VBox(10,button,view);
        vboxCenter.setPadding(new Insets(10));
        root.setCenter(vboxCenter);

        /*
        BOTTOM :: Listening to incoming SMS
        */
        Label incoming = new Label("No messages");

        VBox vboxBottom = new VBox(10,new Label("Incoming SMS"),incoming);
        vboxBottom.setPadding(new Insets(10));

        root.setBottom(vboxBottom);

        button.setOnAction(e->{
            view.setItems(FXCollections.observableArrayList(PlatformService.getInstance().readSMSs()));
        });
        
        buttonSend.setOnAction(e->{
            PlatformService.getInstance().sendSMS(number.getText(),message.getText());
        });

        PlatformService.getInstance().messageProperty().addListener((obs,s,s1)->{
            Platform.runLater(()->incoming.setText(s1.toString()));
        });

        PlatformService.getInstance().messageProperty().addListener((obs,s,s1)->{
            Platform.runLater(()->incoming.setText(s1.toString()));
        });
        
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        scene.getStylesheets().add(getClass().getResource("root.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        // start broadcast
        PlatformService.getInstance().listenToIncomingSMS();
    }

    private static class SMSListCell extends ListCell<SMSMessage> {

        @Override
        protected void updateItem(SMSMessage item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                setGraphic(new Label(item.getId()+ ": " + item.getMsg()));
            } else {
                setGraphic(null);
            }
        }
    }

}
