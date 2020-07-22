package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import static sample.Main.pearlHarbour;

public class MiniMap {
    WritableImage snapshot = Main.mainGroup.snapshot(new SnapshotParameters(), null);

    private static ImageView imageMap;
    private static ImageView mapBG;
    private static SnapshotParameters parameters;

    public static void loadMapBG() {
        WritableImage snapshot = pearlHarbour.backGround.snapshot(new SnapshotParameters(), null);
        mapBG = new ImageView(snapshot);
        Main.mainGroup.getChildren().add(mapBG);

        Scale scale = new Scale();

        scale.setX(0.08);
        scale.setY(0.08);

        mapBG.getTransforms().add(scale);

        parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        imageMap = new ImageView();
        Main.mainGroup.getChildren().add(imageMap);
        imageMap.getTransforms().add(scale);
    }

    public static void actualize() {
            WritableImage snapshot = pearlHarbour.objectsGroup.snapshot(parameters, null);

            imageMap.setImage(snapshot);

            imageMap.setLayoutX( 600 - Main.mainGroup.getLayoutX());
            imageMap.setLayoutY(10 - Main.mainGroup.getLayoutY());


            mapBG.setLayoutX (600 - Main.mainGroup.getLayoutX());
            mapBG.setLayoutY(10 - Main.mainGroup.getLayoutY());
    }

    public static void mouseClick(double x, double y) {
        if(mapBG.boundsInParentProperty ().get ().contains ( x,y )){
            double posX = -(( x - 54.64 + Main.mainGroup.getLayoutX () - 600) /(3000*0.08) * 3000);
            double posY = -(( y - 30.72 + Main.mainGroup.getLayoutY () - 10 )/(1800*0.08) * 1800);
            if (posX > 0) posX = 0.0;
            if (posY > 0) posY = 0.0;
            if (posX < -1630) posX = -1630;
            if (posY < -1050) posY = -1050;

            Main.mainGroup.setLayoutX(posX);
            Main.mainGroup.setLayoutY(posY);
            pearlHarbour.moveFrame(posX, posY);

            actualize();
        }
    }
}
