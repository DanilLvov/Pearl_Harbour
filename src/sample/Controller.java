package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // текстові поля, чексбокси та висувний список
    public TextField name;
    public ComboBox nations;
    public ToggleGroup group;
    public TextField cordX;
    public TextField cordY;
    public Button OK;

    // ініціалізація Controller
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // встановлення стандартних координат
        cordX.setText("0");
        cordY.setText("0");
        // створення висувного списку
        nations.getItems().addAll("Japan", "US");
        System.out.println("Controller.initialize");
    }

    // Дії що відбуваються при натисканні кнопки Ок
    public void pressOK () {

        // виведення застережень при відсутності інформації
        if( !( (name.getText() != null) && (name.getText().length()>0) ) )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Введіть ім'я!");

            alert.showAndWait();

            return;
        }

        if( nations.getValue() == null )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Оберіть Країну!");

            alert.showAndWait();

            return;
        }

        // перетворення інформації у стрічки та числа з комами
        RadioButton selectedType = (RadioButton)group.getSelectedToggle();
        String m_Name= name.getText();
        String m_Nation = nations.getValue().toString();
        String m_Type =  selectedType.getText();
        double m_CordX = Double.parseDouble(cordX.getText());
        double m_CordY = Double.parseDouble(cordY.getText());

        if( m_Nation.equals("US") && m_Type.equals("Jet") )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Не можливо обрати цей тип літаку!");

            alert.showAndWait();

            return;
        }

        // створення нового мікрооб'єкту
        Main.pearlHarbour.getFleet().addJet(m_Nation, m_Name, m_CordX, m_CordY, m_Type);

        Menu.window.close();
    }
}