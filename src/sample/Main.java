package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Harbour;
import objects.TextureManager;

import java.io.File;

public class Main extends Application
{
    public static Scene scene;
    public static Pane mainGroup = new Pane();
    public static Harbour pearlHarbour;
    static FileChooser fileChooser = new FileChooser();
    public static File file;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pearl Harbour");
        primaryStage.setResizable(false);
        scene = new Scene(mainGroup, 1366, 768);

        // завантаження зображень у программу
        TextureManager.init();

        // створення макрооб'єкта
        pearlHarbour = new Harbour();
        pearlHarbour.mainAnimLaunch();
        pearlHarbour.addHarbourGroup(mainGroup);

        MiniMap.loadMapBG();

        // Отримання усіх команд користувача
        Input.input(pearlHarbour, mainGroup, primaryStage);

        primaryStage.setScene(Main.scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
