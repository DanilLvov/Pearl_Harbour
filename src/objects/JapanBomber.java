package objects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class JapanBomber extends US_Bomber {


    public JapanBomber(String name, double positionX, double positionY, Pane group, Ship ship, Ship enemyShip) {

        this(name, positionX, positionY, ship, enemyShip);

        viewName = "Japan_bomber.png";
        viewNameActive = "Japan_bomber_active.png";

        m_Nation = "Japan";

        // створення зображення літаку
        m_View = new ImageView(TextureManager
                .textures
                .get(viewName));
        m_View.setFitWidth(SIZE_X);
        m_View.setFitHeight(SIZE_Y);

        //додавання усіх 3-х графічних примітивів до групи
        m_Group.getChildren().addAll(m_View, nameLine, m_healthBar);
        group.getChildren().add(m_Group);

        m_Type = "Bomber";
    }

    private JapanBomber(String name, double positionX, double positionY, Ship ship, Ship enemyShip) {
        super(name, positionX, positionY, ship, enemyShip);
    }
}
