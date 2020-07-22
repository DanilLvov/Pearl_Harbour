package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.*;

import java.io.IOException;

// клас що реалізує виклик діалогового вікна
// для створення нового мікрооб'екту
public class Menu  {
    public static Stage window = null;
    public static Scene scene;

    public static void display() throws IOException {

        // створення вікна з fxml розміткою
        // детальніше в класі Controller
        Parent root = FXMLLoader.load(Main.class.getResource("sample.fxml"));
        window = new Stage();
        window.initModality( Modality.APPLICATION_MODAL);
        window.setTitle("Створіть новий літак");

        scene = new Scene(root, 440, 358);
        window.setScene(scene);

        window.showAndWait();
    }

    public static void toParade() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ToParade!");

        CheckBox firstQuarter = new CheckBox("Quarter 1");
        firstQuarter.setFont(new Font(20));

        CheckBox secondQuarter = new CheckBox("Quarter 2");
        secondQuarter.setFont(new Font(20));

        CheckBox thirdQuarter = new CheckBox("Quarter 3");
        thirdQuarter.setFont(new Font(20));

        CheckBox fourthQuarter = new CheckBox("Quarter 4");
        fourthQuarter.setFont(new Font(20));

        Button okButton = new Button("Ok");
        okButton.setFont(new Font(45));
        okButton.setLayoutX(60);

        okButton.setOnAction(event -> {
            int jp = 0;
            int us = 0;
            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("US")) {
                if(plane.getState() != State.ACTIVE && plane.getType().equals("Jet") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                }

            }

            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("US")) {
                if(plane.getState() == State.ACTIVE && plane.getType().equals("Jet") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                }
            }

            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("US")) {
                if(plane.getState() != State.ACTIVE && plane.getType().equals("Fighter") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                }
            }

            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("US")) {
                if(plane.getState() == State.ACTIVE && plane.getType().equals("Fighter") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                }
            }

            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("US")) {
                if(plane.getState() != State.ACTIVE && plane.getType().equals("Bomber") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                }
            }

            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("US")) {
                if(plane.getState() == State.ACTIVE  && plane.getType().equals("Bomber") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(jp);
                        plane.setState(State.ON_PARADE);
                        jp++;
                        plane.toParade();
                    }
                }
            }

            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("Japan")) {
                if(plane.getState() != State.ACTIVE && plane.getType().equals("Fighter") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                }
            }

            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("Japan")) {
                if(plane.getState() == State.ACTIVE  && plane.getType().equals("Fighter") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                }
            }
            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("Japan")) {
                if(plane.getState() != State.ACTIVE  && plane.getType().equals("Bomber") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                }
            }
            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("Japan")) {
                if(plane.getState() == State.ACTIVE && plane.getType().equals("Bomber") && plane.getState() != State.DEAD && plane.getState() != State.EXPLODED) {
                    if(firstQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(secondQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() < 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(thirdQuarter.isSelected() && plane.getCenterX() < 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                    else if(fourthQuarter.isSelected() && plane.getCenterX() >= 1500 && plane.getCenterY() >= 909) {
                        plane.setParadeNumber(us);
                        plane.setState(State.ON_PARADE);
                        us++;
                        plane.toParade();
                    }
                }
            }
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(firstQuarter, secondQuarter, thirdQuarter, fourthQuarter, okButton);

        Scene scene = new Scene(layout, 400, 470);
        window.setScene(scene);
        window.showAndWait();
    }
}