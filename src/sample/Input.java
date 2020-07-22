package sample;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.Harbour;
import objects.Jet;
import objects.State;

import java.io.IOException;

//класс для обробки команд користувача
public class Input {
    private static boolean jPressed = false;

    public static void input(Harbour pearlHarbour, Pane group, Stage stage) {

        // функція для відслідковування натискань клавіш
        Main.scene.setOnKeyPressed(event -> {
            double xLocation = group.getLayoutX();
            double yLocation = group.getLayoutY();

            switch (event.getCode()) {
                case RIGHT:
                    // при натисканні клавіши "право" активні літаки починають поворот вправо
                    pearlHarbour.getFleet().startRight();
                    break;

                case LEFT:
                    // при натисканні клавіши "ліво" активні літаки починають поворот вліво
                    pearlHarbour.getFleet().startLeft();
                    break;

                case SPACE:
                    // стрільба активних мікрооб'єктів
                    pearlHarbour.getFleet().startShoot();
                    break;

                case L:
                    if (event.isControlDown ()) {
                        // переривання операції приземлення
                        pearlHarbour.getFleet().stopLanding();
                    }
                    else {
                        // активні мікрооб'єкти починають приземлення
                        pearlHarbour.getFleet().startLanding();
                    }
                    break;

                case U:
                    // взліт усіх японських літаків з авіаносця
                    pearlHarbour.getFleet().takeOff();
                    break;

                case INSERT:
                    // створення нового мікрооб'єкту
                    try {
                        Menu.display ();
                    }
                    catch (IOException exception) {
                        exception.printStackTrace ( );
                    }
                    break;

                case DELETE:
                    // видалення активних мікрооб'єктів
                    pearlHarbour.getFleet().deleteActive();
                    break;

                case ESCAPE:
                    // деактивація активних літаків
                    pearlHarbour.getFleet().deactivate();
                    break;

                case K:
                    pearlHarbour.getFleet().changeInteraction();
                    break;

                case J:
                    if(!jPressed) {
                        Menu.toParade();
                        jPressed = true;
                    }
                    else {
                        jPressed = false;
                        for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("US")) {
                            plane.setState(State.MOVING_TO_CARRIER);
                        }
                        for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("Japan")) {
                            plane.setState(State.MOVING_TO_CARRIER);
                        }
                    }
                    break;

                case I:
                    System.out.println("US:");
                    pearlHarbour.getFleet().sort( pearlHarbour.getFleet().getPlanes("Japan"));
                    System.out.println("Japan:");
                    pearlHarbour.getFleet().sort( pearlHarbour.getFleet().getPlanes("US"));
                    break;

                case P:
                    Main.file = Main.fileChooser.showSaveDialog(stage);
                    if (Main.file != null) {
                        try {
                            Serialize.serialization(Main.file.getAbsolutePath());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case O:
                    Main.file = Main.fileChooser.showOpenDialog(stage);
                    if (Main.file != null) {
                        try {
                            Serialize.deserialization(Main.file.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case A:
                    xLocation += 200;
                    if (xLocation > 0) xLocation = 0.0;
                    pearlHarbour.moveFrame(xLocation, yLocation);
                    group.setLayoutX(xLocation);
                    group.setLayoutY(yLocation);
                    MiniMap.actualize();
                    break;
                case D:
                    xLocation -= 200;
                    if (xLocation < -1630) xLocation = -1630;
                    pearlHarbour.moveFrame(xLocation, yLocation);
                    group.setLayoutX(xLocation);
                    group.setLayoutY(yLocation);
                    MiniMap.actualize();
                    break;
                case W:
                    yLocation += 200;
                    if (yLocation > 0) yLocation = 0.0;
                    pearlHarbour.moveFrame(xLocation, yLocation);
                    group.setLayoutX(xLocation);
                    group.setLayoutY(yLocation);
                    MiniMap.actualize();
                    break;
                case S:
                    yLocation -= 200;
                    if (yLocation < -1050) yLocation = -1050;
                    pearlHarbour.moveFrame(xLocation, yLocation);
                    group.setLayoutX(xLocation);
                    group.setLayoutY(yLocation);
                    MiniMap.actualize();
                    break;

                default:
                    break;
            }
        });

        /*
        * функція для відслідковування відпускання клавіш,
        * потрібна для того, щоб зробити керування мікрооб'ектами більш плавним
        */
        Main.scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case SPACE:
                    pearlHarbour.getFleet().stopShoot();
                    break;
                case LEFT:
                    // при відпусканні клавіши "ліво" активні літаки перстають повертати вліво
                    pearlHarbour.getFleet().stopLeft();
                    break;
                case RIGHT:
                    // при відпусканні клавіши "право" активні літаки перстають повертати вправо
                    pearlHarbour.getFleet().stopRight();
                    break;
                default:
                    break;
                    
            }
        });

        // відслідковування та обробка подій пов'язаних з мишою
        Main.scene.setOnMouseClicked(event -> {
            // якщо миша потрапляе в мікрооб'ект він активуеться
            pearlHarbour.getFleet().mouseClick (event.getX()-Main.mainGroup.getLayoutX(), event.getY()-Main.mainGroup.getLayoutY());

            // при натискуванні на мінікарту видима область переміщується
            MiniMap.mouseClick (event.getX()-Main.mainGroup.getLayoutX(), event.getY()-Main.mainGroup.getLayoutY());
        });



    }
}
