package com.mugenico.languageGenerator;

import com.mugenico.languageGenerator.view.controlpanelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;

/**
 *
 * Created by Gemin on 25.02.2018.
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private MainApp mainApp = this;
    private AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Language Generator");

        initMainLayout();
    }

    private void initMainLayout() {
        try {
            // Load main layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/controlpanel.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the main layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            controlpanelController controller = loader.getController();
            controller.setMainApp(this);

            // As the design is barebones, don't let the window be resized
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

}
